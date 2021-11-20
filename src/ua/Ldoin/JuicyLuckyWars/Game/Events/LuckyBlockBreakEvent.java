package ua.Ldoin.JuicyLuckyWars.Game.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.JuicyEvent;

public class LuckyBlockBreakEvent extends JuicyEvent {

    private final Player player;

    private final Location location;
    private final LuckyBlock luckyBlock;

    private Boolean Cancel;

    public LuckyBlockBreakEvent(Player player, Location location, LuckyBlock luckyBlock) {

        this.player = player;

        this.location = location;
        this.luckyBlock = luckyBlock;

        this.Cancel = Arena.arena == null;

    }

    public Player getPlayer() {

        return this.player;

    }

    public Location getLocation() {

        return location;

    }

    public LuckyBlock getLuckyBlock() {

        return luckyBlock;

    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {

        return handlers;

    }

    public static HandlerList getHandlerList() {

        return handlers;

    }

    public boolean isCancelled() {

        return Cancel;

    }

    public void setCancelled(boolean cancel) {

        this.Cancel = cancel;

    }
}