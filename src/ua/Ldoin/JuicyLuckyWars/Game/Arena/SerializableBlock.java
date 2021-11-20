package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Location;

public class SerializableBlock {

    public SerializableBlock(int block) {

        this.block = block;
        this.data = 0;

    }

    public SerializableBlock(int block, byte data) {

        this.block = block;
        this.data = data;

    }

    public SerializableBlock(String self) {

        String[] bits = self.split(":");

        if (bits.length != 2)
            throw new IllegalArgumentException("String form of SerializableBlock didn't have exactly 2 numbers");

        try {

            this.block = Integer.parseInt(bits[0]);
            this.data = Byte.parseByte(bits[1]);

        } catch (NumberFormatException nfe) {

            throw new IllegalArgumentException("Unable to convert id to integer and data to byte");

        }
    }

    private final int block;
    private final byte data;

    public int getBlock() {

        return this.block;

    }

    public byte getData() {

        return this.data;

    }

    public void place(Location location) {

        location.getBlock().setTypeIdAndData(block, data, false);

    }

    public String toString() {

        return this.block + ":" + this.data;

    }

    public boolean equals(Object o) {

        return (o instanceof SerializableBlock && this.block == ((SerializableBlock)o).block && this.data == ((SerializableBlock)o).data);

    }
}