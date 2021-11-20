package ua.Ldoin.JuicyLuckyWars.Game.Arena.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class ArenaPlayerManager {

    public static boolean playerExists(String name) {

        try {

            ResultSet rs = Main.mysql.query("SELECT * FROM `luckywars_players` WHERE  `name` = '" + name + "'");

            if (rs.next() && rs.getString("name") != null) {

                rs.close();
                return true;

            }

            rs.close();
            return false;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }
    }

    public static void createPlayer(String name) {

        if (!playerExists(name))
            Main.mysql.update("INSERT INTO `luckywars_players` VALUES('" + name + "',0,0,0)");

    }

    public static ResultSet getPlayer(String name) {

        if (playerExists(name))
            return Main.mysql.query("SELECT * FROM `luckywars_players` WHERE  `name` = '" + name + "'");

        return null;

    }

    public static void savePlayer(Player p) {

        if (!playerExists(p.getName()))
            createPlayer(p.getName());

        ArenaPlayer pl = ArenaPlayer.players.get(p.getName());

        if (pl == null)
            return;

        int[] data = getData(p);

        if (data == null)
            return;

        int kills = data[0] + pl.getKills();
        int deaths = data[1] + pl.getDeaths();
        int luckyblocks = data[2] + pl.getLuckyBlocks();

        Main.mysql.update("UPDATE `luckywars_players` SET `Kills`='" + kills + "', `Deaths`=" + deaths + ", `LuckyBlocks`=" + luckyblocks + " WHERE `name`=' " + p
                .getName() + "'");

    }

    public static void savePlayer(Player p, boolean win) {

        if (!playerExists(p.getName()))
            createPlayer(p.getName());

        ArenaPlayer pl = ArenaPlayer.players.get(p.getName());

        if (pl == null)
            return;

        int[] data = getData(p);

        if (data == null)
            return;

        int kills = data[0] + pl.getKills();
        int deaths = data[1] + pl.getDeaths();
        int luckyblocks = data[2] + pl.getLuckyBlocks();

        int wins = data[3];
        int winStreak = data[4];

        if (win) {

            wins++;
            winStreak++;

        } else
            winStreak = 0;

        Main.mysql.update("UPDATE `luckywars_players` SET `Kills`='" + kills + "', `Deaths`=" + deaths + ", `LuckyBlocks`=" + luckyblocks + ", `Wins`=" + wins + ", `WinsStreak`=" + winStreak + " WHERE `name`='" + p
                .getName() + "'");

    }

    private static int[] getData(Player p) {

        ResultSet player = getPlayer(p.getName());

        if (player == null)
            return null;

        try {

            if (player.next())
                return new int[] { player.getInt("Kills"), player.getInt("Deaths"), player
                        .getInt("LuckyBlocks"), player.getInt("Wins"), player.getInt("WinStreak") };

        } catch (Exception e) {

            e.printStackTrace();
            return new int[] { 0, 0, 0, 0, 0 };

        }

        return null;

    }
}