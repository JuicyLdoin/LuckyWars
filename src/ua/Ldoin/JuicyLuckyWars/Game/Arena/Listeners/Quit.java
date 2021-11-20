package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;

public class Quit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent e) {

        if (!Arena.arena.isStarted())
            return;

        Player p = e.getPlayer();

        if (Arena.arena.getSpectators().contains(p)) {

            for (Player p2 : Arena.arena.getSpectators())
                p2.sendMessage("§7Наблюдатели >> " + PPlayer.getPPlayer(p).getGroup().getPrefix() + " §f| §e" + p.getDisplayName() + " §fвышел!");

            e.setQuitMessage("");

        }

        ArenaPlayer ap = ArenaPlayer.players.get(p.getName());
        Arena.arena.getPlayers().remove(ap);

        if (!Arena.arena.isStarted())
            e.setQuitMessage(Main.replace(Main.plugin.getConfig().getString("QuitMessage"), p));

        if (Arena.arena.getPlayers().size() <= 1)
            Arena.arena.end();

        ArenaPlayer.unload(p);

    }
}