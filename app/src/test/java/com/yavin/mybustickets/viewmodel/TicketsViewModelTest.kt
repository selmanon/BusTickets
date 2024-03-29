package com.yavin.mybustickets.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yavin.mybustickets.domaine.TicketDomain
import com.yavin.mybustickets.domaine.TicketType
import com.yavin.mybustickets.domaine.repository.DefaultTicketsRepository
import com.yavin.mybustickets.rule.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TicketsViewModelTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesDispatcherRule = CoroutineTestRule()

    private lateinit var tickets: ArrayList<TicketDomain>

    @MockK
    private lateinit var ticketsRepository: DefaultTicketsRepository

    private lateinit var cut: TicketsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

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