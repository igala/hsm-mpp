package sample
import de.artcom.hsm.*
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito.*
import sample.helper.MyState

class ParallelStateMachineTest {
    @Test
    fun canCreateParallelMyState() {
        // given:
        val capsOn = MyState("caps_on")
        val capsOff = MyState("caps_off")
        val capsStateMachine = StateMachine(capsOff, capsOn)
        val numOn = MyState("num_on")
        val numOff = MyState("num_off")
        val numStateMachine = StateMachine(numOff, numOn)
        val keyboardOn = Parallel("keyboard_on", capsStateMachine, numStateMachine)
        val sm = StateMachine(keyboardOn)
        // when:
        sm.init()
        System.out.println(sm.toString())
        // then:
        // no exception
    }
    @Test
    fun canSwitchStatesInParallel() {
        // given:
        val onEnterCapsOn = mock(Action::class.java)
        val onEnterCapsOff = mock(Action::class.java)
        val onEnterNumOn = mock(Action::class.java)
        val onEnterNumOff = mock(Action::class.java)
        val onEnterKeyboardOn = mock(Action::class.java)
        val onEnterKeyboardOff = mock(Action::class.java)
        val capsOn = MyState("caps_on")
                .onEnter(onEnterCapsOn)
        val capsOff = MyState("caps_off")
                .onEnter(onEnterCapsOff)
        val capsStateMachine = StateMachine(capsOff, capsOn)
        capsOn.addHandler("capslock", capsOff, TransitionKind.External)
        capsOff.addHandler("capslock", capsOn, TransitionKind.External)
        val numOn = MyState("num_on")
                .onEnter(onEnterNumOn)
        val numOff = MyState("num_off")
                .onEnter(onEnterNumOff)
        val numStateMachine = StateMachine(numOff, numOn)
        numOn.addHandler("numlock", numOff, TransitionKind.External)
        numOff.addHandler("numlock", numOn, TransitionKind.External)
        val keyboardOn = Parallel("keyboard_on", capsStateMachine, numStateMachine)
                .onEnter(onEnterKeyboardOn)
        val keyboardOff = MyState("keyboard_off")
                .onEnter(onEnterKeyboardOff)
        val sm = StateMachine(keyboardOff, keyboardOn)
        keyboardOn.addHandler("unplug", keyboardOff, TransitionKind.External)
        keyboardOff.addHandler("plug", keyboardOn, TransitionKind.External)
        sm.init()
        // when:
        sm.handleEvent("plug")
        sm.handleEvent("capslock")
        sm.handleEvent("capslock")
        sm.handleEvent("numlock")
        sm.handleEvent("unplug")
        sm.handleEvent("capslock")
        sm.handleEvent("plug")
        // then:
        val inOrder = inOrder(onEnterCapsOff, onEnterCapsOn, onEnterKeyboardOff,
                onEnterKeyboardOn, onEnterNumOff, onEnterNumOn)
        inOrder.verify(onEnterKeyboardOff).run()
        inOrder.verify(onEnterKeyboardOn).run()
        inOrder.verify(onEnterCapsOff).run()
        inOrder.verify(onEnterNumOff).run()
        inOrder.verify(onEnterCapsOn).run()
        inOrder.verify(onEnterCapsOff).run()
        inOrder.verify(onEnterNumOn).run()
        inOrder.verify(onEnterKeyboardOff).run()
        inOrder.verify(onEnterKeyboardOn).run()
        inOrder.verify(onEnterCapsOff).run()
        inOrder.verify(onEnterNumOff).run()
    }
    @Test
    fun anotherParallelStateTest() {
        // given:
        val p11Enter = mock(Action::class.java)
        val p21Enter = mock(Action::class.java)
        val p11 = MyState("p11").onEnter(p11Enter)
        val p21 = MyState("p21").onEnter(p21Enter)
        val s1 = MyState("s1")
        val p1 = StateMachine(p11)
        val p2 = StateMachine(p21)
        val s2 = Parallel("s2", p1, p2)
        val s = Sub("s", s1, s2).addHandler("T1", p21, TransitionKind.External)
        val sm = StateMachine(s)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        verify(p11Enter).run()
        verify(p21Enter).run()
    }
    @Test
    fun parallelStatesCanEmitEventInEnter() {
        // given:
        val p1 = MyState("p1")
        val p2 = MyState("p2")
        val p1Enter = object:Action() {
            override fun run() {
                p1.eventHandler.handleEvent("foo")
            }
        }
        val p2Action = mock(Action::class.java)
        p1.onEnter(p1Enter)
        p2.addHandler("foo", p2, TransitionKind.Internal, p2Action)
        val p = Parallel("p",
                StateMachine(p1),
                StateMachine(p2)
        )
        val sm = StateMachine(p)
        // when:
        sm.init()
        // then:
        verify(p2Action).run()
    }
    @Test
    fun parallelStatesCanEmitEventInExitAndHandleAction() {
        // given:
        val p1 = MyState("p1")
        val p2 = MyState("p2")
        val p1Action = object:Action() {
            override fun run() {
                p1.eventHandler.handleEvent("foo")
            }
        }
        val p2Action = mock(Action::class.java)
        p1.onExit(p1Action)
        p2.addHandler("foo", p2, TransitionKind.Internal, p2Action)
        val p = Parallel("p",
                StateMachine(p1),
                StateMachine(p2)
        )
        val sm = StateMachine(p)
        // when:
        sm.init()
        sm.teardown()
        // then:
        verify(p2Action).run()
    }
    @Test
    fun parallelStatesCanEmitEventInExitButIsNotHandledInFinishedStates() {
        // given:
        val p1 = MyState("p1")
        val p2 = MyState("p2")
        val p1Action = object:Action() {
            override fun run() {
                p1.eventHandler.handleEvent("foo")
            }
        }
        val p2Action = mock(Action::class.java)
        p1.onExit(p1Action)
        p2.addHandler("foo", p2, TransitionKind.Internal, p2Action)
        val p = Parallel("p",
                StateMachine(p2),
                StateMachine(p1)
        )
        val sm = StateMachine(p)
        // when:
        sm.init()
        sm.teardown()
        // then:
        verify(p2Action, never()).run()
    }
}