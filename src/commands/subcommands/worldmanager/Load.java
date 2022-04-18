package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

public class Load extends SubCommand {

    public Load() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(!sender.hasPermission("worldmanager.load")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        if(args.length < 2) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("notEnoughArguments").replace("%amount%", "2"));
            return;
        }

        if(Bukkit.getWorld(args[1]) != null) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("worldAlreadyLoaded"));
            return;
        }

        if(WorldUtils.loadWorld(args[1])) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("successfullyLoadedWorld"));
        } else {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("providedWorldDoesNotExist"));
        }
    }
}
