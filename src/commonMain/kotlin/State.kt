package de.artcom.hsm

import co.touchlab.stately.collections.IsoMutableList
import co.touchlab.stately.collections.IsoMutableMap
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.freeze
import co.touchlab.stately.isolate.IsolateState

//import com.google.common.collect.LinkedListMultimap
//import java.util.*
//import java.util.logging.Level
//import java.util.logging.Logger


open class State<T : State<T>>(id: String) {
    internal var LOGGER: ILogger = object : ILogger {
            override fun debug(message: String?) {
//            Logger.getAnonymousLogger().log(Level.INFO, message)
                print(message)
        }

    }
    var id: String
    private var mOnEnterAction: IsolateState<Action>? = null
    private var mOnExitAction: IsolateState<Action>? = null
    private  var mHandlers: IsoMutableMap<String, IsoMutableList<Handler?>> = IsoMutableMap<String, IsoMutableList<Handler?>>()
    internal var owner: IsolateState<StateMachine>? = null
    protected open fun getThis(): T
    {
            return this as T
    }

    init {
        this.id = id
    }

    companion object {
        fun <T: State<T>> createInstance(id:String) = State<T>(id)
    }
//    public static fun <T: State<T>> createInstance(id:String): State<T> {
//        return State(id)
//    }


    open fun setOwner(ownerMachine: StateMachine) {
        owner = IsolateState{ownerMachine}
    }

    open fun getOwner(): StateMachine? {
        return owner?.access { it }
    }
    open val descendantStates: Collection<out State<*>>
        get() {
            return ArrayList<State<*>>()
        }
    val eventHandler: EventHandler
        get() {
            return owner?.access {  it.path.get(0) as EventHandler}!!
        }
    open val allActiveStates: List<State<*>>
        get() {
            return ArrayList<State<*>>()
        }

    init {
        //LinkedListMultimap.create()
        this.id = id
    }

    fun setLogger(log: ILogger) {
        LOGGER = log
    }

    fun onEnter(onEnterAction: Action): T {
        mOnEnterAction = IsolateState{onEnterAction}
        return getThis()
    }

    fun onExit(onExitAction: Action): T {
        mOnExitAction = IsolateState {onExitAction}
        return getThis()
    }

    fun addHandler(eventName: String, target: State<*>, kind: TransitionKind, guard: Guard): T {
        var eventList = mHandlers.get(eventName)
        if(eventList==null) {
            val list = IsoMutableList<Handler?>()
            list.add(Handler(target, kind, guard))
            eventList = list//mutableListOf(Handler(target, kind, guard))

        }
        else
            eventList.add(Handler(target, kind, guard))
        mHandlers.put(eventName, eventList)
        return getThis()
    }

    fun addHandler(eventName: String, target: State<*>, kind: TransitionKind, action: Action): T {
        var eventList = mHandlers.get(eventName)
        if(eventList==null) {
            val list = IsoMutableList<Handler?>()
            list.add(Handler(target, kind, action))
            eventList =  list   //(Handler(target, kind, action))

        }
        else
            eventList.add(Handler(target, kind, action))
            mHandlers.put(eventName, eventList)
        return getThis()
    }

    fun addHandler(eventName: String, target: State<*>, kind: TransitionKind, action: Action, guard: Guard): T {
        var eventList = mHandlers.get(eventName)
        if(eventList==null) {
            val list = IsoMutableList<Handler?>()
            list.add(Handler(target, kind, action, guard))
            eventList = list//mutableListOf(Handler(target, kind, action, guard))

        }
        else
            eventList.add(Handler(target, kind, action, guard))
        mHandlers.put(eventName, eventList)
        return getThis()
    }

    fun addHandler(eventName: String, target: State<*>, kind: TransitionKind): T {
        var eventList = mHandlers.get(eventName)
        if(eventList==null) {
            val list = IsoMutableList<Handler?>()
            list.add(Handler(target, kind))
            eventList = list//mutableListOf( Handler(target, kind))

        }
        else
            eventList.add( Handler(target, kind))
        mHandlers.put(eventName, eventList)
        return getThis()
    }

    open fun enter(prev: State<*>?, next: State<*>?, payload: Map<String?, Any?>?) {
        LOGGER.debug("[" + owner?.access {  it.name } + "] " + id + " - enter")
        if (mOnEnterAction != null) {
            mOnEnterAction?.access {  it.setPreviousState(prev)}
            mOnEnterAction?.access {  it.setNextState(next)}
            mOnEnterAction?.access {  it.setPayload(payload)}
            mOnEnterAction?.access {  it.run()}
        }
    }

    open fun exit(prev: State<*>?, next: State<*>?, payload: Map<String?, Any?>?) {
        LOGGER.debug("[" + owner?.access { it.name } + "] " + id + " - exit")
        if (mOnExitAction != null) {
            mOnExitAction?.access {  it.setPreviousState(prev)}
            mOnExitAction?.access {  it.setNextState(next)}
            mOnExitAction?.access {  it.setPayload(payload)}
            mOnExitAction?.access {  it.run()}
        }
    }

    internal fun findHandler(event: Event): Handler? {
        for (handler in mHandlers[event.name].orEmpty() ) {
            if (handler!!.evaluate(event)) {
                return handler
            }
        }
        return null
    }

    open fun handleWithOverride(event: Event): Boolean {
        val handler = findHandler(event)
        if (handler != null) {
            LOGGER.debug("[" + owner?.access { it.name } + "] " + id + " - handle Event: " + event.name)
            owner?.access { it.executeHandler(handler, event)}
            return true
        }
        return false
    }

     override fun toString(): String {
        return id
    }

    open fun addParent(stateMachine: StateMachine) {
        // do nothing
    }


}