package ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Player.ArenaPlayer;
import ua.Ldoin.JuicyLuckyWars.Game.Events.LuckyBlockBreakEvent;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Util;

public class LuckyBlockBreak implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {

        if (e.getBlock().getType().equals(Material.STAINED_GLASS))
            if (LuckyBlock.getLuckyBlockByData(e.getBlock().getData()) != null) {

                Util.callEvent(new LuckyBlockBreakEvent(e.getPlayer(), e.getBlock().getLocation(), LuckyBlock.getLuckyBlockByData(e.getBlock().getData()), LuckyBlockBreakEvent.LuckyBlockBreakSource.PLAYER));
                e.setDropItems(false);

            }
    }

    @EventHandler
    public void breakLuckyBlock(LuckyBlockBreakEvent e) {

        e.getLuckyBlock().breakBlock(e.getLocation());
        ArenaPlayer.getArenaPlayer(e.getPlayer()).addLuckyBlock();

    }
}