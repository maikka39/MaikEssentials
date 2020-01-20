package dev.maik.minecraftplugins.maikessentials.listeners;

import dev.maik.minecraftplugins.maikessentials.MaikEssentials;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import dev.maik.minecraftplugins.maikessentials.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class JoinListener implements Listener {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final MaikEssentials plugin;
    private final FileConfiguration config;
    private final FileConfiguration messages;

    public JoinListener(MaikEssentials plugin) {
        this.plugin = plugin;


        config = new ConfigUtil(plugin).loadConfig("config.yml");
        messages = new ConfigUtil(plugin).loadConfig("messages.yml");


        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (config.getBoolean("first_join_message") && (!p.hasPlayedBefore() || MaikEssentials.DEBUG)) {
            String m = messages.getString("first_join");
            p.sendMessage(ChatUtil.convert(m, Map.of(
                    "player", p.getDisplayName()
            )));
        }

        if (config.getBoolean("welcome_message")) {
            e.setJoinMessage(ChatUtil.convert(messages.getString("welcome"), Map.of(
                    "player", p.getDisplayName()
            )));
        }
    }
}
