package dev.siro256.discordbot.aggregatebot.task

import dev.siro256.discordbot.aggregatebot.AggregateBot
import dev.siro256.discordbot.aggregatebot.Config
import dev.siro256.discordbot.aggregatebot.Emoji
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object AggregateTask: Runnable {
    override fun run() {
        if (AggregateBot.messageId == 0L) return

        val agreeReaction = AggregateBot.BOT.getTextChannelById(Config.POST_CHANNEL)
            ?.retrieveMessageById(AggregateBot.messageId)
            ?.complete()?.reactions?.first { it.reactionEmote.isEmoji && it.reactionEmote.emoji == Emoji["o"]!! }

        AggregateBot.BOT.getTextChannelById(Config.POST_CHANNEL)?.sendMessage(
            MessageBuilder().apply {
                append(AggregateBot.BOT.getRoleById(Config.MENTION_ROLE)?.asMention)
                append("集計結果:")
                setEmbeds(
                    EmbedBuilder().apply {
                        setColor(0x00FFFF)
                        setTitle("${LocalDate.now().format(DateTimeFormatter.ofPattern("MM月dd日"))}の練習")
                        addField("参加者数", agreeReaction?.count?.minus(1).toString(), false)
                        agreeReaction?.retrieveUsers()!!.complete().apply { remove(AggregateBot.BOT.selfUser) }.chunked(5).forEach {
                            addField("参加者", StringBuilder().apply { it.forEach { appendLine(it.asMention) } }.toString(), true)
                        }
                    }.build()
                )
            }.build()
        )!!.queue()

        AggregateBot.messageId = 0L
    }
}
