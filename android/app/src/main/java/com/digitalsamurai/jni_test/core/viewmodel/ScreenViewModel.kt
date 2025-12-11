package com.digitalsamurai.jni_test.core.viewmodel

import androidx.lifecycle.ViewModel
import com.digitalsamurai.core.otel.Otel
import com.digitalsamurai.core.otel.extensions.endWithException
import com.digitalsamurai.core.otel.extensions.endWithSuccess
import com.digitalsamurai.core.otel.extensions.endWithUnknown
import io.opentelemetry.api.trace.Span
import io.opentelemetry.context.Context
import io.opentelemetry.extension.kotlin.asContextElement
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ScreenViewModel<STATE : UiState, EVENT : UiEvent, ACTIONS : UiActions>(
    private val otel: Otel,
    private val screenSpan: Span,
) : ViewModel() {

    protected fun CoroutineScope.launchTraced(
        spanName: String,
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        launch: suspend Span.() -> Unit,
    ): Job {
        val scopeSpan = otel.tracer().spanBuilder(spanName)
            .setParent(Context.current().with(screenSpan))
            .startSpan()
        val scopedContext = Context.current().with(scopeSpan).asContextElement()
        return this.launch(scopedContext + dispatcher) {
            try {
                scopeSpan.launch()
                scopeSpan.endWithSuccess()
            } catch (c: CancellationException) {
                scopeSpan.setAttribute("was_cancelled", true)
                scopeSpan.endWithException(null)
            } catch (e: Exception) {
                scopeSpan.endWithException(e)
                throw e
            } finally {
                // если никто не закрыл спан с ошибкой, то он закроется с неизвестностью
                scopeSpan.endWithUnknown()
            }
        }
    }

    // UI STATE MANAGEMENT

    abstract fun initialState(): STATE

    @Suppress("UNCHECKED_CAST")
    open fun getActions(): ACTIONS = this as ACTIONS

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState())
    val state: StateFlow<STATE> = _state

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