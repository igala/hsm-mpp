package sample


import de.artcom.hsm.*
import org.junit.Test
import java.util.HashMap
import org.mockito.Mockito.*
import sample.helper.MyState

class GuardTest {
    @Test
    fun testFirstGuard() {
        // given:
        val enterA2 = mock(Action::class.java)
        val enterA3 = mock(Action::class.java)
        val a1 = MyState("a1")
        val a2 = MyState("a2").onEnter(enterA2)
        val a3 = MyState("a3").onEnter(enterA3)
        a1.addHandler("T1", a2, TransitionKind.External, object : Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                    val foo = payload?.get("foo") as Boolean
                    return foo
                }

        }).addHandler("T1", a3, TransitionKind.External, object:Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                val foo = payload!!.get("foo") as Boolean
                return !foo
            }
        })
        val sm = StateMachine(a1, a2, a3)
        sm.init()
        //when:
        val payload = HashMap<String?, Any?>()
        payload.put("foo", true)
        sm.handleEvent("T1", payload)
        //then:
        verify(enterA2).run()
        verifyZeroInteractions(enterA3)
    }
    @Test
    fun testSecondGuard() {
        // given:
        val enterA2 = mock(Action::class.java)
        val enterA3 = mock(Action::class.java)
        val a1 = MyState("a1")
        val a2 = MyState("a2").onEnter(enterA2)
        val a3 = MyState("a3").onEnter(enterA3)
        a1.addHandler("T1", a2, TransitionKind.External, object:Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                val foo = payload?.get("foo") as Boolean
                return foo
            }
        }).addHandler("T1", a3, TransitionKind.External, object:Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                val foo = payload?.get("foo") as Boolean
                return !foo
            }
        })
        val sm = StateMachine(a1, a2, a3)
        sm.init()
        //when:
        val payload = HashMap<String?, Any?>()
        payload.put("foo", false)
        sm.handleEvent("T1", payload)
        //then:
        verify(enterA3).run()
        verifyZeroInteractions(enterA2)
    }
    @Test
    fun createHandlerWithActionAndGuard() {
        // given:
        val a1Action = mock(Action::class.java)
        val a = MyState("a")
        a.addHandler("T1", a, TransitionKind.Internal, a1Action, object:Guard {
            override fun evaluate(payload: Map<String?, Any?>?): Boolean {
                return payload!!.containsKey("foo")
            }
        })
        val sm = StateMachine(a)
        sm.init()
        // when:
        val payload = HashMap<String?, Any?>()
        payload.put("foo", "bar")
        sm.handleEvent("T1", payload)
        // then:
        verify(a1Action).run()
    }
}