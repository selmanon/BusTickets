package com.yavin.mybustickets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavin.mybustickets.domaine.TransactionDomain
import com.yavin.mybustickets.domaine.repository.DefaultTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private val transactionRepository: DefaultTransactionRepository) : ViewModel() {
    var ticketsLiveData = MutableLiveData<List<TransactionDomain>>()
    val ticketsUiStateLiveData = MutableLiveData<TicketsSoldUiState>()

    fun getTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    ticketsUiStateLiveData.value = StartLoading
                }

                val tickets = transactionRepository.getTransaction()
                ticketsLiveData.postValue(tickets)

                ticketsUiStateLiveData.postValue(FinishLoading)
            } catch (e: Exception) {
                ticketsUiStateLiveData.postValue(NoResultFound)
            }
        }
    }
}
