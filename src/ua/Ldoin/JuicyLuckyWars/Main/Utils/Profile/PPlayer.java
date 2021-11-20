package ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.bukkit.entity.Player;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Permissions.Group;

public class PPlayer {

    private PPlayer(String group, int level, double xp, int gold, int diamonds, String displayName, String displayGroup) {

        this.group = Group.getGroup(group);

        if (level < 0)
            this.level = 1;
        else
            this.level = level;

        this.xp = Math.max(xp, 0.0D);

        this.gold = gold;
        this.diamonds = diamonds;

        this.displayName = displayName;
        this.displayGroup = Group.getGroup(displayGroup);

    }

    public static HashMap<Player, PPlayer> players = new HashMap<>();

    public static PPlayer getPPlayer(Player p) {

        return players.get(p);

    }

    public static HashMap<Player, PPlayer> getPPlayers() {

        return players;

    }

    public static void removePlayer(Player pl) {

        players.remove(pl);

    }

    private Group group;

    private int level;
    private double xp;

    private int gold;
    private int diamonds;

    private final String displayName;
    private final Group displayGroup;

    public Group getGroup() {

        return this.group;

    }

    public int getLevel() {

        return this.level;

    }

    public double getXP() {

        return this.xp;

    }

    public double getXPToNextLevel() {

        return (getLevel() < 1.0D) ? 5000.0D : (2500.0D * (getLevel() - 1.0D) + 5000.0D);

    }

    public double getNaturalXPToNextLevel() {

        return getXPToNextLevel() - this.xp;

    }

    public double getPercentageToNextLevel() {

        return Math.rint(100.0D * (100.0D - getNaturalXPToNextLevel() / getXPToNextLevel() / 100.0D)) / 100.0D;

    }

    public String buildExpLineByPercentWithColor(int lines, String bg, String active, String nonactive) {

        StringBuilder line = new StringBuilder();
        line.append(active);

        int need = (int)(lines / 100.0D * getPercentageToNextLevel());
        int used = 0;

        while (used <= lines) {

            used++;
            line.append(bg);

            if (used == need)
                line.append(nonactive);

        }

        return line.toString();

    }

    public int getGold() {

        return this.gold;

    }

    public int getDiamonds() {

        return this.diamonds;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public Group getDisplayGroup() {

        return this.displayGroup;

    }

    public void setGroup(Group group) {

        this.group = group;

    }

    public void setLevel(int level) {

        this.level = level;

    }

    public void addXP(double xp) {

        this.xp += xp;

        initLevel();

    }

    public void setXP(double xp) {

        this.xp = xp;

        initLevel();

    }

    public void addGold(int gold) {

        this.gold += gold;

    }

    public void setGold(int gold) {

        this.gold = gold;

    }

    public void addDiamonds(int diamonds) {

        this.diamonds += diamonds;

    }

    public void setDiamonds(int diamonds) {

        this.diamonds = diamonds;

    }

    public void initLevel() {

        if (getNaturalXPToNextLevel() <= 0.0D) {

            double addxp = this.xp - getXPToNextLevel();

            setLevel(getLevel() + 1);

            if (addxp >= 0.0D)
                setXP(addxp);

            if (addxp < 0.0D)
                setXP(0.0D);

        }
    }

    public static void loadPlayer(Player pl) {

        if (!PPlayerManager.playerExists(pl.getName()))
            PPlayerManager.createPlayer(pl.getName());

        addPlayer(pl);

    }

    public static void addPlayer(Player pl) {

        ResultSet set = PPlayerManager.getPlayer(pl.getName());

        try {

            if (set != null) {

                if (set.next())
                    players.put(pl, new PPlayer(set.getString("Rank"), set.getInt("Level"),
                            set.getInt("XP"), set.getInt("Gold"),
                            set.getInt("Diamonds"), set.getString("DisplayName"),
                            set.getString("DisplayRank")));

                set.close();

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        PPlayer p = getPPlayer(pl);

        if (p != null)
            p.initLevel();

    }
}