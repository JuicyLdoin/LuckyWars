package ua.Ldoin.JuicyLuckyWars.Game.Arena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import ua.Ldoin.JuicyLuckyWars.Game.Arena.Listeners.*;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.Item.LuckyBlockItem;
import ua.Ldoin.JuicyLuckyWars.Game.LuckyBlock.LuckyBlock;

public class Main {

    public static void init(ua.Ldoin.JuicyLuckyWars.Main.Main pl) {

        LuckyBlockItem.init();
        LuckyBlock.init();

        new Arena();

        (new Timer()).runTaskTimer(pl, 0L, 20L);

        PluginManager p = Bukkit.getPluginManager();

        p.registerEvents(new Join(), pl);
        p.registerEvents(new Quit(), pl);
        p.registerEvents(new Chat(), pl);
        p.registerEvents(new JoinAttemp(), pl);
        p.registerEvents(new LuckyBlockPlace(), pl);
        p.registerEvents(new LuckyBlockBreak(), pl);

    }
}