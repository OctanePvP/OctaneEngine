package com.octanepvp.splityosis.octaneengine.commands;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import com.octanepvp.splityosis.commandsystem.SYSCommand;
import com.octanepvp.splityosis.commandsystem.SYSCommandBranch;
import com.octanepvp.splityosis.commandsystem.arguments.PlayerArgument;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.Actions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ActionsCommandBranch extends SYSCommandBranch {

    public ActionsCommandBranch(OctaneEngine plugin) {
        super("Actions", "Action");

        addCommand(new SYSCommand("Reload")
                .executes((sender, args) -> {
                    plugin.reloadActions();
                    sender.sendMessage(ChatColor.GOLD + "Reloaded Actions!");
                }));

        addCommand(new SYSCommand("Play")
                .setArguments(new ActionsArgument(plugin), new PlayerArgument())
                .executes((sender, args) -> {
                    Actions actions = plugin.getAction(args[0]);
                    sender.sendMessage(ChatColor.GOLD + "Playing action "+args[0]);
                    actions.perform(Bukkit.getPlayer(args[1]));
                }));
    }
}
