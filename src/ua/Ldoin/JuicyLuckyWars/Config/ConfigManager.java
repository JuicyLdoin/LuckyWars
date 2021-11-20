package ua.Ldoin.JuicyLuckyWars.Config;

import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class ConfigManager {

    private static final ArrayList<Configuration> configs = new ArrayList<>();

    public static void registerConfiguration(Object c) {

        configs.add((Configuration)c);
        Main.plugin.getLogger().log(Level.CONFIG, "" + c + "");

    }

    public static void registerConfigurations(Configuration... config) {

        (new ArrayList(Arrays.asList((Object[])config))).forEach(ConfigManager::registerConfiguration);

    }

    public static void initConfigs() {

        for (Configuration config : configs)
            config.init();

    }

    public static Configuration getConfiguration(Configuration config) {

        return config;

    }
}