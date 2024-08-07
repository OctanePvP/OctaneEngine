package com.octanepvp.splityosis.nucleuscore.commands;

import com.octanepvp.splityosis.commandsystem.SYSCommand;
import com.octanepvp.splityosis.commandsystem.SYSCommandBranch;
import com.octanepvp.splityosis.nucleuscore.Nucleus;
import com.octanepvp.splityosis.nucleuscore.NucleusUtil;
import com.octanepvp.splityosis.nucleuscore.commands.arguments.DisabledModuleArgument;
import com.octanepvp.splityosis.nucleuscore.commands.arguments.EnabledModuleArgument;
import com.octanepvp.splityosis.nucleuscore.commands.arguments.ModuleArgument;
import com.octanepvp.splityosis.nucleuscore.module.Module;
import org.bukkit.entity.Player;

public class ModulesCommandBranch extends SYSCommandBranch {

    private Nucleus nucleus;

    public ModulesCommandBranch(Nucleus nucleus, String basePermission) {
        super("Modules", "Features", "m");
        this.nucleus = nucleus;
        setPermission(basePermission + ".modules");

        addCommand(new SYSCommand("List").executes((commandSender, strings) -> {
            for (Module enabledModule : nucleus.getModuleLoader().getEnabledModules())
                NucleusUtil.sendMessage(commandSender, "&a"+enabledModule.getName());
            for (Module disabledModule : nucleus.getModuleLoader().getDisabledModules())
                NucleusUtil.sendMessage(commandSender, "&c"+disabledModule.getName());
        }));

        addCommand(new SYSCommand("Enable")
                .setArguments(new DisabledModuleArgument(nucleus))
                .executes((commandSender, strings) -> {
                    Module module = nucleus.getModuleLoader().getModule(strings[0]);
                    boolean success = nucleus.getModuleLoader().enableModule(module);
                    if (commandSender instanceof Player) {
                        if (success)
                            nucleus.sendPrefixedMessage(commandSender, "&eSuccessfully &aenabled &emodule &a&l" + module.getName() + "&e.");
                        else
                            nucleus.sendPrefixedMessage(commandSender, "&cAn error occurred while enabling module &c&l" + module.getName() + "&c.");
                    }
                }));

        addCommand(new SYSCommand("Disable")
                .setArguments(new EnabledModuleArgument(nucleus))
                .executes((commandSender, strings) -> {
                    Module module = nucleus.getModuleLoader().getModule(strings[0]);
                    boolean success = nucleus.getModuleLoader().disableModule(module, true);
                    if (commandSender instanceof Player) {
                        if (success)
                            nucleus.sendPrefixedMessage(commandSender, "&eSuccessfully &cdisabled &emodule &a&l" + module.getName() + "&e.");
                        else
                            nucleus.sendPrefixedMessage(commandSender, "&cAn error occurred while disabling module &c&l" + module.getName() + "&c.");
                    }
                }));

        addCommand(new SYSCommand("Reload")
                .setArguments(new EnabledModuleArgument(nucleus))
                .executes((commandSender, strings) -> {
                    Module module = nucleus.getModuleLoader().getModule(strings[0]);
                    boolean success = nucleus.getModuleLoader().reloadModule(module);
                    if (commandSender instanceof Player) {
                        if (success)
                            nucleus.sendPrefixedMessage(commandSender, "&eSuccessfully reloaded module &a&l" + module.getName() + "&e.");
                        else
                            nucleus.sendPrefixedMessage(commandSender, "&cAn error occurred while reloading module &c&l" + module.getName() + "&c.");
                    }
                }));

        addCommand(new SYSCommand("Info")
                .setArguments(new ModuleArgument(nucleus))
                .executes((commandSender, strings) -> {
                    Module module = nucleus.getModuleLoader().getModule(strings[0]);
                    nucleus.sendPrefixedMessage(commandSender, "&e&l"+module.getName() + "&e info:");

                    String[] authors = module.getSignature().authors();
                    if (authors.length == 1)
                        NucleusUtil.sendMessage(commandSender, "&eAuthor: &b"+authors[0]);
                    else if (authors.length == 2)
                        NucleusUtil.sendMessage(commandSender, "&eAuthors: &b"+authors[0] + "and "+authors[1]);
                    else {
                        StringBuilder stringBuilder = new StringBuilder(authors[0]);
                        for (int i = 1; i < authors.length-1; i++)
                            stringBuilder.append(", ").append(authors[i]);
                        stringBuilder.append(" and ").append(authors[authors.length-1]);
                        NucleusUtil.sendMessage(commandSender, "&eAuthors: &b"+stringBuilder);
                    }

                    NucleusUtil.sendMessage(commandSender, "&eDescription: &b"+module.getDescription()+".");

                    if (module.getRequiredPlugins().length != 0){
                        StringBuilder plugins = new StringBuilder();
                        for (String requiredPlugin : module.getRequiredPlugins())
                            plugins.append(requiredPlugin).append(", ");
                        NucleusUtil.sendMessage(commandSender, "&eRequired plugins: &b"+plugins.substring(0, plugins.length()-2));
                    }

                    if (module.getRequiredModules().length != 0){
                        StringBuilder modules = new StringBuilder();
                        for (String requiredModule : module.getRequiredModules())
                            modules.append(requiredModule).append(", ");
                        NucleusUtil.sendMessage(commandSender, "&eRequired modules: &b"+modules.substring(0, modules.length()-2));
                    }
                }));
    }
}
