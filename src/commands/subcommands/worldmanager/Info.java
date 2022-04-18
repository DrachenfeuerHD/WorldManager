package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

import java.util.Arrays;

public class Info extends SubCommand {

    public Info() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {

        if(!sender.hasPermission("worldmanager.info")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        if(args.length < 2) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("notEnoughArguments").replace("%amount%", "2"));
            return;
        }

        Arrays.stream(WorldUtils.getInformationAboutWorld(args[1]).split("%n%")).forEach(s -> MessageUtils.sendMessage(sender, s));
    }
}
