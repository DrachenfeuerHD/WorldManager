package commands;

import org.bukkit.entity.Player;

public class SubCommand {

    Class<?> command;

    public SubCommand(Class<?> command) {
        this.command = command;
    }

    public Class<?> getCommand() {
        return command;
    }

    public void execute(Player sender, String[] args) {}

}
