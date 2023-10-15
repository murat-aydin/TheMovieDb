package com.murataydin.themoviedb.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


abstract class BaseViewModel<
        Event : IEvent,
        State : IState,
        Effect : IEffect,
        > : ViewModel() {

    private val initialState: State by lazy { setInitialState() }
    abstract fun setInitialState(): State

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

}