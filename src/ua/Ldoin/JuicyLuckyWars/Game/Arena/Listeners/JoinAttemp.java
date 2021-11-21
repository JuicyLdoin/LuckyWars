package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;

public class JoinAttemp implements Listener {

    @EventHandler
    public void Login(PlayerLoginEvent e) {

        if (Bukkit.getOnlinePlayers().size() - Arena.arena.getSpectators().size() >= Arena.arena.getMaxPlayers()) {

            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            e.setKickMessage("Ошибка подключения - сервер переполнен!");

            return;

        }

        if (Arena.arena.isStarted() && !e.getPlayer().hasPermission("juicy.spectate")) {

            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            e.setKickMessage("Ошибка подключения - игра начата!");

        }
    }
}