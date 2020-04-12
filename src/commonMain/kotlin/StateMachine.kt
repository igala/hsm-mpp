package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableList
import co.touchlab.stately.concurrency.AtomicBoolean
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import co.touchlab.stately.isolate.IsolateState

//import java.util.ArrayList
//import java.util.Arrays
//import java.util.HashMap
//import java.util.concurrent.ConcurrentLinkedQueue
//import java.util.logging.Level
//import java.util.logging.Logger

class StateMachine(initialState: State<*>?, vararg states: State<*>?) : EventHandler {
    internal var LOGGER: ILogger = object : ILogger {
        override fun debug(message: String?) {
            //Logger.getAnonymousLogger().log(Level.INFO, message)
            print(message)
        }
    }
    private var mStateList = IsoMutableList<State<*>>()
    open var descendantStates = IsoMutableList<State<*>>()
    internal var name: AtomicReference<String> = AtomicReference("")
    private  var mInitialState: AtomicReference<State<*>?> = AtomicReference(null)
    private  var mCurrentState: AtomicReference<State<*>?> = AtomicReference(null)
    private var mEventQueue = AtomicReference<ConcurrentLinkedQueue<Event>>(ConcurrentLinkedQueue<Event>())
    private var mEventQueueInProgress = AtomicBoolean(false)
    internal var path: IsoMutableList<StateMachine> = IsoMutableList()
    internal  var container:AtomicReference<State<*>?> = AtomicReference<State<*>?>(null)
    private  var logger : AtomicReference<ILogger?> = AtomicReference<ILogger?>(LOGGER)
    val pathString: String
        get() {
            var sb = StringBuilder()
            var count = 0
            sb.append("\r\n")
            for (stateMachine in path) {
                sb.append(++count)
                sb.append(' ')
                sb.append(stateMachine.toString())
                sb.append("\r\n")
            }
            return sb.toString()
        }
    val allActiveStates: List<State<*>?>
        get() {
            val stateList = ArrayList<State<*>?>()
            mCurrentState.let { stateList.add(it.get()) }
            mCurrentState.get()?.allActiveStates?.let { stateList.addAll(it) }
            return stateList
        }

    constructor(name: String, initialState: State<*>, vararg states: State<*>) : this(initialState, *states) {
        this.name.set(name)
    }

    init {
        mStateList.addAll(listOf(*states) as Collection<State<*>>)
        mStateList.add(initialState!!)
        mInitialState.set(initialState)
        setOwner()
        generatePath()
        generateDescendantStateList()
        name.set("")
    }

    fun setLogger(log: ILogger) {
        logger.set(log)
    }

    private fun generateDescendantStateList() {
        descendantStates.addAll(mStateList)
        for (state in mStateList) {
            descendantStates.addAll(state.descendantStates)
        }
    }

    private fun generatePath() {
        path.add(0, this)
        for (state in mStateList) {
            state.addParent(this)
        }
    }


    fun init(payload: Map<String, Any>? = HashMap<String, Any>()) {
            logger.get()!!.debug(name.get() + " init")
        if (mInitialState.get() == null) {
            throw IllegalStateException(name.get() + " Can't init without states defined.")
        } else {
            mEventQueueInProgress.value = true
            if (payload == null) {

                    enterState(null, mInitialState.get()!!, HashMap<String?, Any?>())
            }
            else
                enterState(null, mInitialState.get()!!, payload as HashMap<String?, Any?>)
            mEventQueueInProgress.value = false
            processEventQueue()
        }
    }

    fun init(){
        init(HashMap<String, Any>())
    }

    internal fun teardown(payload: Map<String?, Any?>?) {
        logger.get()?.debug(name.get() + " teardown")
        if (payload == null) {
                exitState(mCurrentState.get(), null, HashMap<String?, Any?>())
        }
        else
                exitState(mCurrentState.get(), null, payload as HashMap<String?, Any?>)
        mCurrentState.set(null)
    }

    fun teardown() {
        teardown(HashMap<String?, Any>())
    }


     override fun handleEvent(event: String?) {
        handleEvent(event, HashMap<String?, Any>())
    }

    override fun handleEvent(eventName: String?, payload: Map<String?, Any?>) {
        if (mCurrentState.get() == null) {
            return // TODO: throw an exception here
        }
        // TODO: make a deep copy of the payload (also do this in Parallel)
        mEventQueue.get().add(Event(eventName, payload))
        processEventQueue()
    }

    private fun processEventQueue() {
        if (mEventQueueInProgress.value) {
            return
        }
        mEventQueueInProgress.value = true
        while (mEventQueue.get().peek() != null) {
            val event = mEventQueue.get().poll()
            if (!mCurrentState.get()?.handleWithOverride(event)!!) {
                logger.get()?.debug(name.get() + " nobody handled event: " + event.name)
            }
        }
        mEventQueueInProgress.value = false
    }

