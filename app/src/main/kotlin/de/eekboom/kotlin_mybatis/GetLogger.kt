package de.eekboom.kotlin_mybatis

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Allows to initialize a logger with automatic setting of the logger's name using the declaring
 * parent class:
 *
 * `private val logger = getLogger {}`
 */
fun getLogger(func: () -> Unit): Logger {
    val javaClass = func.javaClass
    val enclosingClass = javaClass.enclosingClass
    // If the logger is declared in a top level fun(ction), then the class name equals the
    // Kotlin file name with "Kt" suffix
    val name = enclosingClass?.name?.removeSuffix("Kt") ?: javaClass.name
    return LogManager.getLogger(name)
}
