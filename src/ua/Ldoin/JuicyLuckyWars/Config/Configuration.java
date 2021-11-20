package ua.Ldoin.JuicyLuckyWars.Config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ua.Ldoin.JuicyLuckyWars.Config.Configurations.ItemsConfig;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Configuration {


    private static Configuration items;

    private static FileConfiguration itemsConfig;

    private final YamlConfiguration cfg;

    private final File file;

    public Configuration(File file) {

        this.file = file;
        this.cfg = YamlConfiguration.loadConfiguration(file);

    }

    public YamlConfiguration getConfig() {

        return this.cfg;

    }

    public void saveConfig() {

        try {

            this.cfg.save(this.file);

        } catch (IOException ex) {

            Main.plugin.getLogger().log(Level.WARNING, "\"" + this.file.getName() + " \"", ex);

        }
    }

    public boolean isDirectory() {

        return this.file.isDirectory();

    }

    public boolean isFile() {

        return this.file.isFile();

    }

    public boolean delete() {

        return this.file.delete();

    }

    public void init() {}

    public enum ConfigurationType {

        ITEMS(Configuration.itemsConfig, Configuration.items);

        private final Configuration file;

        private final FileConfiguration configuration;

        ConfigurationType(FileConfiguration levelsConfig, Configuration file) {

            this.configuration = levelsConfig;
            this.file = file;

        }

        public Configuration getFile() {

            return this.file;

        }

        public FileConfiguration getConfig() {

            return this.configuration;

        }
    }

    public static class ConfigurationUtils {

        public static void init() {

            Configuration.items = ConfigManager.getConfiguration(new ItemsConfig());

            Configuration.itemsConfig = Configuration.items.getConfig();

            ConfigManager.registerConfigurations(new Configuration[] { Configuration.items });

            ConfigManager.initConfigs();

        }
    }
}