    internal fun handleWithOverride(event: Event): Boolean {
        if (mCurrentState.get() != null) {
            return mCurrentState!!.get()!!.handleWithOverride(event)
        } else {
            return false
        }
    }

    internal fun executeHandler(handler: Handler, event: Event) {
        logger.get()?.debug(name.get() + " execute handler for event: " + event.name)
        val action = handler.action
        val targetState = handler.targetState
        if (targetState == null) {
            throw IllegalStateException(name.get() + " cant find target state for transition " + event.name)
        }
        when (handler.kind.get()) {
            TransitionKind.External -> doExternalTransition(mCurrentState.get(), targetState.get(), action.get(), event)
            TransitionKind.Local -> doLocalTransition(mCurrentState.get(), targetState.get(), action.get(), event)
            TransitionKind.Internal -> executeAction(action.get(), mCurrentState.get(), targetState.get(), event.payload)
        }
    }

    private fun executeAction(action: Action?, previousState: State<*>?, targetState: State<*>?, payload: Map<String?, Any?>) {
        if (action != null) {
            action.setPreviousState(previousState)
            action.setNextState(targetState)
            action.setPayload(payload)
            action.run()
        }
    }

    private fun doExternalTransition(previousState: State<*>?, targetState: State<*>?, action: Action?, event: Event) {
        val lca = findLowestCommonAncestor(targetState)
        lca.switchState(previousState, targetState, action, event.payload)
    }

    private fun doLocalTransition(previousState: State<*>?, targetState: State<*>?, action: Action?, event: Event) {
        if (previousState?.descendantStates!!.contains(targetState)) {
            val stateMachine = findNextStateMachineOnPathTo(targetState)
            stateMachine.switchState(previousState, targetState, action, event.payload)
        } else if (targetState?.descendantStates!!.contains(previousState)) {
            val targetLevel = targetState.owner.get()?.path!!.size
            val stateMachine = path.get(targetLevel!!)
            stateMachine.switchState(previousState, targetState, action, event.payload)
        } else if (previousState.equals(targetState)) {
            //TODO: clarify desired behavior for local transition on self
            // currently behaves like an internal transition
        } else {
            doExternalTransition(previousState, targetState, action, event)
        }
    }

    private fun switchState(previousState: State<*>?, nextState: State<*>?, action: Action?, payload: Map<String?, Any?>) {
        exitState(previousState, nextState, payload)
            if (action != null) {
                    executeAction(action, previousState, nextState, payload)
            }
        enterState(previousState, nextState, payload)
    }

    internal fun enterState(previousState: State<*>?, targetState: State<*>?, payload: Map<String?, Any?>) {
        val targetLevel = targetState?.getOwner()?.path?.size
        val localLevel = path.size!!
        var nextState: State<*>? = null
            if (targetLevel != null) {
                    if (targetLevel < localLevel) {
                            nextState = mInitialState.get()
                    } else if (targetLevel == localLevel) {
                            nextState = targetState
                    } else { // if targetLevel > localLevel
                            nextState = findNextStateOnPathTo(targetState)
                    }
            }
        if (mStateList.contains(nextState)) {
            mCurrentState.set(nextState)
        } else {
            mCurrentState.set(mInitialState.get())
        }
        mCurrentState.get()?.enter(previousState, targetState, payload)
    }

    private fun findNextStateOnPathTo(targetState: State<*>?): State<*> {
        return findNextStateMachineOnPathTo(targetState).container.get()!!
    }

    private fun findNextStateMachineOnPathTo(targetState: State<*>?): StateMachine {
        val localLevel = path.size
        val targetOwner = targetState?.getOwner()
        return targetOwner?.path!!.get(localLevel)
    }

    private fun exitState(previousState: State<*>?, nextState: State<*>?, payload: Map<String?, Any?>) {
        mCurrentState.get()?.exit(previousState, nextState, payload)
    }

    private fun setOwner() {
        for (state in mStateList) {
            state.setOwner(this)
        }
    }

    public override fun toString(): String {
        if (mCurrentState.get() == null) {
            return mInitialState.toString()
        }
        return mCurrentState.toString()
    }

    internal fun addParent(stateMachine: StateMachine) {
        path.add(0, stateMachine)
        for (state in mStateList) {
            state.addParent(stateMachine)
        }
    }

    private fun findLowestCommonAncestor(targetState: State<*>?): StateMachine {
        if (targetState?.getOwner() == null) {
            throw IllegalStateException(name.get() + " Target state '" + targetState?.id + "' is not contained in state machine model.")
        }
        val targetPath = targetState.getOwner()?.path
        val size = path.size
        for (i in 1 until size) {
            try {
                val targetAncestor = targetPath?.get(i)
                val localAncestor = path.get(i)
                if (targetAncestor != localAncestor) {
                    return path.get(i - 1)
                }
            } catch (e: IndexOutOfBoundsException) {
                return path.get(i - 1)
            }
        }
        return this
    }


}