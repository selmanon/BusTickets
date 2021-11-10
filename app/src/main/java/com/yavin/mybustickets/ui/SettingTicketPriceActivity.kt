package com.yavin.mybustickets.ui

import android.app.Activity
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
class SettingTicketPriceActivity : AppCompatActivity() {

    private val ticketsViewModel: TicketsViewModel by viewModels()

    private var ticketsSoldAdapter = SettingTicketPriceAdapter()

    private var dayPrice : Int? = null
    private var singlePrice : Int? = null
    private var weekPrice : Int? = null

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
        setContentView(R.layout.activity_settings_main)

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            Log.i("day new price", dayPrice.toString())
            saveNewPrice()
            relaunchPayment()
        }

        val recyclerView: RecyclerView = findViewById(R.id.ticketRecycleView)
        recyclerView.adapter = ticketsSoldAdapter

        ticketsSoldAdapter.onDayPriceChangedChanged = {
            dayPrice = it.items
        }

        ticketsSoldAdapter.onSinglePriceChangedChanged = {
            singlePrice = it.items
        }

        ticketsSoldAdapter.onDayPriceChangedChanged = {
            weekPrice = it.items
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        observeTicketsUiState()
        observeTicketsData()

        ticketsViewModel.fetchTickets()
    }

    private fun relaunchPayment() {
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
    }

    private fun saveNewPrice() {
        dayPrice?.let { ticketsViewModel.saveTicketsNewPrice("Day", it*100) }
        singlePrice?.let { ticketsViewModel.saveTicketsNewPrice("Single", it*100) }
        weekPrice?.let { ticketsViewModel.saveTicketsNewPrice("Week", it*100) }
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


}