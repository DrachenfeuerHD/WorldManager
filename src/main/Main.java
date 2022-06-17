package main;
import commands.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ConfigUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        ConfigUtils.createCustomConfig();
        getCommand("WorldManager").setExecutor(new WorldManager());

        List<String> worldFolders = new ArrayList<>();
        List<String> excludeFolders = ConfigUtils.getStringList("excludedSearchFolders");

        for(File f : Bukkit.getServer().getWorldContainer().listFiles())
            if (f.isDirectory() && !excludeFolders.contains(f.getName()))
                worldFolders.add(f.getName());

        for(String worldName : worldFolders)
            getServer().createWorld(new WorldCreator(worldName));

        Bukkit.getConsoleSender().sendMessage(ConfigUtils.getValue("prefix").replace("&", "§") + " §aThe plugin has been enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ConfigUtils.getValue("prefix").replace("&", "§") + " §cThe plugin has been disabled");
    }

    public static Main getInstance() {
        return instance;
    }

}
