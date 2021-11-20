package ua.Ldoin.JuicyLuckyWars.Config.Configurations;

import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.io.File;

public class ItemsConfig extends Configuration {

    private static final Main plugin = Main.getPlugin(Main.class);

    private static final File f = new File(plugin.getDataFolder(), "items.yml");

    public ItemsConfig() {

        super(f);

    }

    public void init() {

        if (isDirectory())
            delete();

        if (!isFile())
            plugin.saveResource("items.yml", true);

    }
}