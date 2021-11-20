package ua.Ldoin.JuicyLuckyWars.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Listeners.Canceler;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Permissions.Listeners;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.SQL.MySQL;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.ScoreboardUpdater;

public class Main extends JavaPlugin {

    public static String prefix;
    public static Main plugin;

    public static MySQL mysql;

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

        mysql = new MySQL(getConfig().getString("MySQL.Host"), getConfig().getInt("MySQL.Port"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"));

        if (mysql.isClosed()) {

            sendMessageToConsole(prefix + "&cError to connect MySQL, check config.yml!");

            if (mysql != null && mysql.isClosed()) {

                Bukkit.getServer().shutdown();
                return;

            }
        }

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

    public void loadListeners() {

        PluginManager p = Bukkit.getPluginManager();

        p.registerEvents(new Canceler(), this);
        p.registerEvents(new Listeners(), this);

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