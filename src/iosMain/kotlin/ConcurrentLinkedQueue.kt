package de.artcom.hsm
import platform.Foundation.NSRecursiveLock
import co.touchlab.stately.collections.IsoMutableList
actual class ConcurrentLinkedQueue<E>{
    private  var lock: NSRecursiveLock = NSRecursiveLock()
    var queue:IsoMutableList<E>
    init {
//        queue = IsoMutableMap<E>()
        queue = IsoMutableList<E>()
    }
    actual open fun add(element:E):Boolean{

        lock.lock()
        queue.add(element)
        lock.unlock()
        return true
    }
    actual fun peek():E?{
        lock.lock()
        if(queue.isEmpty())
            return null
        else {
            var peek: E = queue.get(0)
            lock.unlock()
            return peek
        }
    }
    actual fun poll():E{
        lock.lock()
        var poll = queue.removeAt(0)
        lock.unlock()
        return poll
    }
}