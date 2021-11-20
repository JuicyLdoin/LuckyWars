package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockStorage {

    public BlockStorage() {

        this.blocks = new HashMap<>();

    }

    private final Map<Location, SerializableBlock> blocks;

    public void addBlock(Block block) {
        
        blocks.put(block.getLocation(), new SerializableBlock(block.getType().getId(), block.getData()));
        
    }

    public void addBlockAir(Block block) {

        blocks.put(block.getLocation(), new SerializableBlock(0));

    }
    
    public void clear() {

        for (Location locations : blocks.keySet())
            blocks.get(locations).place(locations);

        blocks.clear();

    }
}