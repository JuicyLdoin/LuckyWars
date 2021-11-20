package ua.Ldoin.JuicyLuckyWars.Main.Utils.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class JuicyServer {

    public JuicyServer(String name, String status, int players, int maxPlayers, JuicyServerStates state, String map) {

        this.name = name;

        this.status = status;

        this.players = players;
        this.maxPlayers = maxPlayers;

        this.state = state;

        this.map = map;

    }

    public static Map<String, JuicyServer> servers = new HashMap<>();

    private final String name;

    private String status;

    private int players;
    private int maxPlayers;

    private JuicyServerStates state;

    private final String map;

    public String getName() {

        return this.name;

    }

    public String getStatus() {

        return this.status;

    }

    public int getPlayers() {

        return this.players;

    }

    public int getMaxPlayers() {

        return this.maxPlayers;

    }

    public JuicyServerStates getState() {

        return this.state;

    }

    public String getMap() {

        return this.map;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public void setPlayers(int players) {

        this.players = players;

    }

    public void addPlayer() {

        this.players++;

    }

    public void removePlayer() {

        this.players--;

    }

    public void setMaxPlayers(int maxPlayers) {

        this.maxPlayers = maxPlayers;

    }

    public void setState(JuicyServerStates state) {

        this.state = state;

    }

    public static void init() throws SQLException {

        servers.clear();

        if (!servers.containsKey(Bukkit.getMotd()))
            servers.put(Bukkit.getMotd(), new JuicyServer(Bukkit.getMotd(), "enabled",
                    Bukkit.getOnlinePlayers().size() - Arena.arena.getSpectators().size(), Arena.arena.getMaxPlayers(), JuicyServerStates.WAITING, Arena.arena.getMap()));

        ResultSet rs = Main.mysql.query("SELECT * FROM Servers");

        if (rs == null)
            return;

        while (rs.next())
            if (!servers.containsKey(rs.getString("name")))
                servers.put(rs.getString("name"), new JuicyServer(rs.getString("name"), rs
                        .getString("state"), rs
                        .getInt("players"), rs
                        .getInt("maxplayers"),
                        JuicyServerStates.valueOf(rs.getString("gamestate").toUpperCase()), rs
                        .getString("map")));

    }
}