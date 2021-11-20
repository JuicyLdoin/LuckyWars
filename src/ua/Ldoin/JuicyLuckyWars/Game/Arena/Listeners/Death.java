package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class Death implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent e) {

        Player dead = e.getEntity();
        ArenaPlayer ap = ArenaPlayer.players.get(dead.getName());

        if (ap != null) {

            ap.addDeath();

            Arena.arena.removePlayer(ap);
            Arena.arena.end(false);

        }

        Arena.initDeathPlayer(dead);
        
        if (dead.getKiller() != null) {

            ArenaPlayer apk = ArenaPlayer.players.get(dead.getKiller().getName());

            if (apk != null)
                apk.addKill();

            e.setDeathMessage(Main.prefix + "§fИгрок §e" + dead.getKiller().getDisplayName() + " §fубил игрока §e" + dead.getDisplayName());
            return;
            
        }
        
        e.setDeathMessage(Main.prefix + "§fИгрок §e" + dead.getDisplayName() + " §fумер");

    }
}