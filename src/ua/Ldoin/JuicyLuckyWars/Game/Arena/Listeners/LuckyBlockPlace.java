package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Events.LuckyBlockPlaceEvent;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Util;

public class LuckyBlockPlace implements Listener {

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {

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