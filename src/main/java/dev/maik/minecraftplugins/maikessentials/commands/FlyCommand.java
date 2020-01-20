package dev.maik.minecraftplugins.maikessentials.commands;

import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class FlyCommand extends EssentialsCommand {
    public FlyCommand() {
        super("fly");
    }

    @Override
    protected List<String> getTabCompleteOptions(final UserEntity userEntity, final String commandAlias, final String[] args) {
        if (args.length == 1 && userEntity.isAuthorized("maikessentials.fly.others")) {
            return getPlayers(userEntity);
        }

        return Collections.emptyList();
    }

    @Override
    protected List<String> getTabCompleteOptions(final CommandSourceEntity commandSourceEntity, final String commandAlias, final String[] args) {
        if (args.length == 1) {
            return getPlayers(commandSourceEntity);
        }

        return Collections.emptyList();
    }
}
