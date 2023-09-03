package es.angelillo15.mast.velocity.cmd.mastv

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.inject.Inject
import com.velocitypowered.api.proxy.ProxyServer
import es.angelillo15.mast.api.Constants
import es.angelillo15.mast.api.TextUtils
import es.angelillo15.mast.api.addons.LegacyAddonsManager
import es.angelillo15.mast.api.cmd.SubCommand
import es.angelillo15.mast.api.cmd.sender.CommandSender
import es.angelillo15.mast.api.config.velocity.Messages
import es.angelillo15.mast.api.thread.execute
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import java.lang.management.ManagementFactory


class DumpArg : SubCommand() {
  @Inject
  private lateinit var proxy: ProxyServer
  override val name: String
    get() = "dump"
  override val description: String
    get() = "Dumps the plugin's data to a nookure url"
  override val syntax: String
    get() = "/mastv dump"
  override val permission: String
    get() = "mast.admin"

  override fun onCommand(sender: CommandSender, label: String, args: Array<String>) {
    val dump = JsonObject()

    dump.addProperty("pluginName", "MAStaff-Velocity")
    dump.addProperty("version", Constants.VERSION)
    dump.addProperty("serverSoftware", proxy.version.name);
    dump.addProperty("javaVersion", System.getProperty("java.version"))

    dump.addProperty("osName", System.getProperty("os.name"))
    dump.addProperty("osVersion", System.getProperty("os.version"))
    dump.addProperty("osArch", System.getProperty("os.arch"))
    dump.addProperty("cpuCores", Runtime.getRuntime().availableProcessors())
    dump.addProperty("allocatedMemory", Runtime.getRuntime().maxMemory() / 1024 / 1024)
    dump.addProperty(
            "uptime", TextUtils.formatUptime(
            System.currentTimeMillis()
                    - ManagementFactory.getRuntimeMXBean().startTime
    )
    )

    val git = JsonObject()
    git.addProperty("branch", Constants.GIT_BRANCH)
    git.addProperty("commit", Constants.COMMIT)
    git.addProperty("user", Constants.COMMIT_USER)
    git.addProperty("time", Constants.COMMIT_TIME)

    dump.add("git", git)

    val addons = ArrayList<JsonObject>()

    LegacyAddonsManager.getAddons().forEach { (_, addon) ->
      val addonJson = JsonObject()
      addonJson.addProperty("name", addon.descriptionFile.name)
      addonJson.addProperty("version", addon.descriptionFile.version)
      addonJson.addProperty("author", addon.descriptionFile.author)

      addonJson.addProperty(
              "description",
              if (addon.descriptionFile
                              .description == null
              ) "No description" else addon.descriptionFile.description
      )
      addonJson.addProperty("main", addon.descriptionFile.main)
      addons.add(addonJson)
    }

    dump.add("addons", JsonParser.parseString(addons.toString()))

    val plugins = ArrayList<JsonObject>()

    proxy.pluginManager.plugins.forEach { container ->
      val pluginJson = JsonObject()

      pluginJson.addProperty("name", container.description.id)
      pluginJson.addProperty("version", container.description.version.get())
      pluginJson.addProperty("author", container.description.authors.joinToString(", ") { it })
      pluginJson.addProperty(
              "description",
              if (!container.description.description.isPresent)
                "No description"
              else
                container.description.description.get()
      )
      pluginJson.addProperty("main", container.instance.javaClass.packageName)
      plugins.add(pluginJson)
    }

    dump.add("plugins", JsonParser.parseString(plugins.toString()))

    execute {
      try {
        val response: HttpResponse<String> = Unirest.post("https://api.pastes.dev/post")
                .header("Content-Type", "application/json")
                .body(dump.toString())
                .asString()
        val json = JsonParser.parseString(response.getBody()).asJsonObject
        val key = json["key"].asString
        val url = "https://nookure.com/dump/$key"

        sender!!.sendMessage(
                TextUtils.simpleColorize(
                        Messages.prefix() +
                                " &aDump url: &6 <click:open_url:'${url}'>${url}</click>"
                )
        )
      } catch (e: Exception) {
        e.printStackTrace()
        sender!!.sendMessage(TextUtils.simpleColorize(Messages.prefix() + " &cError while dumping plugin info"))
      }
    }
  }
}

