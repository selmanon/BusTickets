package com.yavin.mybustickets.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yavin.mybustickets.data.TicketDomain
import com.yavin.mybustickets.data.TicketType
import com.yavin.mybustickets.data.repository.TicketsRepository
import com.yavin.mybustickets.rule.CoroutineTestRule
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TicketsViewModelTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesDispatcherRule = CoroutineTestRule()

    private lateinit var tickets: ArrayList<TicketDomain>

    @Mock
    private lateinit var ticketsRepository: TicketsRepository

    private lateinit var cut: TicketsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        cut = TicketsViewModel(ticketsRepository)

        tickets = arrayListOf(TicketDomain(TicketType.SINGLE, 100))
    }

    @Test
    fun `result finish loading state with success`() {
        // Arrange
        coEvery { ticketsRepository.getTickets() } returns tickets

        // Act
        cut.fetchTickets()

        // Assert
        assert(cut.ticketsUiStateLiveData.value is FinishLoading)
    }

    @Test
    fun `result finish loading with no result state`() {
        // Arrange
        coEvery { ticketsRepository.getTickets() } returns emptyList()

        // Act
        cut.fetchTickets()

        // Assert
        assert(cut.ticketsUiStateLiveData.value is NoResultFound)
    }


}