package ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyBlockItem {

    public static FileConfiguration config = Configuration.ConfigurationType.ITEMS.getConfig();

    public LuckyBlockItem(Material material, short data, int[] amount, String displayName, List<String> lore, List<LuckyEnchantment> enchantments) {

        this.material = material;
        this.data = data;

        this.amount = amount;

        this.displayName = displayName;
        this.lore = lore;

        this.enchantments = enchantments;

    }

    private static final Map<String, LuckyBlockItem> items = new HashMap<>();

    public static LuckyBlockItem getItemByName(String name) {

        return items.get(name);

    }

    private final Material material;
    private final short data;

    private final int[] amount;

    private final String displayName;
    private final List<String> lore;

    private final List<LuckyEnchantment> enchantments;

    public ItemStack buildToItemStack() {

        ItemStack item = new ItemStack(material, amount.length == 1 ? amount[0] : ThreadLocalRandom.current().nextInt(amount[1] - amount[0]) + amount[0], data);

        for (LuckyEnchantment enchantment : enchantments)
            item.addUnsafeEnchantment(enchantment.getEnchantment(), enchantment.getLevel() + 1);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }

    public static void init() {

        for (String item : config.getConfigurationSection("items").getKeys(false)) {

            Material material = Material.matchMaterial(config.getString("items." + item + ".material"));
            short data = Short.parseShort(config.getString("items." + item + ".data"));

            int[] amount = new int[config.getString("items." + item + ".amount").contains("-") ? 2 : 1];

            if (config.getString("items." + item + ".amount").contains("-")) {

                amount[0] = Integer.parseInt(config.getString("items." + item + ".amount").split("-")[0]);
                amount[1] = Integer.parseInt(config.getString("items." + item + ".amount").split("-")[1]);

            } else
                amount[0] = config.getInt("items." + item + ".amount");

            String displayName = config.getString("items." + item + ".name").replace("&", "§");

            List<String> lore = new ArrayList<>();

            if (config.contains("items." + item + ".lore"))
                for (String lorePiece : config.getStringList("items." + item + ".lore"))
                    lore.add(lorePiece.replace("&", "§"));

            List<LuckyEnchantment> enchantments = new ArrayList<>();

            if (config.contains("items." + item + ".lore"))
                for (String enchant : config.getStringList("items." + item + ".enchantments"))
                    enchantments.add(new LuckyEnchantment(Enchantment.getByName(enchant.split("=")[0].toUpperCase()), Integer.parseInt(enchant.split("=")[1]) - 1));

            items.put(item, new LuckyBlockItem(material, data, amount, displayName, lore, enchantments));

        }

        Main.sendMessageToConsole(Main.prefix + "&fLoaded &e" + items.size() + " &fLuckyItems!");

    }
}