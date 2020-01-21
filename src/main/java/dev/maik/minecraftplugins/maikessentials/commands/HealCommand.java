package dev.maik.minecraftplugins.maikessentials.commands;

import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class HealCommand extends EssentialsCommand {
    public HealCommand() {
        super("heal");
    }

    @Override
    public void run(final UserEntity userEntity, final String commandAlias, final String[] commandArgs) {
//        if (!userEntity.isAuthorized("maikessentials.heal.cooldown.bypass")) {
//            userEntity.healCooldown();
//        }

//        if (commandArgs.length > 0 && userEntity.isAuthorized("maikessentials.heal.others")) {
//            loopOnlinePlayers(userEntity.getSource(), true, true, args[0], null);
//            return;
//        }

        healPlayer(userEntity);

        userEntity.sendMessage(ChatUtil.convert(getPlugin().getConfig("messages.yml").getString("heal_player"), Map.of(
                "player", userEntity.getPlayer().getDisplayName()
        )));
    }

    private void healPlayer(final UserEntity userEntity) {
        final Player player = userEntity.getPlayer();

        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setSaturation(5);
        player.setExhaustion(0);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }


    @Override
    protected List<String> getTabCompleteOptions(final UserEntity userEntity, final String commandAlias, final String[] args) {
        if (args.length == 1 && userEntity.isAuthorized("maikessentials.heal.others")) {
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
