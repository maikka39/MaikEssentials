package dev.maik.minecraftplugins.maikessentials.listeners;

import dev.maik.minecraftplugins.maikessentials.MaikEssentials;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class JoinListener implements Listener {
    @SuppressWarnings({"unused"})
    private final MaikEssentials plugin;

    public JoinListener(MaikEssentials plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (plugin.getConfig("config.yml").getBoolean("first_join_message") && (!p.hasPlayedBefore() || MaikEssentials.DEBUG)) {
            String m = plugin.getConfig("messages.yml").getString("first_join");
            p.sendMessage(ChatUtil.convert(m, Map.of(
                    "player", p.getDisplayName()
            )));
        }

        if (plugin.getConfig("config.yml").getBoolean("welcome_message")) {
            e.setJoinMessage(ChatUtil.convert(plugin.getConfig("messages.yml").getString("welcome"), Map.of(
                    "player", p.getDisplayName()
            )));
        }
    }
}
