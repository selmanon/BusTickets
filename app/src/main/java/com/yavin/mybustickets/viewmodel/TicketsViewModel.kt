package com.yavin.mybustickets.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yavin.mybustickets.TicketSolde
import com.yavin.mybustickets.repository.DefaultTicketsPriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(private val ticketPriceRepository : DefaultTicketsPriceRepository, application : Application) : AndroidViewModel(application) {
    var ticketsLiveData = MutableLiveData<List<TicketSolde>>()
    val ticketsUiStateLiveData = MutableLiveData<TicketsSoldUiState>()

    fun fetchTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //1
                withContext(Dispatchers.Main) {
                    ticketsUiStateLiveData.value = StartLoading
                }

                val tickets = ticketPriceRepository.getTickets()
                ticketsLiveData.postValue(tickets)

                //2
                ticketsUiStateLiveData.postValue(FinishLoading)
            } catch (e: Exception) {
                //3
                ticketsUiStateLiveData.postValue(NoResultFound)
            }
        }
    }

}