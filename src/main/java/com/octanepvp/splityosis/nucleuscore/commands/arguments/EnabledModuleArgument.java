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

public class EnabledModuleArgument extends SYSArgument {

    private Nucleus nucleus;

    public EnabledModuleArgument(Nucleus nucleus) {
        this.nucleus = nucleus;
    }

    @Override
    public boolean isValid(String s) {
        Module module = nucleus.getModuleLoader().getModule(s);
        return module != null && nucleus.getModuleLoader().isModuleEnabled(module);
    }

    @Override
    public List<String> getInvalidInputMessage(String s) {
        Module module = nucleus.getModuleLoader().getModule(s);
        if (module == null)
            return Arrays.asList(nucleus.getLOG_PREFIX() + " &cUnknown module '"+s+"'.");
        return Arrays.asList(nucleus.getLOG_PREFIX() + " &cModule '"+module.getName()+"' is disabled, you must provide an enabled module.");
    }

    @Override
    public @NonNull List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> complete = new ArrayList<>();
        for (Module enabledModule : nucleus.getModuleLoader().getEnabledModules())
            complete.add(enabledModule.getName());
        return complete;
    }
}
