package com.digitalsamurai.jni_test.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class ScreenViewModel<STATE : UiState, EVENT : UiEvent, ACTIONS : UiActions> : ViewModel() {


    private var controller: NavController? = null
    fun setNavController(controller: NavController) {
        this.controller = controller
    }


    // UI STATE MANAGEMENT

    abstract fun initialState(): STATE

    @Suppress("UNCHECKED_CAST")
    open fun getActions(): ACTIONS = this as ACTIONS

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState())
    val state = _state

    private var _events = MutableSharedFlow<EVENT>(extraBufferCapacity = 1)
    val events = _events

    protected fun updateState(update: (STATE) -> STATE) {
        _state.update { state ->
            update(state)
        }
    }

    protected fun event(event: EVENT) {
        _events.tryEmit(event)
    }

    protected fun navigateTo(screen: BaseScreen<*, *, *>) {
        controller?.let {
            it.navigate(screen.screenRoute)
        }
    }
}