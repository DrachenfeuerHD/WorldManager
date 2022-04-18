package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

public class Unload extends SubCommand {

    public Unload() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(!sender.hasPermission("worldmanager.unload")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        if(args.length < 2) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("notEnoughArguments").replace("%amount%", "2"));
            return;
        }
        if(Bukkit.getWorld(args[1]) == null) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("providedWorldDoesNotExist"));
            return;
        }

        if(WorldUtils.isWorldNotAllowedToModify(Bukkit.getWorld(args[1]))) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("worldNotAllowedToBeModified"));
            return;
        }

        WorldUtils.unloadWorld(args[1]);
        MessageUtils.sendMessage(sender, ConfigUtils.getValue("successfullyUnloadedWorld"));
    }
}
