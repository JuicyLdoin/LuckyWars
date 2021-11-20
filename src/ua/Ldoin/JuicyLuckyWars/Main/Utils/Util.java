package ua.Ldoin.JuicyLuckyWars.Main.Utils;

import org.bukkit.Bukkit;

public class Util {

    public static void callEvent(JuicyEvent event) {

        Bukkit.getPluginManager().callEvent(event);

    }
}