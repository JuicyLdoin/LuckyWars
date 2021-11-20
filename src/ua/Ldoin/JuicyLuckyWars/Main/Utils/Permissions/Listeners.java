package ua.Ldoin.JuicyLuckyWars.Main.Utils.Permissions;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import ua.Ldoin.JuicyLuckyWars.Main.Main;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayer;
import ua.Ldoin.JuicyLuckyWars.Main.Utils.Profile.PPlayerManager;

public class Listeners implements Listener {
    public static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void Join(PlayerJoinEvent e) {

        Player pl = e.getPlayer();
        PPlayer.loadPlayer(pl);

        PermissionAttachment pa = pl.addAttachment(Main.plugin);

        if (PPlayer.getPPlayer(pl).getGroup() == null)
            for (String group : Group.pc.getConfigurationSection("groups").getKeys(false))
                if (Group.pc.getBoolean("groups." + group + ".options.default"))
                    PPlayer.getPPlayer(pl).setGroup(Group.getGroup(group));

        for (String perm : PPlayer.getPPlayer(pl).getGroup().getPermissions())
            pa.setPermission(perm, true);

        perms.put(pl.getUniqueId(), pa);

    }

    @EventHandler
    public void Quit(PlayerQuitEvent e) {

        Player pl = e.getPlayer();

        PPlayerManager.savePlayer(pl);
        PPlayer.removePlayer(pl);

        perms.remove(pl.getUniqueId());

    }
}