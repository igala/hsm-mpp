package de.artcom.hsm

import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.isolate.IsolateState

//import java.util.*

class Sub : State<Sub> {
    private val mSubMachine: AtomicReference<StateMachine?> = AtomicReference(null)
    override fun getThis(): Sub {
        return this
    }

    constructor(id: String?, subMachine: StateMachine) : super(id!!) {
        mSubMachine.set(subMachine)
        mSubMachine.get()?.container!!.set(this)
    }

    constructor(id: String?, initialState: State<*>?, vararg states: State<*>?) : super(id!!) {
        mSubMachine.set(StateMachine(initialState, *states))
        mSubMachine.get()!!.container.set(this)
    }

    override fun enter(prev: State<*>?, next: State<*>?, payload: Map<String?, Any?>) {
        super.enter(prev, next, payload)
        mSubMachine.get()!!.enterState(prev, next, payload as HashMap<String?, Any?>)
    }

    override fun exit(prev: State<*>?, next: State<*>?, payload: Map<String?, Any?>) {
        mSubMachine.get()!!.teardown(payload)
        super.exit(prev, next, payload)
    }

    override fun handleWithOverride(event: Event): Boolean {
        return if (mSubMachine.get()!!.handleWithOverride(event)) {
            true
        } else {
            super.handleWithOverride(event)
        }
    }

    override fun toString(): String {
        return "$id/($mSubMachine)"
    }

    override fun addParent(stateMachine: StateMachine) {
        mSubMachine.get()!!.addParent(stateMachine)
    }

    override fun setOwner(ownerMachine: StateMachine) {
        super.owner.set(ownerMachine)
        mSubMachine.get()!!.name.set(owner.get()!!.name.get())
    }

    override val descendantStates: Collection<out State<*>>
        get() {
            return mSubMachine.get()!!.descendantStates
        }

    override val allActiveStates: List<State<*>?>
        get() {
            return mSubMachine.get()!!.allActiveStates
        }
}