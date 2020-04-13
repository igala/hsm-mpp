package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableMap
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import kotlin.math.atan

actual abstract class Action actual constructor() {
    protected var mPreviousState: AtomicReference<State<*>?> = AtomicReference(null)
    protected var mNextState: AtomicReference<State<*>?> = AtomicReference(null)

    protected var mPayload: AtomicReference<MutableMap<String?, Any?>?> = AtomicReference(hashMapOf())

    actual abstract fun run()
    actual fun setPreviousState(state: State<*>?) {

            mPreviousState.value = state

    }

    actual fun setNextState(state: State<*>?) {

            mNextState.value = null


    }

    actual fun setPayload(payload: Map<String?, Any?>?) {

            mPayload.value?.putAll(payload as MutableMap<String?, Any?>)
    }

    actual fun getPayload(): Map<String?, Any?>? {
       return mPayload.get()
    }
}