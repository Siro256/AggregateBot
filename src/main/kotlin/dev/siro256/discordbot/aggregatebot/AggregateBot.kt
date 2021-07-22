package dev.siro256.discordbot.aggregatebot

import dev.siro256.discordbot.aggregatebot.listener.StopListener
import dev.siro256.discordbot.aggregatebot.task.AggregateTask
import dev.siro256.discordbot.aggregatebot.task.PollTask
import dev.siro256.kotlin.consolelib.Console
import dev.siro256.kotlin.eventlib.EventManager
import net.dv8tion.jda.api.JDABuilder
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

object AggregateBot {
    val BOT by lazy { JDABuilder.createDefault(Config.TOKEN).build() }
    private val SCHEDULER = Executors.newSingleThreadScheduledExecutor()
    private val tasks = mutableListOf<ScheduledFuture<*>>()
    var messageId = 0L

    @JvmStatic
    fun main(args: Array<String>) {
        BOT.awaitReady()
        Console.initialize()

        listOf(
            StopListener
        ).forEach { EventManager.registerHandler(it) }
        Console.println("Type \"stop\" if you want to shutdown bot.")

        Console.println("${System.currentTimeMillis()}\n${getMillisOfNextNineOClock()}")

        tasks.add(SCHEDULER.scheduleAtFixedRate(PollTask, getMillisOfNextNineOClock(), 86400000, TimeUnit.MILLISECONDS))
        tasks.add(SCHEDULER.scheduleAtFixedRate(AggregateTask, getMillisOfNextSixteenOClock(), 86400000, TimeUnit.MILLISECONDS))
    }

    fun stopBot() {
        Console.println("Shutdown.")
        tasks.forEach {
            it.cancel(false)
        }
        if (this::BOT.isLazyInitialized) exitProcess(0) else BOT.shutdown()
    }

    private fun getMillisOfNextNineOClock(): Long {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.HOURS).withHour(9).let { return@let if (it.hour >= 9) it.plusDays(1) else it }.toInstant().toEpochMilli() - ZonedDateTime.now().toInstant().toEpochMilli()
    }

    private fun getMillisOfNextSixteenOClock(): Long {
        return Instant.ofEpochMilli(getMillisOfNextNineOClock()).atZone(ZoneId.of("Asia/Tokyo")).plusHours(7).toInstant().toEpochMilli()
    }
}
