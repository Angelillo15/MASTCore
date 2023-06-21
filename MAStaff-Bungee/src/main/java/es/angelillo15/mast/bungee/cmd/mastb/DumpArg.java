package es.angelillo15.mast.bungee.cmd.mastb;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.angelillo15.mast.api.Constants;
import es.angelillo15.mast.api.TextUtils;
import es.angelillo15.mast.api.addons.AddonsManager;
import es.angelillo15.mast.api.cmd.SubCommand;
import es.angelillo15.mast.api.cmd.sender.CommandSender;
import es.angelillo15.mast.bungee.config.Messages;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import net.md_5.bungee.api.ProxyServer;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class DumpArg extends SubCommand {
    @Override
    public String getName() {
        return "dump";
    }

    @Override
    public String getDescription() {
        return "Dumps the plugin's data to a nookure url";
    }

    @Override
    public String getSyntax() {
        return "/mastb dump";
    }

    @Override
    public String getPermission() {
        return "mastb.admin";
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(getPermission())) return;

        JsonObject dump = new JsonObject();

        dump.addProperty("pluginName", "MAStaff");
        dump.addProperty("version", Constants.VERSION);
        dump.addProperty("serverSoftware", ProxyServer.getInstance().getName());
        dump.addProperty("javaVersion", System.getProperty("java.version"));

        dump.addProperty("osName", System.getProperty("os.name"));
        dump.addProperty("osVersion", System.getProperty("os.version"));
        dump.addProperty("osArch", System.getProperty("os.arch"));
        dump.addProperty("cpuCores", Runtime.getRuntime().availableProcessors());
        dump.addProperty("allocatedMemory", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        dump.addProperty("uptime", TextUtils.formatUptime(System.currentTimeMillis()
                - ManagementFactory.getRuntimeMXBean().getStartTime())
        );

        JsonObject git = new JsonObject();
        git.addProperty("branch", Constants.GIT_BRANCH);
        git.addProperty("commit", Constants.COMMIT);
        git.addProperty("user", Constants.COMMIT_USER);
        git.addProperty("time", Constants.COMMIT_TIME);

        dump.add("git", git);

        ArrayList<JsonObject> addons = new ArrayList<>();

        AddonsManager.getAddons().forEach((key, addon) -> {
            JsonObject addonJson = new JsonObject();
            addonJson.addProperty("name", addon.getDescriptionFile().getName());
            addonJson.addProperty("version", addon.getDescriptionFile().getVersion());
            addonJson.addProperty("author", addon.getDescriptionFile().getAuthor());
            addonJson.addProperty("description", addon.getDescriptionFile().getDescription() == null ?
                    "No description" : addon.getDescriptionFile().getDescription()
            );
            addonJson.addProperty("main", addon.getDescriptionFile().getMain());
            addons.add(addonJson);
        });

        dump.add("addons", JsonParser.parseString(addons.toString()));

        ArrayList<JsonObject> plugins = new ArrayList<>();

        ProxyServer.getInstance().getPluginManager().getPlugins().forEach(plugin -> {
            JsonObject pluginJson = new JsonObject();
            pluginJson.addProperty("name", plugin.getDescription().getName());
            pluginJson.addProperty("version", plugin.getDescription().getVersion());
            pluginJson.addProperty("author", plugin.getDescription().getAuthor());
            pluginJson.addProperty("description", plugin.getDescription().getDescription() == null ?
                    "No description" : plugin.getDescription().getDescription()
            );
            pluginJson.addProperty("main", plugin.getDescription().getMain());
            plugins.add(pluginJson);
        });

        dump.add("plugins", JsonParser.parseString(plugins.toString()));

        new Thread(() -> {
            try {
                HttpResponse<String> response = Unirest.post("https://api.pastes.dev/post")
                        .header("Content-Type", "application/json")
                        .body(dump.toString())
                        .asString();

                JsonObject json = JsonParser.parseString(response.getBody()).getAsJsonObject();

                String key = json.get("key").getAsString();
                String url = "https://nookure.com/dump/" + key;

                sender.sendMessage(TextUtils.colorize(Messages.getPrefix() + " &aDump url: &6" + url));
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(TextUtils.colorize(Messages.getPrefix() + " &cError while dumping plugin info"));
            }
        }).start();
    }
}
