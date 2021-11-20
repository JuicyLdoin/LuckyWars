package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.LocationUtil;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private FileConfiguration config = Main.plugin.getConfig();

    public Arena() {

        this.map = config.getString("Arena.Name");

        this.started = false;

        this.minPlayers = config.getInt("Arena.MinPlayers");
        this.maxPlayers = config.getInt("Arena.MaxPlayers");

        this.spawnLocations = new ArrayList<>();

        for (String s : config.getStringList("Arena.Locations.Players"))
            spawnLocations.add(LocationUtil.getLocation(s));

        players = new ArrayList<>();
        spectators = new ArrayList<>();

        storage = new LuckyBlockStorage();

        arena = this;

    }

    public static Arena arena;

    private final String map;

    private boolean started;

    private final int minPlayers;
    private final int maxPlayers;

    private final List<Location> spawnLocations;

    private final List<ArenaPlayer> players;
    private final List<Player> spectators;

    private final LuckyBlockStorage storage;

    public String getMap() {

        return map;

    }

    public boolean isStarted() {

        return started;

    }

    public int getMinPlayers() {

        return minPlayers;

    }

    public int getMaxPlayers() {

        return maxPlayers;

    }

    public List<Location> getSpawnLocations() {

        return spawnLocations;

    }

    public int getPlayersInGame() {

        return players.size();

    }

    public List<ArenaPlayer> getPlayers() {

        return players;

    }

    public List<Player> getSpectators() {

        return spectators;

    }

    public LuckyBlockStorage getStorage() {

        return storage;

    }

    public void addSpectator(Player p) {

        this.spectators.add(p);

    }

    public void removeSpectator(Player p) {

        this.spectators.remove(p);

    }

    public void addPlayer(ArenaPlayer p) {

        this.players.add(p);

    }

    public void initSpectator(Player p) {

        addSpectator(p);

        p.setGameMode(GameMode.SPECTATOR);

        for (Player pl : this.spectators)
            pl.sendMessage("§7Наблюдатели >> §fИгрок §e" + PPlayer.getPPlayer(p).getDisplayName() + " §fприсоединился к наблюдателям!");

    }

    public static void initGamePlayer(Player p) {

        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);

        p.setGameMode(GameMode.SURVIVAL);

    }

    public static void initDeathPlayer(final Player p) {

        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());

        p.setHealth(p.getMaxHealth());
        p.setGameMode(GameMode.SPECTATOR);

        p.sendTitle("§cВы умерли!", "");

    }

    public void start() {



    }

    public void end() {

        if (players.size() > 1)
            return;

        ArenaPlayer winner = players.get(0);

        new Arena();

    }
}