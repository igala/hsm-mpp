package de.artcom.hsm

expect class ConcurrentLinkedQueue<E>(){

    fun add(element: E):Boolean
    fun peek():E?
    fun poll():E

}