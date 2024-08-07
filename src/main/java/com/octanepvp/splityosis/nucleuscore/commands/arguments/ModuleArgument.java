package com.octanepvp.splityosis.nucleuscore.commands.arguments;

import com.octanepvp.splityosis.commandsystem.SYSArgument;
import com.octanepvp.splityosis.commandsystem.SYSCommand;
import com.octanepvp.splityosis.nucleuscore.Nucleus;
import com.octanepvp.splityosis.nucleuscore.module.Module;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleArgument extends SYSArgument {
    private Nucleus nucleus;

    public ModuleArgument(Nucleus nucleus) {
        this.nucleus = nucleus;
    }

    @Override
    public boolean isValid(String s) {
        return nucleus.getModuleLoader().getModule(s) != null;
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
            return Arrays.asList(nucleus.getLOG_PREFIX() + " &cUnknown module '"+s+"'.");
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> complete = new ArrayList<>();
        for (Module module : nucleus.getModuleLoader().getLoadedModules())
            complete.add(module.getName());
        return complete;
    }
}
