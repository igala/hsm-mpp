package de.artcom.hsm.test

import org.junit.Assert
import org.junit.Test
import org.mockito.InOrder
import java.util.HashMap
import java.util.List
import java.util.Map
import de.artcom.hsm.Action
import de.artcom.hsm.Parallel
import de.artcom.hsm.State
import de.artcom.hsm.StateMachine
import de.artcom.hsm.Sub
import de.artcom.hsm.TransitionKind
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItems
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.equalTo
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

class BasicStateMachineTest {
    @Test
    fun cannotCreateEmptyStateMachine() {
        try {
            val sm = StateMachine(null)
            Assert.fail("NullPointerException should raise when creating a empty StateMachine")
        } catch (npe: NullPointerException) {
        }
    }

    @Test
    fun canChainMethods() {
        val state: State = State("foo")
            .onEnter(mock(Action::class.java))
            .onExit(mock(Action::class.java))
    }

    @Test
    fun initialStateIsEntered() { //given:
        val enterAction: Action = mock(Action::class.java)
        val on: State = State("on")
            .onEnter(enterAction)
        val sm = StateMachine(on)
        //when:
        sm.init()
        //then:
        verify(enterAction).run()
    }

    @Test
    fun currentStateIsExited() { //given:
        val exitAction: Action = mock(Action::class.java)
        val on: State = State("on")
            .onExit(exitAction)
        val sm = StateMachine(on)
        sm.init()
        //when:
        sm.teardown()
        //then:
        verify(exitAction).run()
    }

    @Test
    fun eventsWithoutPayloadCauseStateTransition() { //given:
        val onExitAction: Action = mock(Action::class.java)
        val offEnterAction: Action = mock(Action::class.java)
        val off: State = State("off")
            .onEnter(offEnterAction)
        val on: State = State("on")
            .addHandler("toggle", off, TransitionKind.External)
            .onExit(onExitAction)
        val sm = StateMachine(on, off)
        sm.init()
        //when:
        sm.handleEvent("toggle")
        //then:
        verify(onExitAction).run()
        verify(offEnterAction).run()
    }

    @Test
    fun impossibleTransitionTest() { // given:
        val onExitAction: Action = mock(Action::class.java)
        val off = State("off")
        val on: State = State("on")
            .addHandler("toggle", off, TransitionKind.External)
            .onExit(onExitAction)
        val sm = StateMachine(on)
        sm.init()
        // when:
        try {
            sm.handleEvent("toggle")
            Assert.fail("expected NullpointerException but nothing happnend")
        } catch (npe: IllegalStateException) {
        }
    }

    @Test
    fun eventsWithPayloadCauseStateTransition() { //given:
        val offEnterAction: Action = mock(Action::class.java)
        val off: State = State("off")
            .onEnter(offEnterAction)
        val onExitAction: Action = mock(Action::class.java)
        val on: State = State("on")
            .addHandler("toggle", off, TransitionKind.External)
            .onExit(onExitAction)
        val sm = StateMachine(on, off)
        sm.init()
        //when:
        sm.handleEvent("toggle", HashMap<String, Object>())
        //then:
        verify(onExitAction).run()
        verify(offEnterAction).run()
    }

    @Test
    fun actionsAreCalledBetweenExitAndEnter() {
        val aExit: Action = mock(Action::class.java)
        val aaExit: Action = mock(Action::class.java)
        val bEnter: Action = mock(Action::class.java)
        val bbEnter: Action = mock(Action::class.java)
        val action: Action = mock(Action::class.java)
        val aa: State = State("aa").onExit(aaExit)
        val a: Sub = Sub("a", aa).onExit(aExit)
        val bb: State = State("bb").onEnter(bbEnter)
        val b: Sub = Sub("b", bb).onEnter(bEnter)
        aa.addHandler("T", bb, TransitionKind.External, action)
        val sm = StateMachine(a, b)
        sm.init()
        sm.handleEvent("T")
        val inOrder: InOrder = inOrder(aaExit, aExit, action, bEnter, bbEnter)
        inOrder.verify(aaExit).run()
        inOrder.verify(aExit).run()
        inOrder.verify(action).run()
        inOrder.verify(bEnter).run()
        inOrder.verify(bbEnter).run()
    }

