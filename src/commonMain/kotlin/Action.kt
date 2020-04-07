package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableMap
import co.touchlab.stately.concurrency.AtomicReference

abstract class Action() {
    protected var mPreviousState: AtomicReference<State<*>?> = AtomicReference(null)
    protected var mNextState: AtomicReference<State<*>?> = AtomicReference(null)

    protected var mPayload: MutableMap<String?, Any?>? = null

    abstract fun run()
    fun setPreviousState(state: State<*>?) {
        mPreviousState = AtomicReference(state)
    }

    fun setNextState(state: State<*>?) {
        mNextState = AtomicReference(state)
    }

    fun setPayload(payload: Map<String?, Any?>?) {
        mPayload = payload as MutableMap<String?, Any?>
    }
}