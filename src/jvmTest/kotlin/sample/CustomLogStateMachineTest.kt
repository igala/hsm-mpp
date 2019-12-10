package sample;

import de.artcom.hsm.Parallel
import de.artcom.hsm.StateMachine
import de.artcom.hsm.Sub
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.internal.matchers.Not
import sample.helper.Logger


class CustomLogStateMachineTest {
    @Test
    fun getAllActiveStates() {
        // given:
        val log = Logger()
        val a11 = MyState("a11")
        val a22 = MyState("a22")
        val a33 = MyState("a33")
        val a1 = Parallel("a1", StateMachine(a11), StateMachine(a22, a33))
        val a = Sub("a", a1)
        a11.setLogger(log)
        val sm = StateMachine(a)
        sm.setLogger(log)
        sm.init()
        // when:
        val allActiveStates = sm.allActiveStates
        // then:
        assertThat(allActiveStates, hasItems(a, a1, a11, a22))
        assertThat(allActiveStates, Not(hasItems(a33)))
    }
}
