package dev.maik.minecraftplugins.maikessentials.entitys;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class EssentialsEntity {
    protected CommandSender base;

    public Player getPlayer() {
        if (!(base instanceof Player)) {
            return null;
        }

        return (Player) base;
    }

    public final boolean isPlayer() {
        return (base instanceof Player);
    }

    public void sendMessage(String message) {
        if (message.isEmpty()) {
            return;
        }

        base.sendMessage(message);
    }

    public boolean canInteractWith(UserEntity interactee) {
        if (this.isPlayer()) {
            if (this.equals(interactee)) {
                return true;
            }

            return this.getPlayer().canSee(interactee.getPlayer());
        }

        return true; // console

    }
}
