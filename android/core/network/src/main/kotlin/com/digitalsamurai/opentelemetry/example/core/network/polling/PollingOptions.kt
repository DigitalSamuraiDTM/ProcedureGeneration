package com.digitalsamurai.opentelemetry.example.core.network.polling

public sealed class PollingOptions {
    public open val delayBetweenRequests: Long = 1000L

    public abstract val failedRequestPolicy: FailedRequestPolicy

    public abstract fun isNeedContinue(currentStep: Int): Boolean

    public data class Infinity(
        override val failedRequestPolicy: FailedRequestPolicy = FailedRequestPolicy.RETURN_ERROR_AND_CONTINUE
    ) : PollingOptions() {
        override fun isNeedContinue(currentStep: Int): Boolean = true
    }

    public data class Fixed(
        val counts: Int,
        override val failedRequestPolicy: FailedRequestPolicy = FailedRequestPolicy.RETURN_ERROR_AND_CONTINUE
    ) : PollingOptions() {
        override fun isNeedContinue(currentStep: Int): Boolean {
            return currentStep <= counts
        }
    }

    public enum class FailedRequestPolicy {
        /**
         * Верни ошибку в Result<> и продолжай поллинг
         */
        RETURN_ERROR_AND_CONTINUE,

        /**
         * Пропусти ошибки и продолжай поллинг
         */
        SKIP_ERROR_AND_CONTINUE,

        /**
         * Верни ошибку в Result и вызови cancel
         */
        RETURN_ERROR_AND_CANCEL,

        /**
         * Верни ошибку в Result и выйди из цикла поллинга
         */
        RETURN_ERROR_AND_FINISH,
    }
}