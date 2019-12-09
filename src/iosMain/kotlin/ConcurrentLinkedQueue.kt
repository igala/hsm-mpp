package de.artcom.hsm
import platform.Foundation.NSRecursiveLock

actual class ConcurrentLinkedQueue<E>{
    private lateinit var lock: NSRecursiveLock
    lateinit var queue:MutableList<E>
    actual open fun add(element:E):Boolean{
        lock =NSRecursiveLock()
        lock.lock()
        queue.add(element)
        lock.unlock()
        return true
    }
    actual fun peek():E{
        lock.lock()
        var peek:E= queue.get(0)
        lock.unlock()
        return peek
    }
    actual fun poll():E{
        lock.lock()
        var poll = queue.removeAt(0)
        lock.unlock()
        return poll
    }
}