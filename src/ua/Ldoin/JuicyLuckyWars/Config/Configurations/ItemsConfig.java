package ua.Ldoin.JuicyLuckyWars.Config.Configurations;

import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.io.File;

public class ItemsConfig extends Configuration {

    private static final File f = new File(Main.plugin.getDataFolder(), "items.yml");

    public ItemsConfig() {

        super(f);

    }

    public void init() {

        if (isDirectory())
            delete();

        if (!isFile())
            Main.plugin.saveResource("items.yml", true);

    }
}