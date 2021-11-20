package ua.Ldoin.JuicyLuckyWars.Main.Utils.Permissions;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import ua.Ldoin.JuicyLuckyWars.Config.Configuration;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class Group {

    public static FileConfiguration pc = Configuration.ConfigurationType.PERMISSIONS.getConfig();

    private Group(String name, String prefix, String suffix, boolean isdefault, ArrayList<String> permissions) {

        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;

        this.isdefault = isdefault;

        this.permissions = permissions;

    }

    public static HashMap<String, Group> groups = new HashMap<>();

    public static Group getGroup(String name) {

        return groups.get(name);

    }

    private final String name;
    private final String prefix;
    private final String suffix;

    private final boolean isdefault;

    private final ArrayList<String> permissions;

    public String getName() {

        return name;

    }

    public String getPrefix() {

        return prefix.replace("&", "§");

    }

    public String getSuffix() {

        return suffix.replace("&", "§");

    }

    public boolean isDefault() {

        return isdefault;

    }

    public ArrayList<String> getPermissions() {

        return permissions;

    }

    public static void addGroups() {

        for (String group : pc.getConfigurationSection("groups").getKeys(false)) {

            String prefix = pc.getString("groups." + group + ".options.prefix");
            String suffix = pc.getString("groups." + group + ".options.suffix");

            boolean isdefault = pc.getBoolean("groups." + group + ".options.default");

            ArrayList<String> perms = new ArrayList<>(pc.getStringList("groups." + group + ".permissions"));

            groups.put(group, new Group(group, prefix, suffix, isdefault, perms));

        }

        Bukkit.getConsoleSender().sendMessage(Main.prefix + "Loaded " + groups.size() + " groups!");

    }
}