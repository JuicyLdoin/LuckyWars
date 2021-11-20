package ua.Ldoin.JuicyLuckyWars.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;

public class Canceler implements Listener {

    @EventHandler
    public void BlockSave(BlockBreakEvent e) {

        if (!Arena.arena.isStarted())
            e.setCancelled(true);

        if (e.getBlock().getType().equals(Material.STAINED_GLASS))
            if (LuckyBlock.getLuckyBlockByData(e.getBlock().getData()) != null)
                return;

        Arena.arena.getBlockStorage().addBlock(e.getBlock());

    }

    @EventHandler
    public void BlockSave(BlockIgniteEvent e) {

        Arena.arena.getBlockStorage().addBlock(e.getBlock());

    }

    @EventHandler
    public void BlockSave(BlockPlaceEvent e) {

        if (!Arena.arena.isStarted())
            e.setCancelled(true);

        if (e.getBlock().getType().equals(Material.STAINED_GLASS))
            if (LuckyBlock.getLuckyBlockByData(e.getBlock().getData()) != null)
                return;

        Arena.arena.getBlockStorage().addBlockAir(e.getBlock());

    }

    @EventHandler
    public void BlockSave(BlockFromToEvent e) {

        Arena.arena.getBlockStorage().addBlock(e.getBlock());

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThunder(ThunderChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(WeatherChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent e) {

        if (e.getBuffer().contains(" "))
            return;

        e.setCancelled(true);

    }

    @EventHandler
    public void Food(FoodLevelChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void Spawn(CreatureSpawnEvent e) {

        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))
            e.setCancelled(true);

    }
    
    @EventHandler
    public void Damage(EntityDamageByEntityEvent e) {

        if (!Arena.arena.isStarted())
            e.setCancelled(true);

    }
}