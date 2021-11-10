package com.yavin.mybustickets.ui.payment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.yavin.mybustickets.ui.settings.SettingTicketPriceActivity
import com.yavin.mybustickets.viewmodel.FinishLoading
import com.yavin.mybustickets.viewmodel.NoResultFound
import com.yavin.mybustickets.viewmodel.StartLoading
import com.yavin.mybustickets.viewmodel.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    private val ticketsViewModel: TicketsViewModel by viewModels()

    private var ticketsSoldAdapter: TicketsSoldeAdapter = TicketsSoldeAdapter()

    private var dayPriceForQuantity: Int = 0
    private var singlePriceForQuantity: Int = 0
    private var weekPriceForQuantity: Int = 0


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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

        ticketsSoldAdapter.onDayItemsCountChanged = {
            dayPriceForQuantity = it.items * it.ticketDomain.ticketPrice
        }
        ticketsSoldAdapter.onSingleItemsCountChanged = {
            singlePriceForQuantity = it.items * it.ticketDomain.ticketPrice
        }
        ticketsSoldAdapter.onWeekItemsCountChanged = {
            weekPriceForQuantity = it.items * it.ticketDomain.ticketPrice
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        observeTicketsUiState()
        observeTicketsData()

        ticketsViewModel.fetchTickets()
    }

    private fun calculateTotalAmount(): Int {
        return dayPriceForQuantity + singlePriceForQuantity + weekPriceForQuantity
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.options -> {
                val intent = Intent(this, SettingTicketPriceActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.history -> {
                //val intent = Intent(this, TransactionsActivity::class.java)
                //startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}