package dev.maik.minecraftplugins.maikessentials.utils;

import org.bukkit.ChatColor;

import java.util.Map;

public class ChatUtil {
    public static String convert(String s, Map<String,String> map) {
        for (Map.Entry<String,String> entry : map.entrySet()) {
            s = s.replace(String.format("<%s>", entry.getKey()), entry.getValue());
        }

        return convert(s);
    }

    public static String convert(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
