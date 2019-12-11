package sample

import de.artcom.hsm.Parallel
import de.artcom.hsm.State
import de.artcom.hsm.StateMachine
import de.artcom.hsm.Sub
import org.junit.Before
import org.junit.Test
import sample.helper.MyState

class ComplexTests {
    private var a1: State<*>? = null
    private var a2: State<*>? = null
    private var a3: State<*>? = null
    private var a: Sub? = null
    private var b1: State<*>? = null
    private var b2: Sub? = null
    private var b21: State<*>? = null
    private var b22: State<*>? = null
    private var b: Sub? = null
    private var c11: State<*>? = null
    private var c12: State<*>? = null
    private var c21: State<*>? = null
    private var c22: State<*>? = null
    private var c1Machine: StateMachine? = null
    private var c2Machine: StateMachine? = null
    private var c: Parallel? = null
    private var stateMachine: StateMachine? = null
    @Before
    fun setUpTest() {
        a1 = MyState("a1")
        a2 = MyState("a2")
        a3 = MyState("a3")
        a = Sub("a", a1, a2, a3)
        b1 = MyState("b1")
        b21 = MyState("b21")
        b22 = MyState("b22")
        b2 = Sub("b2", b21, b22)
        b = Sub("b", b1, b2)
        c11 = MyState("c11")
        c12 = MyState("c12")
        c21 = MyState("c21")
        c22 = MyState("c22")
        c1Machine = StateMachine(c11, c12)
        c2Machine = StateMachine(c21, c22)
        c = Parallel("c", c1Machine, c2Machine)
        stateMachine = StateMachine(a, b, c)
    }

    @Test
    fun complexStateMachineTest1() { //when:
        stateMachine!!.init()
        stateMachine!!.teardown()
        //then: no exceptions
    }
}