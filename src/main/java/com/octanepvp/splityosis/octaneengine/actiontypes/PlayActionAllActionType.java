package com.octanepvp.splityosis.octaneengine.actiontypes;

import com.octanepvp.splityosis.octaneengine.OctaneEngine;
import dev.splityosis.configsystem.configsystem.actionsystem.ActionType;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;
import dev.splityosis.configsystem.configsystem.actionsystem.InvalidActionParameterException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PlayActionAllActionType extends ActionType {

    private OctaneEngine plugin;

    public PlayActionAllActionType(OctaneEngine plugin) {
        super("PlayActionAll", "PlayActionsAll", "ActionsAll", "ActionAll", "presetAll", "presetsAll", "play_action_all", "play_actions_all");
        this.plugin = plugin;
    }

    @Override
    public void run(@Nullable Player player, @NotNull List<String> list, @NotNull Map<String, String> map) {
        for (String s : list) {
            Actions actions = plugin.getAction(s);
            if (actions == null){
                new InvalidActionParameterException("Unknown action '"+s+"'").printStackTrace();
                continue;
            }
            actions.performOnAll();
        }

    }
}
