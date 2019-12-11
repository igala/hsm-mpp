package de.artcom.hsm

abstract class Action() {
    protected var mPreviousState: State<*>? = null
    protected var mNextState: State<*>? = null

    protected var mPayload: MutableMap<String?, Any?>? = null

    abstract fun run()
    fun setPreviousState(state: State<*>?) {
        mPreviousState = state
    }

    fun setNextState(state: State<*>?) {
        mNextState = state
    }

    fun setPayload(payload: Map<String?, Any?>?) {
        mPayload = payload as MutableMap<String?, Any?>
    }
}