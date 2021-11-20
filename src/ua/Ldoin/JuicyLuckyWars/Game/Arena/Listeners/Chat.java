package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Time;

public class Chat implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void Chat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        ResultSet set = Main.mysql.query("SELECT * FROM `report` WHERE `Reported`='" + p.getName() + "'");

        try {

            if (set.next() && !set.getString("Mute").isEmpty()) {

                e.setCancelled(true);

                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

                long toUnMute = dateFormat.parse(set.getString("Mute")).getTime() - (new Date()).getTime();

                p.sendMessage("" + Time.makeReadable((int)(toUnMute / 1000L)));
                set.close();

                return;
            }
        } catch (Exception ex) {

            ex.printStackTrace();

        }
        ArenaPlayer ap = ArenaPlayer.getArenaPlayer(p);

        String msg = e.getMessage();
        e.setCancelled(true);

        if (Arena.arena.getSpectators().contains(p)) {

            for (Player pl : Arena.arena.getSpectators())
                pl.sendMessage("§7Наблюдатели >> " + PPlayer.getPPlayer(p).getDisplayGroup().getPrefix() + " §f| §7" + p.getDisplayName() + " §f>> " + msg);

            return;

        }

        if (ap != null) {

            Bukkit.broadcastMessage(chat(p, msg));
            return;

        }

        Bukkit.broadcastMessage(chat(p, msg));
        saveMessage(p, e.getMessage());

    }

    public void saveMessage(Player player, String message) {

        ResultSet set = Main.mysql.query("SELECT * FROM `report` WHERE `Reported`='" + player.getName() + "'");

        try {

            if (set.next()) {

                Main.mysql.update("UPDATE `report` SET `Messages`='" + set
                        .getString("Messages") + Bukkit.getMotd() + ".message=" + message + ";' WHERE `Reported`='" + player
                        .getName() + "'");

                set.close();

            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @EventHandler
    public void Join(PlayerJoinEvent e) {

        Main.mysql.update("UPDATE `report` SET `Messages`='' WHERE `Reported`='" + e.getPlayer().getName() + "'");

    }

    private String chat(Player s, String msg) {

        return PPlayer.getPPlayer(s).getDisplayGroup().getPrefix() + " §f|§f " + s.getDisplayName() + " §f>> " + msg;

    }
}