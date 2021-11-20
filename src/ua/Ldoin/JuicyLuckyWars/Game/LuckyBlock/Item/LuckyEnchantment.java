package ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item;

import org.bukkit.enchantments.Enchantment;

public class LuckyEnchantment {

    public LuckyEnchantment(Enchantment enchantment, int level) {

        this.enchantment = enchantment;
        this.level = level;

    }

    private final Enchantment enchantment;
    private final int level;

    public Enchantment getEnchantment() {

        return enchantment;

    }

    public int getLevel() {

        return level;

    }

    public static void init() {


    }
}