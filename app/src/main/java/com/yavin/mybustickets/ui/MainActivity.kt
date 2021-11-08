package com.yavin.mybustickets.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.R
import com.yavin.mybustickets.viewmodel.FinishLoading
import com.yavin.mybustickets.viewmodel.NoResultFound
import com.yavin.mybustickets.viewmodel.StartLoading
import com.yavin.mybustickets.viewmodel.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val ticketsViewModel: TicketsViewModel by viewModels()

    private var ticketsSoldAdapter : TicketsSoldeAdapter = TicketsSoldeAdapter()

    private val ticketPriceAndItems : MutableMap<Int, Int> = mutableMapOf()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            // Handle the Intent
            //do stuff here
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonPay).setOnClickListener {
            val totalAmount = calculateTotalAmount()
            Log.i("totalAmount", totalAmount.toString())
            doPayment(totalAmount)
        }

        val recyclerView: RecyclerView = findViewById(R.id.ticketRecycleView)
        recyclerView.adapter = ticketsSoldAdapter

        ticketsSoldAdapter.onItemsCountChanged = {
            ticketPriceAndItems[it.ticketSolde.ticketPrice] = it.items
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        observeTicketsUiState()
        observeTicketsData()

        ticketsViewModel.fetchTickets()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for ((price, items) in ticketPriceAndItems) {
            totalAmount += price * items
        }
        return totalAmount
    }

    private fun observeTicketsData() {
        ticketsViewModel.ticketsLiveData.observe(this, Observer {
            ticketsSoldAdapter.updateData(it)
        })
    }

    private fun observeTicketsUiState() {
        ticketsViewModel.ticketsUiStateLiveData.observe(this, Observer {
            when (it) {
                StartLoading -> {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE

                    findViewById<RecyclerView>(R.id.ticketRecycleView).visibility = View.GONE
                    findViewById<TextView>(R.id.textViewNoData).visibility = View.GONE

                }
                FinishLoading -> {
                    findViewById<RecyclerView>(R.id.ticketRecycleView).visibility = View.VISIBLE

                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    findViewById<TextView>(R.id.textViewNoData).visibility = View.GONE

                }
                NoResultFound -> {
                    findViewById<TextView>(R.id.textViewNoData).visibility = View.VISIBLE

                    findViewById<RecyclerView>(R.id.ticketRecycleView).visibility = View.GONE
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                }
            }
        })
    }

    private fun doPayment(ticketPrice: Int) {
        try {
            val intent = Intent()
            val cn = ComponentName.createRelative("com.yavin.macewindu", ".PaymentActivity")

            intent.component = cn

            intent.putExtra("amount", ticketPrice.toString())
            intent.putExtra("cartId", "178238")
            intent.putExtra("vendorToken", "busTicket")
            intent.putExtra("reference", "872")

            startForResult.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Yavin PaymentActivity not found", Toast.LENGTH_SHORT).show()
        }
    }


}