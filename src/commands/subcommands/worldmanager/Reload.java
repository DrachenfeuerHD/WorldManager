package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;

public class Reload extends SubCommand {

    public Reload() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(!sender.hasPermission("worldmanager.reload")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        ConfigUtils.createCustomConfig();
        MessageUtils.sendMessage(sender, ConfigUtils.getValue("configReloaded"));
    }
}
