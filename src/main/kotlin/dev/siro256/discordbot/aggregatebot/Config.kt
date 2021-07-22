package dev.siro256.discordbot.aggregatebot

import dev.siro256.kotlin.consolelib.Console
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.nio.file.Files

object Config {
    private val tempConfig = mutableMapOf<String, Any>(
        "token" to "",
        "post_channel" to 0L,
        "mention_role" to 0L,
        "poll_message" to ""
    )

    val TOKEN by lazy { tempConfig["token"].toString() }
    val POST_CHANNEL by lazy { tempConfig["post_channel"] as Long }
    val MENTION_ROLE by lazy { tempConfig["mention_role"] as Long }
    val POLL_MESSAGE by lazy { tempConfig["poll_message"].toString() }

    init {
        deployConfigFile()

        val configFile = File(".", "config.yml")
        val yaml = Yaml()

        yaml.loadAs(configFile.inputStream(), HashMap<String, Any>()::class.java).forEach { (key, value) ->
            when (key) {
                "token" -> tempConfig["token"] = value.toString()
                "post_channel" -> tempConfig["post_channel"] = value.toString().toLong()
                "mention_role" -> tempConfig["mention_role"] = value.toString().toLong()
                "poll_message" -> tempConfig["poll_message"] = value.toString()
            }
        }
    }

    private fun deployConfigFile() {
        val destinationFile = File(".", "config.yml")

        if (destinationFile.exists()) return
        Console.println("Copying configuration file to current directory.\nPlease setting your configuration.")
        Files.copy(ClassLoader.getSystemResourceAsStream("config.yml")!!, destinationFile.toPath())
        AggregateBot.stopBot()
    }
}
