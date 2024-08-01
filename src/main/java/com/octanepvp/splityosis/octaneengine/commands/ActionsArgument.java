package com.octanepvp.splityosis.octaneengine.commands;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import com.octanepvp.splityosis.commandsystem.SYSArgument;
import com.octanepvp.splityosis.commandsystem.SYSCommand;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionsArgument extends SYSArgument {

    private OctaneEngine plugin;

    public ActionsArgument(OctaneEngine plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isValid(String input) {
        return plugin.getAction(input) != null;
    }

    @Override
    public List<String> getInvalidInputMessage(String input) {
        return Arrays.asList("&cUnknown Action '"+input+"'");
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> complete = new ArrayList<>();
        for (String s : new ArrayList<>(plugin.getActionsMap().keySet())) {
            if (s.startsWith(input.toLowerCase()))
                complete.add(s);
        }
        return complete;
    }
}
