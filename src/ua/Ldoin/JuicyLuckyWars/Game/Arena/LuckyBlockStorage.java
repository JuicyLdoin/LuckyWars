package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Location;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;

import java.util.Map;

public class LuckyBlockStorage {

    public LuckyBlockStorage(Map<Location, LuckyBlock> luckyBlocks) {

        this.luckyBlocks = luckyBlocks;

    }

    private final Map<Location, LuckyBlock> luckyBlocks;

    public void add(Location location, LuckyBlock luckyBlock) {

        luckyBlocks.put(location, luckyBlock);

    }

    public void remove(Location location) {

        luckyBlocks.remove(location);

    }

    public void clear() {

        luckyBlocks.clear();

    }
}