package sample
import de.artcom.hsm.*
import org.hamcrest.Matchers
import org.hamcrest.collection.IsMapContaining
import org.jetbrains.annotations.Nullable
import org.junit.Test
import java.util.HashMap
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.mockito.Mockito.*
import sample.helper.MyState

class EventHandlingTest {
    @Test
    fun runToCompletionTest() {
        // given:
        val rawEgg = MyState("rawEgg")
        val softEgg = MyState("softEgg")
        val hardEgg = MyState("hardEgg")
        val sm = StateMachine(rawEgg, softEgg, hardEgg)
        val onEnterHardEgg = mock(Action::class.java)
        val onEnterSoftEgg = mock(Action::class.java)
        softEgg.onEnter(onEnterSoftEgg)
        hardEgg.onEnter(onEnterHardEgg)
        val boilAction = object:Action() {
            override fun run() {
                sm.handleEvent("boil_too_long")
            }
        }
        rawEgg.addHandler("boil", softEgg, TransitionKind.External, boilAction)
        val boilTooLongAction = mock(Action::class.java)
        rawEgg.addHandler("boil_too_long", hardEgg, TransitionKind.External, boilTooLongAction)
        val boilTooLongAction2 = mock(Action::class.java)
        softEgg.addHandler("boil_too_long", softEgg, TransitionKind.Internal, boilTooLongAction2)
        sm.init()
        // when:
        sm.handleEvent("boil")
        // then:
        verifyZeroInteractions(onEnterHardEgg)
        verifyZeroInteractions(boilTooLongAction)
        verify(onEnterSoftEgg).run()
        verify(boilTooLongAction2).run()
    }
    @Test
    fun dontBubbleUpTest() {
        // given:
        val enterA2 = mock(Action::class.java)
        val enterB = mock(Action::class.java)
        val a1 = MyState("a1")
        val a2 = MyState("a2").onEnter(enterA2)
        val a = Sub("a", a1, a2)
        val b = MyState("b").onEnter(enterB)
        a1.addHandler("T1", a2, TransitionKind.External, object:Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                return payload!!.containsKey("foo")
            }
        })
        a.addHandler("T1", b, TransitionKind.External)
        val sm = StateMachine(a, b)
        sm.init()
        // when:
        val payload = HashMap<String?, Any>()
        payload.put("foo", "bar")
        sm.handleEvent("T1", payload)
        // then:
        verify(enterA2).run()
        verifyZeroInteractions(enterB)
    }
    @Test
    fun bubbleUpTest() {
        // given:
        val enterA2 = mock(Action::class.java)
        val enterB = mock(Action::class.java)
        val a1 = MyState("a1")
        val a2 = MyState("a2").onEnter(enterA2)
        val a = Sub("a", a1, a2)
        val b = MyState("b").onEnter(enterB)
        a1.addHandler("T1", a2, TransitionKind.External, object:Guard {

            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                return payload!!.containsKey("foo")
            }
        })
        a.addHandler("T1", b, TransitionKind.External)
        val sm = StateMachine(a, b)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        verify(enterB).run()
        verifyZeroInteractions(enterA2)
    }
    @Test
    fun handleTransitionWithPayload() {
        // given
        val a1 = MyState("a1")
        val a2 = MyState("a2")
                .onEnter(object:Action() {
            override fun run() {
                assertThat(mPayload, Matchers.hasKey("foo"))
            }
        })
        a1.addHandler("T1", a2, TransitionKind.External)
                .onExit(object:Action() {
                    override fun run() {
                assertThat(mPayload, Matchers.hasKey("foo"))
            }
        })
        val sm = StateMachine(a1, a2)
        sm.init()
        val payload = HashMap<String?, Any>()
        payload.put("foo", "bar")
        // when
        sm.handleEvent("T1", payload)
    }
    @Test
    fun initWithPayload() {
        // given
        val a1Enter = object:Action() {
            override fun run() {
                // then
                assertThat(mPayload, IsMapContaining.hasKey("foo"))
            }
        }
        val a1 = MyState("a1").onEnter(a1Enter)
        val sm = StateMachine(a1)
        val payload = HashMap<String, Any>()
        payload.put("foo", "bar")
        // when
        sm.init(payload)
    }
    @Test
    fun initWithNullPayload() {
        // given
        val a1Enter = mock(Action::class.java)
        val a1 = MyState("a1").onEnter(a1Enter)
        val sm = StateMachine(a1)
        // when
        try
        {
            sm.init(null)
        }
        catch (npe:NullPointerException) {
            fail("StateMachine.init() should instantiate a new payload instead of throwing npe")
        }
        // then
    }
    @Test
    fun teardownWithPayload() {
        // when
        val a1 = MyState("a1")
        val a = Sub("a", a1)
        val b1 = MyState("b1").onEnter(object:Action() {
            override fun run() {
                assertThat(mPayload, IsMapContaining.hasKey("foo"))
            }
        })
        a1.addHandler("T1", b1, TransitionKind.External).onExit(object:Action() {
            override fun run() {
                mPayload?.set("foo",null)

            }
        })
        val b = Sub("b", a, b1)
        val sm = StateMachine(b)
        sm.init()
        sm.handleEvent("T1")
    }
}