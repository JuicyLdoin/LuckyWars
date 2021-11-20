package ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item;

import org.bukkit.enchantments.Enchantment;

import java.util.concurrent.ThreadLocalRandom;

public class LuckyEnchantment {

    public LuckyEnchantment(Enchantment enchantment, int[] levels) {

        this.enchantment = enchantment;
        this.levels = levels;

    }

    private final Enchantment enchantment;
    private final int[] levels;

    public Enchantment getEnchantment() {

        return enchantment;

    }

    public int getLevel() {

        return levels.length == 1 ? levels[0] : ThreadLocalRandom.current().nextInt(levels[1] - levels[0]) + levels[0];

    }

    public static void init() {


    }
}