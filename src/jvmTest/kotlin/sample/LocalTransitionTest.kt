package sample
import de.artcom.hsm.*
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito.*
import sample.helper.MyState

class LocalTransitionTest {
    @Test
    fun canExecuteLocalTransition() {
        // given:
        val sEnter = mock(Action::class.java)
        val s1Enter = mock(Action::class.java)
        val s1 = MyState("s1").onEnter(s1Enter)
        val s = Sub("s", s1).onEnter(sEnter).addHandler("T1", s1, TransitionKind.Local)
        val sm = StateMachine(s)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        verify(sEnter, times(1)).run()
        verify(s1Enter, times(2)).run()
    }
    @Test
    fun canExecuteLocalTransitionToAncestorState() {
        // given:
        val sEnter = mock(Action::class.java)
        val s1Enter = mock(Action::class.java)
        val s1 = MyState("s1").onEnter(s1Enter)
        val s2 = MyState("s2")
        val s = Sub("s", s1, s2).onEnter(sEnter)
        s1.addHandler("T1", s2, TransitionKind.External)
        s2.addHandler("T2", s, TransitionKind.Local)
        val sm = StateMachine(s)
        sm.init()
        // when:
        sm.handleEvent("T1")
        sm.handleEvent("T2")
        // then:
        verify(sEnter, times(1)).run()
        verify(s1Enter, times(2)).run()
    }
    @Test
    fun canExecuteLocalTransitionWhichResultsInExternal() {
        // given:
        val sExit = mock(Action::class.java)
        val s1Exit = mock(Action::class.java)
        val b1Enter = mock(Action::class.java)
        val s1 = MyState("s1").onExit(s1Exit)
        val s = Sub("s", s1).onExit(sExit)
        val b1 = MyState("b1").onEnter(b1Enter)
        val a = Sub("a", s, b1)
        val sm = StateMachine(a)
        s.addHandler("T1", b1, TransitionKind.Local)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        val inOrder = inOrder(s1Exit, sExit, b1Enter)
        inOrder.verify(s1Exit).run()
        inOrder.verify(sExit).run()
        inOrder.verify(b1Enter).run()
    }
}