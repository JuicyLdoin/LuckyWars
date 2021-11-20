package ua.Ldoin.JuicyLuckyWars.Main.Utils;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import ua.Ldoin.JuicyLuckyWars.Main.Main;

public class ScoreboardUpdater extends BukkitRunnable {

    public static FileConfiguration sb = Main.plugin.getConfig();

    public static final Map<Player, String> updater = new HashMap<>();

    public static void setScoreboard(Player p, String board) {

        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = b.registerNewObjective("board", "dummy");

        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(sb.getString("scoreboard." + board + ".title").replace("&", "§"));

        for (int count = 1; count <= sb.getInt("scoreboard." + board + ".rowscount"); count++) {

            String value;
            String[] tv = sb.getString("scoreboard." + board + ".rows." + count).split(": ");

            String text = tv[0].replace("&", "§");

            if (tv.length == 2)
                value = Main.replace(tv[1], p);
            else
                value = "";

            if (text.length() > 40)
                text = text.substring(40);

            if (!value.equalsIgnoreCase("")) {

                b.registerNewTeam(String.valueOf(count)).addEntry(text + ": ");
                b.getTeam(String.valueOf(count)).setSuffix(Main.replace(value, p));

                o.getScore(text + ": ").setScore(count - 1);

            } else
                o.getScore(text).setScore(count - 1);

        }

        p.setScoreboard(b);

    }

    public void updateScoreboard(Player p, String board) {

        Scoreboard b = p.getScoreboard();

        if (b == null)
            setScoreboard(p, board);
        else {
            for (int count = 1; count <= sb.getInt("scoreboard." + board + ".rowscount"); count++) {

                String value;
                String[] tv = sb.getString("scoreboard." + board + ".rows." + count).split(": ");

                if (tv.length == 2)
                    value = Main.replace(tv[1], p);
                else
                    value = "";

                if (!value.equalsIgnoreCase(""))
                    b.getTeam(String.valueOf(count)).setSuffix(Main.replace(value, p));

            }
        }
    }

    public void run() {

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (!updater.containsKey(p))
                updater.put(p, "default");

            updateScoreboard(p, updater.get(p));

        }
    }
}