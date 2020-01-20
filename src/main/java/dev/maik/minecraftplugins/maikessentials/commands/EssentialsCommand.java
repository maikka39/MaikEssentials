package dev.maik.minecraftplugins.maikessentials.commands;

import com.google.common.collect.Lists;
import dev.maik.minecraftplugins.maikessentials.MaikEssentials;
import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.EssentialsEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;

public abstract class EssentialsCommand {
    private final String name;
    private MaikEssentials plugin;

    public EssentialsCommand(final String name) {
        this.name = name;
    }

    public void onLoad() {
    }

    public void run(UserEntity sender, String label, String[] args) throws Exception {
        throw new Exception("Command not handled, plugin error");
    }

    public void run(CommandSourceEntity sender, String label, String[] args) throws Exception {
        throw new Exception("Command is only allowed for players");
    }

    public final List<String> tabComplete(final CommandSourceEntity commandSourceEntity, final String commandAlias, final String[] commandArgs) {
        List<String> options;
        if (commandSourceEntity.isPlayer()) {
            options = getTabCompleteOptions(new UserEntity(commandSourceEntity.getPlayer()), commandAlias, commandArgs);
        } else {
            options = getTabCompleteOptions(commandSourceEntity, commandAlias, commandArgs);
        }

        if (options == null) {
            return null;
        }

        return StringUtil.copyPartialMatches(commandArgs[commandArgs.length - 1], options, Lists.newArrayList());
    }

    protected List<String> getTabCompleteOptions(final UserEntity userEntity, final String commandAlias, final String[] commandArgs) {
        return getTabCompleteOptions(userEntity.getSource(), commandAlias, commandArgs);
    }

    protected List<String> getTabCompleteOptions(final CommandSourceEntity sender, final String commandAlias, final String[] commandArgs) {
        return Collections.emptyList();
    }

    protected List<String> getPlayers(final EssentialsEntity interactor) {
        List<String> players = Lists.newArrayList();
        for (UserEntity user : plugin.getOnlineUsers()) {
            if (interactor.canInteractWith(user)) {
                players.add(user.getName());
            }
        }
        return players;
    }

    public String getName() {
        return name;
    }

    public void setPlugin(MaikEssentials plugin) {
        this.plugin = plugin;
    }

    public MaikEssentials getPlugin() {
        return plugin;
    }
}
