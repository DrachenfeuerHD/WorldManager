package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

public class Delete extends SubCommand {

    public Delete() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {

        if(!sender.hasPermission("worldmanager.delete")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        if(args.length < 2) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("notEnoughArguments").replace("%amount%", "2"));
            return;
        }

        if(!WorldUtils.existsWorld(args[1])) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("providedWorldDoesNotExist"));
            return;
        }

        if(WorldUtils.isWorldNotAllowedToModify(Bukkit.getWorld(args[1]))) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("worldNotAllowedToBeModified"));
            return;
        }

        WorldUtils.deleteWorld(args[1]);
        MessageUtils.sendMessage(sender, ConfigUtils.getValue("successfullyDeleted").replace("%worldName%", args[1]));
    }
}
