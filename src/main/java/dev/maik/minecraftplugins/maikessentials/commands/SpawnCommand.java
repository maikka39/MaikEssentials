package dev.maik.minecraftplugins.maikessentials.commands;

import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SpawnCommand extends EssentialsCommand {
    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public void run(final UserEntity userEntity, final String commandAlias, final String[] commandArgs) {
//        if (!userEntity.isAuthorized("maikessentials.spawn.cooldown.bypass")) {
//            userEntity.healCooldown();
//        }

        switch (commandArgs.length) {
            case 0: {
                spawnPlayer(userEntity);

                userEntity.sendMessage(ChatUtil.convert(getPlugin().getConfig("messages.yml").getString("move_to_spawn"), Map.of(
                        "player", userEntity.getPlayer().getDisplayName()
                )));
                break;
            }
            case 1: {
                if (!userEntity.isAuthorized("essentials.spawn.others")) {
                    return;
                }

                UserEntity receiver = new UserEntity(getPlugin().getServer().getPlayer(commandArgs[0]));

                spawnPlayer(receiver);

                userEntity.sendMessage(ChatUtil.convert(getPlugin().getConfig("messages.yml").getString("move_to_spawn_other_sender"), Map.of(
                        "player", receiver.getPlayer().getDisplayName(),
                        "sender", userEntity.getPlayer().getDisplayName()
                )));

                receiver.sendMessage(ChatUtil.convert(getPlugin().getConfig("messages.yml").getString("move_to_spawn_other_player"), Map.of(
                        "player", receiver.getPlayer().getDisplayName(),
                        "sender", userEntity.getPlayer().getDisplayName()
                )));

                break;
            }
        }


    }

    private void spawnPlayer(final UserEntity userEntity) {
        final Player player = userEntity.getPlayer();

        player.setFallDistance(0);
        player.teleportAsync(player.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
    }


    @Override
    protected List<String> getTabCompleteOptions(final UserEntity userEntity, final String commandAlias, final String[] args) {
        if (args.length == 1 && userEntity.isAuthorized("essentials.spawn.others")) {
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
