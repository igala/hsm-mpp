package sample
import de.artcom.hsm.*
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import sample.helper.MyState

class SubStateMachineTest {
    @Test
    fun canCreateSubStateMachine() {
        //given:
        val loud = MyState("loud")
        val quiet = MyState("quiet")
        val on = Sub("on", StateMachine(quiet, loud))
        //when:
        val sm = StateMachine(on)
        sm.init()
        //then: no exception
    }
    @Test
    fun canTransitionSubStates() {
        //given:
        val onEnterLoud = mock(Action::class.java)
        val onEnterQuiet = mock(Action::class.java)
        val onEnterOn = mock(Action::class.java)
        val onEnterOff = mock(Action::class.java)
        val loud = MyState("loud")
                .onEnter(onEnterLoud)
        val quiet = MyState("quiet")
                .onEnter(onEnterQuiet)
        quiet.addHandler("volume_up", loud, TransitionKind.External)
        loud.addHandler("volume_down", quiet, TransitionKind.External)
        val on = Sub("on", StateMachine(quiet, loud))
                .onEnter(onEnterOn)
        val off = MyState("off")
                .onEnter(onEnterOff)
        on.addHandler("switched_off", off, TransitionKind.External)
        off.addHandler("switched_on", on, TransitionKind.External)
        val sm = StateMachine(off, on)
        sm.init()
        //when:
        sm.handleEvent("switched_on")
        sm.handleEvent("volume_up")
        sm.handleEvent("switched_off")
        sm.handleEvent("switched_on")
        //then:
        val inOrder = inOrder(onEnterOff, onEnterOn, onEnterQuiet, onEnterLoud)
        inOrder.verify(onEnterOff).run()
        inOrder.verify(onEnterOn).run()
        inOrder.verify(onEnterQuiet).run()
        inOrder.verify(onEnterLoud).run()
        inOrder.verify(onEnterOff).run()
        inOrder.verify(onEnterOn).run()
        inOrder.verify(onEnterQuiet).run()
    }
}