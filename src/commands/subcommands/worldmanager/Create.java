package commands.subcommands.worldmanager;

import commands.WorldManager;
import commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import utils.ConfigUtils;
import utils.MessageUtils;
import utils.WorldUtils;

public class Create extends SubCommand {

    public Create() {
        super(WorldManager.class);
    }

    @Override
    public void execute(Player sender, String[] args) {

        if(!sender.hasPermission("worldmanager.create")) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("noPermission"));
            return;
        }

        if(args.length < 3) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("notEnoughArguments").replace("%amount%", "3"));
            return;
        }

        if(WorldUtils.existsWorld(args[1])) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("worldAlreadyExists"));
            return;
        }

        World.Environment env;

        try {

            env = World.Environment.valueOf(args[2].toUpperCase());

        } catch(IllegalArgumentException e) {
            MessageUtils.sendMessage(sender, ConfigUtils.getValue("unknownWorldType"));
            return;
        }

        WorldCreator creator = new WorldCreator(args[1]).environment(env);

        if(args.length > 3) {
            WorldType type = WorldType.getByName(args[3].toUpperCase());

            if(type == null) {
                MessageUtils.sendMessage(sender, ConfigUtils.getValue("unknownParameters"));
                return;
            }

            creator.type(type);
        }

        MessageUtils.sendMessage(sender, ConfigUtils.getValue("startedCreating"));
        long currentTimeMillis = System.currentTimeMillis();

        World created = Bukkit.createWorld(creator);

        long time = System.currentTimeMillis() - currentTimeMillis;

        sender.teleport(created.getSpawnLocation());
        MessageUtils.sendMessage(sender, ConfigUtils.getValue("successfullyCreated")
                .replace("%name%", created.getName())
                .replace("%time%", "" + time));
    }

}
