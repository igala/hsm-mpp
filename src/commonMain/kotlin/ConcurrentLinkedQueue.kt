package de.artcom.hsm

expect class ConcurrentLinkedQueue<E>(){

    open fun add(element: E):Boolean
    fun peek():E
    fun poll():E

}