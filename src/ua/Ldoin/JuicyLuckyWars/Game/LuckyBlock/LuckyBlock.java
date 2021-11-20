package ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Arena;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item.LuckyBlockItem;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyBlock {

    public LuckyBlock(String name, short data, String displayName, String head, int itemChance, int trapChance, int[] itemsAmount, List<LuckyBlockItem> items) {

        this.name = name;

        this.data = data;
        this.displayName = displayName;

        this.head = head;

        this.itemChance = itemChance;
        this.trapChance = trapChance;

        this.itemsAmount = itemsAmount;
        this.items = items;

        display = new HashMap<>();

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

    public static LuckyBlock getLuckyBlockByItemStack(ItemStack itemStack) {

        for (LuckyBlock block : luckyBlocks.values())
            if (block.getItemStack().equals(itemStack))
                return block;

        return null;

    }

    private final String name;

    private final short data;
    private final String displayName;

    private final String head;

    private final int itemChance;
    private final int trapChance;

    private final int[] itemsAmount;
    private final List<LuckyBlockItem> items;

    public String getName() {

        return name;

    }

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

    public int[] getItemsAmount() {

        return itemsAmount;

    }

    public List<LuckyBlockItem> getItems() {

        return items;

    }

    private List<ItemStack> getRandomItems() {

        List<ItemStack> items = new ArrayList<>();

        int amount = itemsAmount.length == 1 ? itemsAmount[0] : ThreadLocalRandom.current().nextInt(itemsAmount[1] - itemsAmount[0]) + itemsAmount[0];

        while (items.size() < amount)
            items.add(this.items.size() == 1 ? this.items.get(0).buildToItemStack() :
                    this.items.get(ThreadLocalRandom.current().nextInt(this.items.size() - 1)).buildToItemStack());

        return items;

    }

    private void dropItems(Location location) {

        for (ItemStack item : getRandomItems())
            location.getWorld().dropItem(location, item);

    }

    private final Map<Location, ArmorStand> display;

    public void placeBlock(Location location) {

        location.getBlock().setTypeIdAndData(95, (byte) data, false);

        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location.clone().subtract(-0.5, 1.2, -0.5), EntityType.ARMOR_STAND);

        stand.setBasePlate(false);
        stand.setVisible(false);
        stand.setGravity(false);

        stand.setHelmet(getItemStack());

        display.put(location, stand);

        Arena.arena.getLuckyBlockStorage().add(location.clone(), this);

    }

    public void breakBlock(Location location, boolean items) {

        display.get(location).remove();

        if (items)
            dropItems(location);

        Arena.arena.getLuckyBlockStorage().remove(location.clone());

    }

    public ItemStack getItemStack() {

        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", head));

        try {

            Field profileField = meta.getClass().getDeclaredField("profile");

            profileField.setAccessible(true);
            profileField.set(meta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {

            error.printStackTrace();

        }

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

            int[] amount = new int[config.getString("LuckyBlocks." + block + ".items.amount").contains("-") ? 2 : 1];

            if (config.getString("LuckyBlocks." + block + ".items.amount").contains("-")) {

                amount[0] = Integer.parseInt(config.getString("LuckyBlocks." + block + ".items.amount").split("-")[0]);
                amount[1] = Integer.parseInt(config.getString("LuckyBlocks." + block + ".items.amount").split("-")[1]);

            } else
                amount[0] = config.getInt("LuckyBlocks." + block + ".items.amount");

            List<LuckyBlockItem> items = new ArrayList<>();

            if (config.contains("LuckyBlocks." + block + ".items.list"))
                for (String item : config.getStringList("LuckyBlocks." + block + ".items.list"))
                    items.add(LuckyBlockItem.getItemByName(item));

            luckyBlocks.put(block, new LuckyBlock(block, data, displayName, head, itemChance, trapChance, amount, items));

        }

        Main.sendMessageToConsole(Main.prefix + "&fLoaded &e" + luckyBlocks.size() + " &fLuckyBlocks!");

    }
}