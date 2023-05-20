package com.example.betareadingapp.feature_text.domain.util.error

import java.util.*

class ExceptionHandlerBuildingScope(private val first: (ExceptionHandler?) -> ExceptionHandler) {

    private val handlers: Deque<(ExceptionHandler?) -> ExceptionHandler> = LinkedList()

    infix fun then(handler: (ExceptionHandler?) -> ExceptionHandler) = handlers.add(handler)

    fun build(): ExceptionHandler {
        var second: ExceptionHandler? = null
        while (handlers.isEmpty().not()) {
            second = handlers.removeLast()(second)
        }

        return first(second)
    }
}

fun createChainOfHandlers(first: (ExceptionHandler?) -> ExceptionHandler, builder: ExceptionHandlerBuildingScope.() -> Unit): ExceptionHandler {
    val scope = ExceptionHandlerBuildingScope(first)
    builder(scope)
    return scope.build()
}
