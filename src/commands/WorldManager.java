package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reflections.Reflections;
import utils.ConfigUtils;
import utils.MessageUtils;

import java.util.Arrays;
import java.util.HashMap;

public class WorldManager implements CommandExecutor {

    private HashMap<String, SubCommand> commands = new HashMap<>();

    public WorldManager() {
        new Reflections("commands.subcommands").getSubTypesOf(SubCommand.class).forEach(clazz -> {
            try {
                commands.put(clazz.getSimpleName().toUpperCase(), clazz.newInstance());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("worldmanager")) {
            MessageUtils.sendMessage((Player) sender, ConfigUtils.getValue("noPermission"));
            return true;
        }

        if(args.length < 1) {
            displayHelp(sender);
            return true;
        }

        if(commands.containsKey(args[0].toUpperCase())) {

            if(!ConfigUtils.isCommandEnabled(args[0].toLowerCase())) {
                MessageUtils.sendMessage((Player) sender, ConfigUtils.getValue("commandDisabled"));
                return true;
            }

            commands.get(args[0].toUpperCase()).execute((Player) sender, args);
            return true;
        }

        displayHelp(sender);
        return true;
    }

    private void displayHelp(CommandSender sender) {
        Arrays.stream(ConfigUtils.getValue("helpMessage").split("%n%")).forEach(s -> MessageUtils.sendMessage((Player) sender, s));
    }

}
