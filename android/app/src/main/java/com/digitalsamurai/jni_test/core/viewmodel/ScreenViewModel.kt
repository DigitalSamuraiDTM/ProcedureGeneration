package com.digitalsamurai.jni_test.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.extension.kotlin.asContextElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ScreenViewModel<STATE : UiState, EVENT : UiEvent, ACTIONS : UiActions>(
    private val otel: Otel,
    private val screenSpan: Span,
) : ViewModel() {

    protected fun CoroutineScope.launchTraced(
        spanName: String,
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        launch: suspend () -> Unit,
    ) {
        val scopeSpan = otel.tracer().spanBuilder(spanName)
            .setParent(Context.current().with(screenSpan))
            .startSpan()
        val scopedContext = Context.current().with(scopeSpan).asContextElement()
        this.launch(scopedContext + dispatcher) {
            try {
                launch()
            } catch (e: Exception) {
                scopeSpan.end()
                throw e
            } finally {
                scopeSpan.end()
            }
        }
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
}