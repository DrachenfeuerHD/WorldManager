package utils;

import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldUtils {

    private WorldUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getWorldsFormatted() {

        StringBuilder message = new StringBuilder();
        List<String> verifyWorlds = new ArrayList<>();

        for(World w : Bukkit.getWorlds()) {
            message.append(ConfigUtils.getValue("loadedWorldDesign").replace("%world%", w.getName())).append(" ");
            verifyWorlds.add(w.getName());
        }

        for(File f : Bukkit.getServer().getWorldContainer().listFiles())
            if(!verifyWorlds.contains(f.getName()) && f.isDirectory() && !ConfigUtils.getStringList("excludedSearchFolders").contains(f.getName()))
                message.append(ConfigUtils.getValue("unloadedWorldDesign").replace("%world%", f.getName())).append(" ");

        return message.toString().trim();
    }

    public static boolean existsWorld(String name) {
        return Bukkit.getWorlds().stream().anyMatch(world -> world.getName().equalsIgnoreCase(name));
    }

    public static void unloadWorld(String name) {

        World w = Bukkit.getWorld(name);

        w.setKeepSpawnInMemory(false);
        w.setAutoSave(false);

        w.getPlayers().forEach(p -> {
            MessageUtils.sendMessage(p, ConfigUtils.getValue("currentWorldUnloaded"));
            p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        });

        for(Chunk c : w.getLoadedChunks())
            c.unload(true);

        Bukkit.unloadWorld(w, true);

    }

    public static void deleteWorld(String name) {

        World w = Bukkit.getWorld(name);

        unloadWorld(name);

        deleteFile(w.getWorldFolder());

    }

    public static String getInformationAboutWorld(String name) {

        if(!existsWorld(name))
            return ConfigUtils.getValue("providedWorldDoesNotExist");

        World w = Bukkit.getWorld(name);

        String message = ConfigUtils.getValue("worldInformation");

        message = message.replace("%name%", w.getName());
        message = message.replace("%playerCount%", "" + w.getPlayers().size());
        message = message.replace("%seed%", "" + w.getSeed());
        message = message.replace("%uuid%", w.getUID().toString());
        message = message.replace("%maxHeight%", "" + w.getMaxHeight());
        message = message.replace("%entities%", "" + w.getEntities().size());

        return message;
    }

    public static boolean isWorldNotAllowedToModify(World world) {
        return world.getName().equals(Bukkit.getWorlds().get(0).getName());
    }

    private static void deleteFile(File file) {
        if(file.isDirectory())
            for (File c : file.listFiles())
                deleteFile(c);

        if(!file.delete())
            Bukkit.getConsoleSender().sendMessage(ConfigUtils.getValue("prefix").replace("&", "§") + " " + ConfigUtils.getValue("deletedFileError").replace("%fileName%", file.getName()));
    }

    public static boolean loadWorld(String name) {

        boolean directoryExists = false;

        for(File f : Bukkit.getServer().getWorldContainer().listFiles())
            if(f.getName().equals(name) && f.isDirectory() && !ConfigUtils.getStringList("excludedSearchFolders").contains(f.getName()))
                directoryExists = true;

        if(!directoryExists) return false;

        Main.getInstance().getServer().createWorld(new WorldCreator(name));

        return true;
    }
}
