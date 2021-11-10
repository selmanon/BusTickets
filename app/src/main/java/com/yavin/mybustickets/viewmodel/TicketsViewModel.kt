package com.yavin.mybustickets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavin.mybustickets.data.TicketDomain
import com.yavin.mybustickets.repository.DefaultTicketsPriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(private val ticketPriceRepository : DefaultTicketsPriceRepository) : ViewModel() {
    var ticketsLiveData = MutableLiveData<List<TicketDomain>>()
    val ticketsUiStateLiveData = MutableLiveData<TicketsSoldUiState>()

    fun fetchTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    ticketsUiStateLiveData.value = StartLoading
                }

                val tickets = ticketPriceRepository.getTickets()
                ticketsLiveData.postValue(tickets)

                ticketsUiStateLiveData.postValue(FinishLoading)
            } catch (e: Exception) {
                ticketsUiStateLiveData.postValue(NoResultFound)
            }
        }
    }

    fun saveTicketsNewPrice(type : String, price : Int) {
        viewModelScope.launch(Dispatchers.IO) {
                ticketPriceRepository.setPrice(type, price)
            }
        }
    }
