package sample
import de.artcom.hsm.*
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito.*
import sample.helper.MyState

class LcaTest {
    @Test
    fun testLowestCommonAncestor1() {
        // given:
        val exitA1 = mock(Action::class.java)
        val exitA = mock(Action::class.java)
        val exitFoo = mock(Action::class.java)
        val a1 = MyState("a1").onExit(exitA1)
        val a = Sub("a", a1).onExit(exitA)
        val foo = Sub("foo", a).onExit(exitFoo)
        val enterB21 = mock(Action::class.java)
        val enterB201 = mock(Action::class.java)
        val enterB1 = mock(Action::class.java)
        val enterB = mock(Action::class.java)
        val enterBar = mock(Action::class.java)
        val b201 = MyState("b201").onEnter(enterB201)
        val b21 = Sub("b21", b201).onEnter(enterB21)
        val b1 = Sub("b1", b21).onEnter(enterB1)
        val b = Sub("b", b1).onEnter(enterB)
        val bar = Sub("bar", b).onEnter(enterBar)
        val sm = StateMachine(foo, bar)
        a1.addHandler("T1", bar, TransitionKind.External)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        val inOrder = inOrder(exitA1, exitA, exitFoo, enterBar, enterB, enterB1, enterB21, enterB201)
        inOrder.verify(exitA1).run()
        inOrder.verify(exitA).run()
        inOrder.verify(exitFoo).run()
        inOrder.verify(enterBar).run()
        inOrder.verify(enterB).run()
        inOrder.verify(enterB1).run()
        inOrder.verify(enterB21).run()
        inOrder.verify(enterB201).run()
    }
    @Test
    fun testLowestCommonAncestor2() {
        // given:
        val exitA1 = mock(Action::class.java)
        val exitA = mock(Action::class.java)
        val exitFoo = mock(Action::class.java)
        val a1 = MyState("a1").onExit(exitA1)
        val a = Sub("a", a1).onExit(exitA)
        val foo = Sub("foo", a).onExit(exitFoo)
        val enterB21 = mock(Action::class.java)
        val enterB201 = mock(Action::class.java)
        val enterB1 = mock(Action::class.java)
        val enterB = mock(Action::class.java)
        val enterBar = mock(Action::class.java)
        val b201 = MyState("b201").onEnter(enterB201)
        val b21 = Sub("b21", b201).onEnter(enterB21)
        val b1 = Sub("b1", b21).onEnter(enterB1)
        val b = Sub("b", b1).onEnter(enterB)
        val bar = Sub("bar", b).onEnter(enterBar)
        val sm = StateMachine(foo, bar)
        a1.addHandler("T1", b201, TransitionKind.External)
        sm.init()
        // when:
        sm.handleEvent("T1")
        // then:
        val inOrder = inOrder(exitA1, exitA, exitFoo, enterBar, enterB, enterB1, enterB21, enterB201)
        inOrder.verify(exitA1).run()
        inOrder.verify(exitA).run()
        inOrder.verify(exitFoo).run()
        inOrder.verify(enterBar).run()
        inOrder.verify(enterB).run()
        inOrder.verify(enterB1).run()
        inOrder.verify(enterB21).run()
        inOrder.verify(enterB201).run()
    }
    @Test
    fun testLowestCommonAncestor3() {
        // given:
        val enterA2 = mock(Action::class.java)
        val enterB2 = mock(Action::class.java)
        val exitM = mock(Action::class.java)
        val a1 = MyState("a1")
        val a2 = MyState("a2")
                .onEnter(enterA2)
        val a = Sub("a", a1, a2)
        val foo = Sub("foo", a)
        val b1 = MyState("b1")
        val b2 = MyState("b2")
                .onEnter(enterB2)
        val b = Sub("b", b1, b2)
        val bar = Sub("bar", b)
        val main = Sub("main", foo, bar)
                .onExit(exitM)
        val sm = StateMachine(main)
        a1.addHandler("B1", b1, TransitionKind.External)
                .addHandler("T1", a2, TransitionKind.External)
        b1.addHandler("T1", a2, TransitionKind.External)
        sm.init()
        // when:
        sm.handleEvent("B1")
        sm.handleEvent("T1")
        // then:
        verify(enterA2).run()
        verifyZeroInteractions(enterB2)
        verifyZeroInteractions(exitM)
    }
}