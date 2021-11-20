package ua.Ldoin.JuicyLuckyWars.Game.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.JuicyEvent;

public class LuckyBlockPlaceEvent extends JuicyEvent {

    private final Player player;

    private Location location;
    private LuckyBlock luckyBlock;

    private Boolean Cancel;

    public LuckyBlockPlaceEvent(Player player, Location location, LuckyBlock luckyBlock) {

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

    public void setLocation(Location location) {

        if (Arena.arena == null)
            return;

        Arena.arena.getStorage().remove(this.location);
        Arena.arena.getStorage().add(location, luckyBlock);

        this.location = location;

    }

    public void setLuckyBlock(LuckyBlock luckyBlock) {

        if (Arena.arena == null)
            return;

        Arena.arena.getStorage().remove(location);
        Arena.arena.getStorage().add(location, luckyBlock);

        this.luckyBlock = luckyBlock;

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