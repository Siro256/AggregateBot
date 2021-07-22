package dev.siro256.discordbot.aggregatebot.task

import dev.siro256.discordbot.aggregatebot.AggregateBot
import dev.siro256.discordbot.aggregatebot.Config
import dev.siro256.discordbot.aggregatebot.Emoji
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object PollTask: Runnable {
    override fun run() {
        AggregateBot.BOT.getTextChannelById(Config.POST_CHANNEL)?.sendMessage(
            MessageBuilder().apply {
                append(AggregateBot.BOT.getRoleById(Config.MENTION_ROLE)?.asMention)
                append(Config.POLL_MESSAGE)
                setEmbeds(
                    EmbedBuilder().apply {
                        setColor(0x00FF00)
                        setTitle("${LocalDate.now().format(DateTimeFormatter.ofPattern("MM月dd日"))}の練習")
                        addField(Emoji["o"]!!, "参加する", false)
                        addField(Emoji["x"]!!, "参加しない", false)
                    }.build()
                )
            }.build()
        )!!.queue {
            it.apply {
                addReaction(Emoji["o"]!!).queue()
                addReaction(Emoji["x"]!!).queue()
            }
            AggregateBot.messageId = it.idLong
        }
    }
}
