package ua.Ldoin.JuicyLuckyWars.Main.Utils.Server;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;

public class JuicyServerUpdater extends BukkitRunnable {

    public static boolean stoped = false;

    public void run() {

        if (stoped)
            return;

        try {

            JuicyServer.init();

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        JuicyServer server = JuicyServer.servers.get(Bukkit.getMotd());

        if (server.getState().equals(JuicyServerStates.INGAME))
            server.setPlayers(Arena.arena.getPlayersInGame());
        else
            server.setPlayers(Bukkit.getOnlinePlayers().size() - Arena.arena.getSpectators().size());

        JuicyServerManager.saveServer(server);

    }
}