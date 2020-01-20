package dev.maik.minecraftplugins.maikessentials.entitys;

import dev.maik.minecraftplugins.maikessentials.commands.EssentialsCommand;
import org.bukkit.entity.Player;

public class UserEntity extends EssentialsEntity {
    private final Player player;

    public UserEntity(Player player) {
        this.base = player;
        this.player = player;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAuthorized(final EssentialsCommand cmd) {
        return isAuthorized("maikessentials." + cmd.getName());
    }

    public boolean isAuthorized(final String node) {
        return player.hasPermission(node);
    }

    public String getName() {
        return player.getName();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public CommandSourceEntity getSource() {
        return new CommandSourceEntity(base);
    }
}