    @Test
    fun actionsAreCalledOnTransitionsWithPayload() { //given:
        val actionGotCalled = booleanArrayOf(false)
        val toggleAction: Action = object : Action() {
            @Override
            fun run() {
                actionGotCalled[0] = true
                assertThat(mPayload, notNullValue())
                Assert.assertTrue(mPayload.containsKey("foo"))
                Assert.assertTrue(mPayload.get("foo") is String)
                Assert.assertTrue((mPayload.get("foo") as String).equals("bar"))
            }
        }
        val off = State("off")
        val on: State = State("on")
            .addHandler("toggle", off, TransitionKind.External, toggleAction)
        val sm = StateMachine(on, off)
        sm.init()
        val payload: Map<String, Object> = HashMap<String, Object>()
        payload.put("foo", "bar")
        //when:
        sm.handleEvent("toggle", payload)
        //then:
        if (!actionGotCalled[0]) {
            Assert.fail("action was not called")
        }
    }

    @Test
    fun actionsAreCalledAlwaysWithValidPayload() { //given:
        val actionGotCalled = booleanArrayOf(false)
        val toggleAction: Action = object : Action() {
            @Override
            fun run() {
                actionGotCalled[0] = true
                assertThat(mPayload, notNullValue())
                Assert.assertTrue(mPayload.isEmpty())
            }
        }
        val off = State("off")
        val on: State = State("on")
            .addHandler("toggle", off, TransitionKind.External, toggleAction)
        val sm = StateMachine(on, off)
        sm.init()
        //when:
        sm.handleEvent("toggle")
        //then:
        if (!actionGotCalled[0]) {
            Assert.fail("action was not called")
        }
    }

    @Test
    fun actionsCanBeInternal() { //given:
        val onExitAction: Action = mock(Action::class.java)
        val toggleAction: Action = mock(Action::class.java)
        val on = State("on")
        on.addHandler("toggle", on, TransitionKind.Internal, toggleAction)
            .onExit(onExitAction)
        val sm = StateMachine(on)
        sm.init()
        //when:
        sm.handleEvent("toggle")
        //then:
        verify(toggleAction).run()
        verifyZeroInteractions(onExitAction)
    }

    @Test
    fun noMatchingStateAvailable() { // given:
        val off = State("off")
        val on: State = State("on").addHandler("toggle", off, TransitionKind.External)
        val sm = StateMachine(on)
        sm.init()
        // when:
        try {
            sm.handleEvent("toggle")
            Assert.fail("expected IllegalStateException since target State was not part of StateMachine")
        } catch (e: IllegalStateException) {
            Assert.assertTrue(e != null)
        }
    }

    @Test
    fun canGetPathString() { // given:
        val b201 = State("b201")
        val b21 = Sub("b21", b201)
        val b1 = Sub("b1", b21)
        val b = Sub("b", b1)
        val bar = Sub("bar", b)
        val a1: State = State("a1").addHandler("T1", b201, TransitionKind.External)
        val a = Sub("a", a1)
        val foo = Sub("foo", a)
        val sm = StateMachine(foo, bar)
        // when:
        val pathString: String = sm.getPathString()
        // then:
        assertThat(pathString, notNullValue())
    }

    @Test
    fun enumTest() { // just for the code coverage (^__^)
        val local: TransitionKind = TransitionKind.valueOf("Local")
        assertThat(local, equalTo(TransitionKind.Local))
    }

    @Test
    fun emittedEventTestHandledFromTopStateMachine() {
        class SampleState(id: String?) : State<SampleState?>(id) {
            init {
                onEnter(object : Action() {
                    @Override
                    fun run() {
                        this@SampleState.getEventHandler().handleEvent("T1")
                    }
                })
            }
        }
        // given:
        val bAction: Action = mock(Action::class.java)
        val a1 = SampleState("a1")
        val a = Sub("a", a1)
        val b = Sub("b", a)
        b.addHandler("T1", b, TransitionKind.Internal, bAction)
        val c = Sub("c", b)
        val stateMachine = StateMachine(c)
        // when:
        stateMachine.init()
        // then:
        verify(bAction).run()
    }

    // given:
    @get:Test
    val allActiveStates:
            // when:
            // then:
            Unit
        get() { // given:
            val a11 = State("a11")
            val a22 = State("a22")
            val a33 = State("a33")
            val a1 = Parallel("a1", StateMachine(a11), StateMachine(a22, a33))
            val a = Sub("a", a1)
            val sm = StateMachine(a)
            sm.init()
            // when:
            val allActiveStates: List<State<*>> = sm.getAllActiveStates()
            // then:
            assertThat(allActiveStates, hasItems(a, a1, a11, a22))
            assertThat(allActiveStates, not(hasItems(a33)))
        }
}