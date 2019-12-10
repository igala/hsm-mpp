package sample

import org.junit.Assert
import org.junit.Test
import org.mockito.InOrder
import java.util.HashMap
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
import org.mockito.internal.matchers.Not

class BasicStateMachineTest {
    @Test
    fun cannotCreateEmptyStateMachine() {
        try
        {
            val sm = StateMachine(null)
            Assert.fail("NullPointerException should raise when creating a empty StateMachine")
        }
        catch (npe:NullPointerException) {}
    }
    @Test
    fun canChainMethods() {
        val state = MyState("foo")
            .onEnter(mock(Action::class.java))
            .onExit(mock(Action::class.java))
    }
    @Test
    fun initialStateIsEntered() {
        //given:
        val enterAction = mock(Action::class.java)
        val on = MyState("on")
            .onEnter(enterAction)
        val sm = StateMachine(on)
        //when:
        sm.init()
        //then:
        verify(enterAction).run()
    }
    @Test
    fun currentStateIsExited() {
        //given:
        val exitAction = mock(Action::class.java)
        val on = MyState("on")
            .onExit(exitAction)
        val sm = StateMachine(on)
        sm.init()
        //when:
        sm.teardown()
        //then:
        verify(exitAction).run()
    }
    @Test
    fun eventsWithoutPayloadCauseStateTransition() {
        //given:
        val onExitAction = mock(Action::class.java)
        val offEnterAction = mock(Action::class.java)
        val off = MyState("off")
            .onEnter(offEnterAction)
        val on = MyState("on")
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
    fun impossibleTransitionTest() {
        // given:
        val onExitAction = mock(Action::class.java)
        val off = MyState("off")
        val on = MyState("on")
            .addHandler("toggle", off, TransitionKind.External)
            .onExit(onExitAction)
        val sm = StateMachine(on)
        sm.init()
        // when:
        try
        {
            sm.handleEvent("toggle")
            Assert.fail("expected NullpointerException but nothing happnend")
        }
        catch (npe:IllegalStateException) {}
    }
    @Test
    fun eventsWithPayloadCauseStateTransition() {
        //given:
        val offEnterAction = mock(Action::class.java)
        val off = MyState("off")
            .onEnter(offEnterAction)
        val onExitAction = mock(Action::class.java)
        val on = MyState("on")
            .addHandler("toggle", off, TransitionKind.External)
            .onExit(onExitAction)
        val sm = StateMachine(on, off)
        sm.init()
        //when:
        sm.handleEvent("toggle", HashMap<String?, Any>())
        //then:
        verify(onExitAction).run()
        verify(offEnterAction).run()
    }
    @Test
    fun actionsAreCalledBetweenExitAndEnter() {
        val aExit = mock(Action::class.java)
        val aaExit = mock(Action::class.java)
        val bEnter = mock(Action::class.java)
        val bbEnter = mock(Action::class.java)
        val action = mock(Action::class.java)
        val aa = MyState("aa").onExit(aaExit)
        val a = Sub("a", aa).onExit(aExit)
        val bb = MyState("bb").onEnter(bbEnter)
        val b = Sub("b", bb).onEnter(bEnter)
        aa.addHandler("T", bb, TransitionKind.External, action)
        val sm = StateMachine(a, b)
        sm.init()
        sm.handleEvent("T")
        val inOrder = inOrder(aaExit, aExit, action, bEnter, bbEnter)
        inOrder.verify(aaExit).run()
        inOrder.verify(aExit).run()
        inOrder.verify(action).run()
        inOrder.verify(bEnter).run()
        inOrder.verify(bbEnter).run()
    }
    @Test
    fun actionsAreCalledOnTransitionsWithPayload() {
        //given:
        val actionGotCalled = booleanArrayOf(false)
        val toggleAction = object:Action() {
            override fun run() {
                actionGotCalled[0] = true
                assertThat(mPayload, notNullValue())
                Assert.assertTrue(mPayload!!.containsKey("foo"))
                Assert.assertTrue(mPayload!!.get("foo") is String)
                Assert.assertTrue((mPayload!!.get("foo") as String) == "bar")
            }
        }
        val off = MyState("off")
        val on = MyState("on")
            .addHandler("toggle", off, TransitionKind.External, toggleAction)
        val sm = StateMachine(on, off)
        sm.init()
        val payload = HashMap<String?, Any>()
        payload.put("foo", "bar")
        //when:
        sm.handleEvent("toggle", payload)
        //then:
        if (!actionGotCalled[0])
        {
            Assert.fail("action was not called")
        }
    }
    @Test
    fun actionsAreCalledAlwaysWithValidPayload() {
        //given:
        val actionGotCalled = booleanArrayOf(false)
        val toggleAction = object:Action() {
            override fun run() {
                actionGotCalled[0] = true
                assertThat(mPayload, notNullValue())
                Assert.assertTrue(mPayload!!.isEmpty())
            }
        }
        val off = MyState("off")
        val on = MyState("on")
            .addHandler("toggle", off, TransitionKind.External, toggleAction)
        val sm = StateMachine(on, off)
        sm.init()
        //when:
        sm.handleEvent("toggle")
        //then:
        if (!actionGotCalled[0])
        {
            Assert.fail("action was not called")
        }
    }
    @Test
    fun actionsCanBeInternal() {
        //given:
        val onExitAction = mock(Action::class.java)
        val toggleAction = mock(Action::class.java)
        val on = MyState("on")
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
    fun noMatchingStateAvailable() {
        // given:
        val off = MyState("off")
        val on = MyState("on").addHandler("toggle", off, TransitionKind.External)
        val sm = StateMachine(on)
        sm.init()
        // when:
        try
        {
            sm.handleEvent("toggle")
            Assert.fail("expected IllegalStateException since target State was not part of StateMachine")
        }
        catch (e:IllegalStateException) {
            Assert.assertTrue(e != null)
        }
    }
    @Test
    fun canGetPathString() {
        // given:
        val b201 = MyState("b201")
        val b21 = Sub("b21", b201)
        val b1 = Sub("b1", b21)
        val b = Sub("b", b1)
        val bar = Sub("bar", b)
        val a1 = MyState("a1").addHandler("T1", b201, TransitionKind.External)
        val a = Sub("a", a1)
        val foo = Sub("foo", a)
        val sm = StateMachine(foo, bar)
        // when:
        val pathString = sm.pathString
        // then:
        assertThat(pathString, notNullValue())
    }
    @Test
    fun enumTest() {
        // just for the code coverage (^__^)
        val local = TransitionKind.valueOf("Local")
        assertThat(local, equalTo(TransitionKind.Local))
    }
    @Test
    fun emittedEventTestHandledFromTopStateMachine() {
        class SampleState(id:String):State<SampleState>(id) {
            init{
                onEnter(object:Action() {
                    override fun run() {
                        this@SampleState.eventHandler.handleEvent("T1")
                    }
                })
            }
        }
        // given:
        val bAction = mock(Action::class.java)
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
    @Test
    fun getAllActiveStates() {
        // given:
        val a11 = MyState("a11")
        val a22 = MyState("a22")
        val a33 = MyState("a33")
        val a1 = Parallel("a1", StateMachine(a11), StateMachine(a22, a33))
        val a = Sub("a", a1)
        val sm = StateMachine(a)
        sm.init()
        // when:
        val allActiveStates = sm.allActiveStates
        // then:
        assertThat(allActiveStates, hasItems(a, a1, a11, a22))
        assertThat(allActiveStates, Not(hasItems(a33)))
    }
}