package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableMap
import co.touchlab.stately.concurrency.AtomicReference

abstract class Action() {
    protected var mPreviousState: AtomicReference<State<*>?> = AtomicReference(null)
    protected var mNextState: AtomicReference<State<*>?> = AtomicReference(null)

    protected var mPayload: MutableMap<String?, Any?>? = null

    abstract fun run()
    fun setPreviousState(state: State<*>?) {
        if(mPreviousState==null)
            mPreviousState = AtomicReference(state)
        else
            mPreviousState.set(state)
    }

    fun setNextState(state: State<*>?) {
        if(mNextState==null)
            mNextState = AtomicReference(state)
        else
            mNextState.set(state)

    }

    fun setPayload(payload: Map<String?, Any?>?) {
        mPayload = payload as MutableMap<String?, Any?>
    }
}