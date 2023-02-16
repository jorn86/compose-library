package org.hertsig.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker

private val log = logger {}

fun logger(lambda: () -> Unit): Logger = LoggerFactory.getLogger(parseName(lambda))
private fun parseName(lambda: () -> Unit): String {
    val name = lambda.javaClass.name
    return when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
}

fun Logger.error(t: Throwable? = null, marker: Marker? = null, message: LazyMessage) = error(marker, message.safeInvoke(), t)
fun Logger.warn(t: Throwable? = null, marker: Marker? = null, message: LazyMessage) = warn(marker, message.safeInvoke(), t)
fun Logger.info(t: Throwable? = null, marker: Marker? = null, message: LazyMessage) = info(marker, message.safeInvoke(), t)
fun Logger.debug(t: Throwable? = null, marker: Marker? = null, message: LazyMessage) = debug(marker, message.safeInvoke(), t)
fun Logger.trace(t: Throwable? = null, marker: Marker? = null, message: LazyMessage) = trace(marker, message.safeInvoke(), t)

typealias LazyMessage = () -> String
private fun LazyMessage.safeInvoke(): String = try {
    invoke()
} catch (e: Exception) {
    val message = "Exception invoking lazy message ${javaClass.name}"
    log.error(message, e)
    message
}
