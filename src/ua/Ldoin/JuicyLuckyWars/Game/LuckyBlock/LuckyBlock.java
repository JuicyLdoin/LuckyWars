package ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item.LuckyBlockItem;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuckyBlock {

    public LuckyBlock(short data, String displayName, String head, int itemChance, int trapChance, List<LuckyBlockItem> items) {

        this.data = data;
        this.displayName = displayName;

        this.head = head;

        this.itemChance = itemChance;
        this.trapChance = trapChance;

        this.items = items;

    }

    private static final Map<String, LuckyBlock> luckyBlocks = new HashMap<>();

    public static LuckyBlock getLuckyBlockByName(String name) {

        return luckyBlocks.get(name);

    }
    public static LuckyBlock getLuckyBlockByData(short data) {

        for (LuckyBlock block : luckyBlocks.values())
            if (block.getData() == data)
                return block;

        return null;

    }

    private final short data;
    private final String displayName;

    private final String head;

    private final int itemChance;
    private final int trapChance;

    private final List<LuckyBlockItem> items;

    public short getData() {

        return data;

    }

    public String getDisplayName() {

        return displayName;

    }

    public String getHead() {

        return head;

    }

    public int getItemChance() {

        return itemChance;

    }

    public int getTrapChance() {

        return trapChance;

    }

    public List<LuckyBlockItem> getItems() {

        return items;

    }

    public ItemStack getItemStack() {

        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName.replace("&", "§"));

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§fШанс удачи: §a" + itemChance + "%");
        lore.add("§fШанс неудачи: §c" + trapChance + "%");
        lore.add("");

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }

    public static void init() {

        FileConfiguration config = Main.plugin.getConfig();

        for (String block : config.getConfigurationSection("LuckyBlocks").getKeys(false)) {

            short data = Short.parseShort(config.getString("LuckyBlocks." + block + ".data"));
            String displayName = config.getString("LuckyBlocks." + block + ".name");

            String head = config.getString("LuckyBlocks." + block + ".head");

            int itemChance = config.getInt("LuckyBlocks." + block + ".items.chance");
            int trapChance = config.getInt("LuckyBlocks." + block + ".trap.chance");

            List<LuckyBlockItem> items = new ArrayList<>();

            if (config.contains("LuckyBlocks." + block + ".items.list"))
                for (String item : config.getStringList("LuckyBlocks." + block + ".items.list"))
                    items.add(LuckyBlockItem.getItemByName(item));

            luckyBlocks.put(block, new LuckyBlock(data, displayName, head, itemChance, trapChance, items));

        }

        Main.sendMessageToConsole(Main.prefix + "&fLoaded &e" + luckyBlocks.size() + " &fLuckyBlocks!");

    }
}