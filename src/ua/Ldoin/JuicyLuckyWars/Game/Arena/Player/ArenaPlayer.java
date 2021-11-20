package ua.Ldoin.JuicyLuckyWars.Game.Arena.Player;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class ArenaPlayer {

    public ArenaPlayer(Player player) {

        this.player = player;

        kills = 0;
        deaths = 0;

        luckyBlocks = 0;

        players.put(player, this);

    }

    public static Map<Player, ArenaPlayer> players = new HashMap<>();

    private final Player player;

    private int kills;
    private int deaths;

    private int luckyBlocks;

    public Player getPlayer() {

        return player;

    }

    public int getKills() {

        return kills;

    }

    public int getDeaths() {

        return deaths;

    }

    public int getLuckyBlocks() {

        return luckyBlocks;

    }

    public void addKill() {

        kills++;

    }

    public void addDeath() {

        deaths++;

    }

    public void addLuckyBlock() {

        luckyBlocks++;

    }

    public static void unload(Player p) {

        if (!players.containsKey(p))
            return;

        ArenaPlayerManager.savePlayer(p);
        players.remove(p);

    }
}