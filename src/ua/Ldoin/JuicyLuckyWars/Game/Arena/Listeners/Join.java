package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.ScoreboardUpdater;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.*;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player pl = e.getPlayer();

        PPlayer.loadPlayer(pl);
        PPlayer pp = PPlayer.getPPlayer(pl);

        pl.setDisplayName(pp.getDisplayName());

        if ((JuicyServer.servers.get(Bukkit.getMotd())).getState() == JuicyServerStates.INGAME) {

            Arena.arena.initSpectator(pl);
            ScoreboardUpdater.setScoreboard(pl, "game");
            return;

        }

        ScoreboardUpdater.setScoreboard(pl, "wait");
        pl.teleport(e.getPlayer().getWorld().getSpawnLocation());

    }
}