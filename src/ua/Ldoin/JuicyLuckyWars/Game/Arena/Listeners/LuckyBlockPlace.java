package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.Events.LuckyBlockPlaceEvent;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.LocationUtil;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LuckyBlockPlace implements Listener {

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) throws IOException {

        if (LuckyBlock.getLuckyBlockByData(e.getItemInHand().getData().getData()) != null)
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
                if (!Arena.arena.isStarted()) {

                    File file = new File(Main.plugin.getDataFolder(), "config.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    List<String> locations = config.getStringList("Arena.Locations.LuckyBlocks");

                    locations.add(LuckyBlock.getLuckyBlockByData(e.getItemInHand().getData().getData()).getName() + "=" + LocationUtil.getLocation(e.getBlock().getLocation()));

                    config.set("Arena.Locations.LuckyBlocks", locations);
                    config.save(file);

                    e.setCancelled(true);

                }

        if (e.getItemInHand() != null)
            if (LuckyBlock.getLuckyBlockByItemStack(e.getItemInHand()) != null) {


                Util.callEvent(new LuckyBlockPlaceEvent(e.getPlayer(), e.getBlock().getLocation(), LuckyBlock.getLuckyBlockByItemStack(e.getItemInHand())));
                e.setCancelled(true);

            }
    }

    @EventHandler
    public void placeLuckyBlock(LuckyBlockPlaceEvent e) {

        e.getLuckyBlock().placeBlock(e.getLocation());

    }
}