package de.artcom.hsm



actual abstract class Action actual constructor() {
    protected var mPreviousState: State<*>? = null
    protected var mNextState: State<*>? = null

    protected var mPayload: MutableMap<String?, Any?>? = null

    actual abstract fun run()
    actual fun setPreviousState(state: State<*>?) {

            mPreviousState = state

    }

    actual fun setNextState(state: State<*>?) {

            mNextState = state


    }

    actual fun setPayload(payload: Map<String?, Any?>?) {

            mPayload = payload as MutableMap<String?, Any?>
    }


}