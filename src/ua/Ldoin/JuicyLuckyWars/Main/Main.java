package ua.Ldoin.JuicyLuckyWars.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.ScoreboardUpdater;

public class Main extends JavaPlugin {

    public static String prefix;
    public static Main plugin;

    public void onLoad() {

        Configuration.ConfigurationUtils.init();

    }

    public void onEnable() {

        plugin = this;

        if (!getDataFolder().isDirectory()) {

            getDataFolder().mkdirs();
            saveDefaultConfig();

        }

        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));

        sendMessageToConsole(prefix + "&f====================");
        sendMessageToConsole(prefix + "&fJuicyLuckyWars by Ldoin :3");
        sendMessageToConsole(prefix + "&fVersion: 1.0");

        for (World w : Bukkit.getWorlds())
            for (Entity e : w.getEntities())
                if (!(e instanceof Player))
                    e.remove();

        (new ScoreboardUpdater()).runTaskTimer(this, 0L, 5L);

        sendMessageToConsole(prefix + "&fPlugin Enabled!");
        sendMessageToConsole(prefix + "&f====================");

    }

    public static void sendMessageToConsole(String message) {

        Bukkit.getConsoleSender().sendMessage(message.replace("&", "§"));

    }

    public static String replace(String str, Player p) {



        return str;

    }

    public void onDisable() {



    }
}