package de.artcom.hsm

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
enum class TransitionKind {
    External, Local, Internal
}