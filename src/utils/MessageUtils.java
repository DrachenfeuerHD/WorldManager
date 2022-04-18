package utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    private MessageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendMessage(Player p, String message) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigUtils.getValue("prefix") + " " + message));
    }

}
