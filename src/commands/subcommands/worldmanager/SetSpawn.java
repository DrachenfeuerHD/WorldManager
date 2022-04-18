package commands.subcommands.worldmanager;

import commands.SubCommand;
import commands.WorldManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;

public class SetSpawn extends SubCommand {

    public SetSpawn() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(!sender.hasPermission("worldmanager.setspawn")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        Location loc = sender.getLocation();

        sender.getWorld().setSpawnLocation((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        MessageUtils.sendMessage(sender, ConfigUtils.getValue("spawnChanged"));
    }
}
