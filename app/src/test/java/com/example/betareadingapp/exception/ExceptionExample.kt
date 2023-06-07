package com.example.betareadingapp.exception

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExceptionExample {

    @Test(expected = ExampleException::class)
    fun `given flow that throws exception when collecting it then that exception is thrown`() = runTest {
        createExampleFlow()
            .toList()
    }

    @Test
    fun `given flow that throws exception when flow catches it then flow completes without throwing`() = runTest {
        val expected = listOf("a", "b")

        val result = createExampleFlow()
            .catch { println(it.message) }
            .toList()

        assertEquals(expected, result)
    }

    @Test
    fun `given flow that throws exception when flow catches an processes it then flow completes with processed value`() = runTest {
        val expected = listOf("a", "b", "Example")

        val result = createExampleFlow()
            .catch { emit(it.message.orEmpty()) }
            .toList()

        assertEquals(expected, result)
    }

    private fun createExampleFlow() = flow {
        emit("a")
        emit("b")
        throw ExampleException()
    }
}

class ExampleException : Throwable("Example")
