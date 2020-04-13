package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableMap
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import kotlin.math.atan

expect abstract class Action() {


    abstract fun run()
    fun setPreviousState(state: State<*>?)

    fun setNextState(state: State<*>?)

    fun setPayload(payload: Map<String?, Any?>?)

    fun getPayload():Map<String?, Any?>?

}