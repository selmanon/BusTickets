package com.yavin.mybustickets

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.viewmodel.FinishLoading
import com.yavin.mybustickets.viewmodel.NoResultFound
import com.yavin.mybustickets.viewmodel.StartLoading
import com.yavin.mybustickets.viewmodel.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var ticketsViewModel: TicketsViewModel

    private var ticketsSoldAdapter : TicketsSoldeAdapter = TicketsSoldeAdapter()

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

        val recyclerView: RecyclerView = findViewById(R.id.ticketRecycleView)
        recyclerView.adapter = ticketsSoldAdapter

        ticketsViewModel.fetchTickets()

        observeTicketsUiState()
        observeTicketsData()
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

                    findViewById<ProgressBar>(R.id.ticketRecycleView).visibility = View.GONE
                    findViewById<ProgressBar>(R.id.textViewNoData).visibility = View.GONE

                }
                FinishLoading -> {
                    findViewById<ProgressBar>(R.id.ticketRecycleView).visibility = View.VISIBLE

                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    findViewById<ProgressBar>(R.id.textViewNoData).visibility = View.GONE

                }
                NoResultFound -> {
                    findViewById<ProgressBar>(R.id.textViewNoData).visibility = View.VISIBLE

                    findViewById<ProgressBar>(R.id.ticketRecycleView).visibility = View.GONE
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                }
            }
        })
    }

    private fun doPayment() {
        try {
            val intent = Intent()
            val cn = ComponentName.createRelative("com.yavin.macewindu", ".WelcomeActivity")

            intent.component = cn

            intent.putExtra("amount", "100")
            intent.putExtra("cartId", "178238")
            intent.putExtra("vendorToken", "busTicket")
            intent.putExtra("reference", "872")

            startForResult.launch(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Yavin PaymentActivity not found", Toast.LENGTH_SHORT).show()
        }
    }


}