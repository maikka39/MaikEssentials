package dev.maik.minecraftplugins.maikessentials.commands;

import dev.maik.minecraftplugins.maikessentials.entitys.CommandSourceEntity;
import dev.maik.minecraftplugins.maikessentials.entitys.UserEntity;
import dev.maik.minecraftplugins.maikessentials.utils.ChatUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class EnchantCommand extends EssentialsCommand {
    public EnchantCommand() {
        super("heal");
    }

    @Override
    public void run(final UserEntity userEntity, final String commandAlias, final String[] commandArgs) {
        switch (commandArgs.length) {
            case 0:
            case 1: {
                return;
            }
            case 2: {
                final ItemStack item = userEntity.getPlayer().getInventory().getItemInMainHand();
                final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(commandArgs[0].replace("minecraft:", "")));
                final int enchantmentLevel = Integer.parseInt(commandArgs[1]);

                enchantItem(item, enchantment, enchantmentLevel);

                userEntity.sendMessage(ChatUtil.convert(getPlugin().getConfig("messages.yml").getString("enchant_item"), Map.of(
                        "player", userEntity.getPlayer().getDisplayName(),
                        "item", item.getItemMeta().getDisplayName()
                )));
                break;
            }
        }
    }

    private void enchantItem(final ItemStack itemStack, final Enchantment enchantment, final int level) {
        // TODO: 1/21/2020 Check for unsafe enchantments
        itemStack.addUnsafeEnchantment(enchantment, level);

    }


    @Override
    protected List<String> getTabCompleteOptions(final UserEntity userEntity, final String commandAlias, final String[] commandArgs) {
        return getTabCompleteOptions(userEntity.getSource(), commandAlias, commandArgs);
    }

    @Override
    protected List<String> getTabCompleteOptions(final CommandSourceEntity commandSourceEntity, final String commandAlias, final String[] commandArgs) {
        switch (commandArgs.length) {
            case 1: {
                ArrayList<String> enchantments = new ArrayList<>();

                for (Enchantment enchantment : Enchantment.values()) {
                    enchantments.add(enchantment.getKey().toString());
                    enchantments.add(enchantment.getKey().toString().replace("minecraft:", ""));
                }

                return enchantments;
            }
            case 2: {
                return IntStream.rangeClosed(0, 5)
                        .boxed().map(Object::toString).collect(Collectors.toList());
            }
            default: {
                return Collections.emptyList();
            }
        }

    }
}
