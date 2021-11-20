package ua.Ldoin.JuicyLuckyWars.Main.Utils.Server;

import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JuicyServerManager {

    public static boolean serverExists(String name) {

        try {

            ResultSet rs = Main.mysql.query("SELECT * FROM `servers` WHERE  `name` = '" + name + "'");

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

    public static void createServer(String name) {

        if (!serverExists(name))
            Main.mysql.update("INSERT INTO `servers` VALUES('" + name + "','',0,0,'','')");

    }

    public static void saveServer(JuicyServer server) {

        if (server == null)
            return;

        createServer(server.getName());

        Main.mysql.update("UPDATE `servers` SET `state`='" + server.getStatus() + "', `players`=" + server.getPlayers() + ", `maxplayers`=" + server
                .getMaxPlayers() + ", `gamestate`='" + server.getState().toString() + "', `map`='" + server
                .getMap() + "' WHERE `name`='" + server.getName() + "'");

    }
}