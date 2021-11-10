package com.yavin.mybustickets.ui.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.R
import com.yavin.mybustickets.viewmodel.*
import com.yavin.mybustickets.viewmodel.FinishLoading
import com.yavin.mybustickets.viewmodel.NoResultFound
import com.yavin.mybustickets.viewmodel.StartLoading
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionActivity : AppCompatActivity() {

    private val transactionsViewModel: TransactionsViewModel by viewModels()

    private var transactionAdapter: TransactionAdapter = TransactionAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)


        val recyclerView: RecyclerView = findViewById(R.id.ticketRecycleView)
        recyclerView.adapter = transactionAdapter


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        transactionsViewModel.getTransactions()

        observeTicketsUiState()
        observeTicketsData()


    }

    private fun observeTicketsData() {
        transactionsViewModel.ticketsLiveData.observe(this, Observer {
            transactionAdapter.updateData(it)
        })
    }

    private fun observeTicketsUiState() {
        transactionsViewModel.ticketsUiStateLiveData.observe(this, Observer {
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