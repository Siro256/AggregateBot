package dev.siro256.discordbot.aggregatebot.listener

import dev.siro256.discordbot.aggregatebot.AggregateBot
import dev.siro256.kotlin.consolelib.ConsoleInputEvent
import dev.siro256.kotlin.eventlib.EventHandler

object StopListener {
    @EventHandler
    fun onConsoleInput(event: ConsoleInputEvent) {
        if (event.input == "stop") AggregateBot.stopBot()
    }
}