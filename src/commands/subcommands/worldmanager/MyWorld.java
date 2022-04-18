package commands.subcommands.worldmanager;

import commands.SubCommand;
import commands.WorldManager;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;

public class MyWorld extends SubCommand {

    public MyWorld() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {

        if (!sender.hasPermission("worldmanager.myworld")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        MessageUtils.sendMessage(sender, ConfigUtils.getValue("currentWorld").replace("%worldName%", sender.getWorld().getName()));

    }

}
