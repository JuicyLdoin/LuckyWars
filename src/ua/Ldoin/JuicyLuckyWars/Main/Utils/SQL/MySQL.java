package ua.Ldoin.JuicyLuckyWars.Main.Utils.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class MySQL {

    public MySQL(String host, int port, String database, String user, String password) {

        this.Host = host;
        this.port = port;
        this.Database = database;
        this.Username = user;
        this.Password = password;

        connect();

    }

    private final String Host;
    private final int port;
    private final String Database;
    private final String Username;
    private final String Password;

    private Connection con;

    public PreparedStatement sql;

    public void connect() {

        try {

            this.con = DriverManager.getConnection("jdbc:mysql://" + this.Host + ":" + this.port + "/" + this.Database + "?autoReconnect=true&verifyServerCertificate=false&useSSL=false", this.Username, this.Password);
            Main.sendMessageToConsole(Main.prefix + "&aMySQL connected");

        } catch (SQLException e) {

            Main.sendMessageToConsole(Main.prefix + "&cThe connection to MySQL failed, Error:" + e.getMessage());

        }
    }

    public boolean isClosed() {

        try {

            if (this.con == null || this.con.isClosed())
                return true;

        } catch (SQLException e) {

            return false;

        }

        return false;

    }

    public void update(String sql) {

        try {

            this.con.createStatement().executeUpdate(sql);

        } catch (SQLException e) {

            Bukkit.getConsoleSender().sendMessage("Could not run the update (" + sql + ")");
            e.printStackTrace();

        }
    }

    public ResultSet query(String qry) {

        ResultSet rs = null;

        try {

            this.sql = this.con.prepareStatement(qry);
            rs = this.sql.executeQuery(qry);

        } catch (SQLException e) {

            connect();
            e.printStackTrace();

        }

        return rs;

    }
}