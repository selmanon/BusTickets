package com.yavin.mybustickets.viewmodel

sealed class TicketsSoldUiState

internal object StartLoading : TicketsSoldUiState()
internal object NoResultFound : TicketsSoldUiState()
internal object FinishLoading : TicketsSoldUiState()
