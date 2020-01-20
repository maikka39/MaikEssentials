package dev.maik.minecraftplugins.maikessentials.entitys;

import org.bukkit.command.CommandSender;

public class CommandSourceEntity extends EssentialsEntity {
    public CommandSourceEntity(CommandSender base) {
        this.base = base;
    }
}
