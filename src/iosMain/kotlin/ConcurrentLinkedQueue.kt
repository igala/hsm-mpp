package de.artcom.hsm
import platform.Foundation.NSRecursiveLock
import co.touchlab.stately.collections.IsoMutableList
actual class ConcurrentLinkedQueue<E>{

    var queue:IsoMutableList<E>
    init {
//        queue = IsoMutableMap<E>()
        queue = IsoMutableList<E>()
    }
    actual open fun add(element:E):Boolean{


        queue.add(element)

        return true
    }
    actual fun peek():E?{

        if(queue.isEmpty())
            return null
        else {
            var peek: E = queue.get(0)

            return peek
        }
    }
    actual fun poll():E{

        var poll = queue.removeAt(0)

        return poll
    }
}