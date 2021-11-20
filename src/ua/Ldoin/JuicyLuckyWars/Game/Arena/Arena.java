package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.LocationUtil;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.ScoreboardUpdater;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServerManager;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServerStates;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Server.JuicyServerUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {

    private final FileConfiguration config = Main.plugin.getConfig();

    public static int getPlayersToStart() {

        return Math.max(arena.getMinPlayers() - Bukkit.getOnlinePlayers().size() - arena.getSpectators().size(), 0);

    }

    public Arena() {

        this.map = config.getString("Arena.Name");

        Timer.timeToStart = this.config.getInt("Arena.TimeToStart");
        Timer.timeToEnd = this.config.getInt("Arena.TimeToEnd");

        this.started = false;

        this.minPlayers = config.getInt("Arena.MinPlayers");
        this.maxPlayers = config.getInt("Arena.MaxPlayers");

        this.spawnLocations = new ArrayList<>();

        for (String s : config.getStringList("Arena.Locations.Players"))
            spawnLocations.add(LocationUtil.getLocation(s));

        players = new ArrayList<>();
        spectators = new ArrayList<>();

        luckyBlockStorage = new LuckyBlockStorage();
        blockStorage = new BlockStorage();

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

    private final LuckyBlockStorage luckyBlockStorage;
    private final BlockStorage blockStorage;

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

    public LuckyBlockStorage getLuckyBlockStorage() {

        return luckyBlockStorage;

    }

    public BlockStorage getBlockStorage() {

        return blockStorage;

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

        started = true;

        JuicyServer server = JuicyServer.servers.get(Bukkit.getMotd());
        JuicyServerUpdater.stoped = true;

        server.setState(JuicyServerStates.INGAME);

        JuicyServerManager.saveServer(server);
        JuicyServerUpdater.stoped = false;

        List<Location> spawned = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers())
            if (!getSpectators().contains(player)) {

                while (true) {

                    Location spawn = spawnLocations.get(ThreadLocalRandom.current().nextInt(spawnLocations.size() - 1));

                    if (!spawned.contains(spawn)) {

                        player.teleport(spawn);

                        spawned.add(spawn);
                        break;

                    }
                }

                ArenaPlayer.players.put(player.getName(), new ArenaPlayer(player));
                players.add(new ArenaPlayer(player));

                player.sendTitle("§aИгра началась!", "");
                ScoreboardUpdater.setScoreboard(player, "game");

            }

        for (String s : config.getStringList("Arena.Locations.LuckyBlocks"))
            LuckyBlock.getLuckyBlockByName(s.split("=")[0]).placeBlock(Objects.requireNonNull(LocationUtil.getLocation(s.split("=")[1])));

    }

    public void end() {

        JuicyServer server = JuicyServer.servers.get(Bukkit.getMotd());
        JuicyServerUpdater.stoped = true;

        server.setState(JuicyServerStates.WAITING);

        JuicyServerManager.saveServer(server);
        JuicyServerUpdater.stoped = false;

        ArenaPlayer winner = players.get(0);

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendMessage(Main.prefix + "§fИгра окончена!");
            player.sendMessage(Main.prefix + "");
            player.sendMessage(Main.prefix + "§fПобедитель: §e" + PPlayer.getPPlayer(winner.getPlayer()).getDisplayName());
            player.sendMessage(Main.prefix + "");
            player.sendMessage(Main.prefix + "§fЛучшие игроки по убийствам:");

            ScoreboardUpdater.setScoreboard(player, "wait");

        }

        blockStorage.clear();
        luckyBlockStorage.clear();

        Timer.timeToStart = this.config.getInt("Arena.TimeToStart");

        for (Player p : Bukkit.getOnlinePlayers())
            if (p.getGameMode().equals(GameMode.SPECTATOR))
                initSpectator(p);

        ArenaPlayer.players.clear();

        new Arena();

    }
}