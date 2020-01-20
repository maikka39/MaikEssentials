package dev.maik.minecraftplugins.maikessentials.utils;

import dev.maik.minecraftplugins.maikessentials.MaikEssentials;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Objects;

public class ConfigUtil {
    private final MaikEssentials plugin;

    public ConfigUtil(MaikEssentials plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration loadConfig(String name) {
        File mainConfigFile = new File(plugin.getDataFolder(), name);
        FileConfiguration mainConfig = new YamlConfiguration();

        if (!mainConfigFile.exists() || MaikEssentials.DEBUG) {
            //noinspection ResultOfMethodCallIgnored
            mainConfigFile.getParentFile().mkdirs();
            copy(Objects.requireNonNull(plugin.getResource(name)), mainConfigFile);
        }

        try {
            mainConfig.load(mainConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return mainConfig;
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
