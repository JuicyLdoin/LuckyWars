package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServerStates;

public class Timer extends BukkitRunnable {

    public static int timeToEnd;

    public static int timeToStart;

    public void run() {

        if (Bukkit.getOnlinePlayers().size() - Arena.arena.getSpectators().size() >= Arena.arena.getMinPlayers() && timeToStart > 0)
            timeToStart--;

        if (timeToStart == 0) {

            timeToStart = -1;
            Arena.arena.start();

        }

        if (JuicyServer.servers.get(Bukkit.getMotd()) != null && timeToEnd > 0 && JuicyServer.servers.get(Bukkit.getMotd()).getState().equals(JuicyServerStates.INGAME))
            timeToEnd--;

        if (timeToEnd == 0) {

            timeToEnd = Main.plugin.getConfig().getInt("Arena.TimeToEnd");
            Arena.arena.end();

        }
    }
}