package com.octanepvp.splityosis.nucleuscore.commands;

import com.octanepvp.splityosis.commandsystem.SYSCommandBranch;
import com.octanepvp.splityosis.nucleuscore.Nucleus;

public class CommandsCommandBranch extends SYSCommandBranch {

    private Nucleus nucleus;

    public CommandsCommandBranch(Nucleus nucleus, String basePermission) {
        super("Commands", "Command", "Cmd", "Cmds");
        this.nucleus = nucleus;

        setPermission(basePermission + ".commands");
    }
}
