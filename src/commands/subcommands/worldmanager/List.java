package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

public class List extends SubCommand {

    public List() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(!sender.hasPermission("worldmanager.list")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        MessageUtils.sendMessage(sender, WorldUtils.getWorldsFormatted());
    }
}
