package utils;

import main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigUtils {

    private ConfigUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static FileConfiguration customConfig;

    public static void createCustomConfig() {

        File customConfigFile = new File(Main.getInstance().getDataFolder(), "config.yml");

        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return customConfig.getString(key);
    }

    public static List<String> getStringList(String key) {
        return customConfig.getStringList(key);
    }

    public static boolean isCommandEnabled(String name) {
        return customConfig.getStringList("sub-commands").contains(name);
    }

}
