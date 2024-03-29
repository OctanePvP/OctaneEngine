package com.octanepvp.splityosis.octaneengine.commands;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import dev.splityosis.commandsystem.SYSCommandBranch;

public class OctaneEngineCommandBranch extends SYSCommandBranch {

        public OctaneEngineCommandBranch(OctaneEngine plugin) {
                super("OctaneEngine", "OEngine");
                setPermission("OctaneEngine.admin");

                addBranch(new ActionsCommandBranch(plugin));
        }
}
