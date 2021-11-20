package ua.Ldoin.JuicyLuckyWars.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Canceler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThunder(ThunderChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeather(WeatherChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent e) {

        e.setKeepInventory(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent e) {

        if (e.getBuffer().contains(" "))
            return;

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorDamageByEntity(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof org.bukkit.entity.ArmorStand)
            e.setCancelled(true);

    }

    @EventHandler
    public void xpChange(PlayerExpChangeEvent e) {

        e.setAmount(0);

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
}