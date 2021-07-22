package dev.siro256.discordbot.aggregatebot

import kotlin.reflect.KProperty0

val KProperty0<*>.isLazyInitialized: Boolean
    get() {
        if (this !is Lazy<*>) return true
        return (getDelegate() as Lazy<*>).isInitialized()
    }

val Emoji = mapOf(
    "1" to "1️⃣",
    "2" to "2️⃣",
    "3" to "3️⃣",
    "4" to "4️⃣",
    "5" to "5️⃣",
    "6" to "6️⃣",
    "7" to "7️⃣",
    "8" to "8️⃣",
    "9" to "9️⃣",
    "o" to "⭕",
    "x" to "❌"
)
