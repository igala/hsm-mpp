package de.artcom.hsm

import co.touchlab.stately.concurrency.AtomicReference

internal class Handler {
    val targetState: AtomicReference<State<*>?> = AtomicReference(null)
    val kind: AtomicReference<TransitionKind?> = AtomicReference(null)
    private var mGuard: AtomicReference<Guard?> = AtomicReference(null)
    var action: AtomicReference<Action?> = AtomicReference(null)
        private set

    constructor(targetState: State<*>, kind: TransitionKind, action: Action?, guard: Guard?) {
        this.targetState.set(targetState)
        this.kind.set(kind)
        this.action.set(action)
        mGuard.set(guard)
    }

    constructor(targetState: State<*>, kind: TransitionKind, guard: Guard?) {
        this.targetState.set(targetState)
        this.kind.set(kind)
        mGuard.set(guard)
    }

    constructor(targetState: State<*>, kind: TransitionKind, action: Action?) {
        this.targetState.set(targetState)
        this.kind.set(kind)
        this.action.set(action)
    }

    constructor(targetState: State<*>, kind: TransitionKind) {
        this.targetState.set(targetState)
        this.kind.set(kind)
    }

    fun evaluate(event: Event): Boolean {
        return if (mGuard.get() != null) {
            mGuard.get()!!.evaluate(event.payload)
        } else true
    }

}