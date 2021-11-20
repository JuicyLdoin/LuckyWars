package ua.Ldoin.JuicyLuckyWars.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item.LuckyBlockItem;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Listeners.Canceler;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Permissions.*;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.*;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.SQL.*;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.*;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.*;

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

        (new JuicyServerUpdater()).runTaskTimer(this, 0L, 5L);

        sendMessageToConsole(prefix + "&f====================");
        sendMessageToConsole(prefix + "&fJuicyLuckyWars by Ldoin :3");
        sendMessageToConsole(prefix + "&fVersion: 1.0");

        for (World w : Bukkit.getWorlds())
            for (Entity e : w.getEntities())
                if (!(e instanceof Player))
                    e.remove();

        loadListeners();
        sendMessageToConsole(prefix + "&fListeners Loaded!");

        LuckyBlockItem.init();
        LuckyBlock.init();

        Group.addGroups();

        for (Player p : Bukkit.getOnlinePlayers()) {

            PPlayer.loadPlayer(p);
            ScoreboardUpdater.setScoreboard(p, "wait");

        }

        (new ScoreboardUpdater()).runTaskTimer(this, 0L, 5L);

        sendMessageToConsole(prefix + "&fPlugin Enabled!");
        sendMessageToConsole(prefix + "&f====================");

        JuicyServerUpdater.stoped = true;
        JuicyServer server = JuicyServer.servers.get(Bukkit.getMotd());

        if (Bukkit.hasWhitelist())
            server.setState(JuicyServerStates.DEVELOPMENT);
        else
            server.setState(JuicyServerStates.WAITING);

        server.setMaxPlayers(Arena.arena.getMaxPlayers());

        JuicyServerManager.saveServer(server);
        JuicyServerUpdater.stoped = false;

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

        JuicyServerUpdater.stoped = true;
        JuicyServer server = JuicyServer.servers.get(Bukkit.getMotd());

        server.setPlayers(0);
        server.setStatus("disabled");
        server.setState(JuicyServerStates.NULL);

        JuicyServerManager.saveServer(server);

        sendMessageToConsole(prefix + "&f====================");
        sendMessageToConsole(prefix + "&fJuicyLuckyWars by Ldoin :3");
        sendMessageToConsole(prefix + "&fVersion: 1.0");

        for (World w : Bukkit.getWorlds())
            for (Entity e : w.getEntities())
                if (!(e instanceof Player))
                    e.remove();

        for (Player p : Bukkit.getOnlinePlayers()) {

            PPlayerManager.savePlayer(p);
            PPlayer.removePlayer(p);

        }

        (new ScoreboardUpdater()).runTaskTimer(this, 0L, 5L);

        sendMessageToConsole(prefix + "&fPlugin Disabled!");
        sendMessageToConsole(prefix + "&f====================");

    }
}