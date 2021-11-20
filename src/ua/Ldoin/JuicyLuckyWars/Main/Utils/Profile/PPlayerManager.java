package ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class PPlayerManager {

    public static boolean playerExists(String name) {

        try {

            ResultSet rs = Main.mysql.query("SELECT * FROM `players` WHERE  `name` = '" + name + "'");

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

        Main.mysql.update("INSERT INTO `players` VALUES('" + name + "','player',1,0,0,0,'')");

    }

    public static ResultSet getPlayer(String name) {

        if (playerExists(name))
            return Main.mysql.query("SELECT * FROM `players` WHERE `name` = '" + name + "'");

        return null;

    }

    public static void savePlayer(Player p) {

        if (!playerExists(p.getName()))
            createPlayer(p.getName());

        PPlayer pl = PPlayer.getPPlayer(p);

        if (pl == null)
            return;

        Main.mysql.update("UPDATE `players` SET `Rank`='" + pl.getGroup().getName() + "', `Level`=" + pl.getLevel() + ", " +
                "`XP`=" + pl.getXP() + ", `Gold`=" + pl.getGold() + ", `Diamonds`=" + pl.getDiamonds() + " WHERE `name`='" + p.getName() + "'");

    }
}