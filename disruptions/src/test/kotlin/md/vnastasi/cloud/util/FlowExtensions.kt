package md.vnastasi.cloud.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

fun <T> collecting(block: suspend () -> Flow<T>): List<T> = runBlocking { block.invoke().toList(mutableListOf()) }
