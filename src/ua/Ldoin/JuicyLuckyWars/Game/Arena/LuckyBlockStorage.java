package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Location;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;

import java.util.HashMap;
import java.util.Map;

public class LuckyBlockStorage {

    public LuckyBlockStorage() {

        this.luckyBlocks = new HashMap<>();

    }

    private final Map<Location, LuckyBlock> luckyBlocks;

    public void add(Location location, LuckyBlock luckyBlock) {

        luckyBlocks.put(location, luckyBlock);

    }

    public boolean contains(Location location) {

        return luckyBlocks.containsKey(location);

    }

    public void remove(Location location) {

        luckyBlocks.remove(location);

    }

    public void clear() {

        if (!luckyBlocks.isEmpty())
            for (Location location : luckyBlocks.keySet())
                luckyBlocks.get(location).breakBlock(location, false);

        luckyBlocks.clear();

    }
}