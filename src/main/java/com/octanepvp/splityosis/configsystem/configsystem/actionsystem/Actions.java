package com.octanepvp.splityosis.configsystem.configsystem.actionsystem;

import com.octanepvp.splityosis.configsystem.configsystem.ConfigSystem;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.ActionData;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.ActionType;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.InvalidActionParameterException;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.InvalidActionTypeException;
import com.octanepvp.splityosis.configsystem.configsystem.actionsystem.actiontypes.DelayActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actions {

    private List<ActionData> actionDataList;

    public Actions(List<ActionData> actionDataList) {
        this.actionDataList = actionDataList;
        if (actionDataList == null)
            this.actionDataList = new ArrayList<>();
    }

    /**
     * Parses the given list of strings into an Actions object.
     * @param rawInput List of strings to be parsed
     * @return Actions object
     */

    public static Actions parseActions(List<String> rawInput){
        List<ActionData> actionDataList = new ArrayList<>();
        for (String s : rawInput)
            try {
                actionDataList.add(ActionData.parseString(s));
            }catch (Exception e){
                new ActionsFormatParseException(s).printStackTrace();
            }
        return new Actions(actionDataList);
    }


    /**
     * Runs all the actions where the target is the given player and sets PlaceholderAPI (if exists) on given player.
     * @param player Target
     * @param placeholders Placeholders that will be set, Map in the form of <From, To>
     */
    public void perform(@Nullable Player player, @Nullable Map<String, String> placeholders){
        if (placeholders == null)
            placeholders = new HashMap<>();
        handle(0, player, placeholders);
    }

    /**
     * Runs all the actions where the target is the given player and sets PlaceholderAPI (if exists) on given player.
     * @param player Target
     */
    public void perform(@Nullable Player player){
        perform(player, null);
    }

    private void handle(int i, Player player, Map<String, String> placeholders){
        for (; i < actionDataList.size(); i++) {
            ActionData actionData = actionDataList.get(i);
            ActionType actionType = ActionType.getActionType(actionData.getActionKey());
            if (actionType == null)
                new InvalidActionTypeException(actionData.getActionKey()).printStackTrace();
            else if (actionType instanceof DelayActionType && ConfigSystem.plugin != null){
                if (actionData.getParameters().isEmpty()) continue;
                int ticks;
                try {
                    ticks = Integer.parseInt(actionData.getParameters().get(0));
                }catch (Exception e){
                    new InvalidActionParameterException("Invalid wait time '"+actionData.getParameters().get(0)).printStackTrace();
                    continue;
                }

                int finalI = i;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        handle(finalI +1, player, placeholders);
                    }
                }.runTaskLater(ConfigSystem.plugin, ticks);
                return;
            }
            else {
                try {
                    actionType.run(player, actionData.getParameters(), placeholders);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Runs the same logic as {perform} on all the players online.
     */
    public void performOnAll(@Nullable Map<String, String> placeholders){
        if (placeholders == null)
            placeholders = new HashMap<>();
        handleAll(0, placeholders);
    }

    /**
     * Runs the same logic as {@link #perform(Player)} on all the players online.
     */
    public void performOnAll(){
        performOnAll(null);
    }

    public void handleAll(int i, Map<String, String> placeholders){
        for (; i < actionDataList.size(); i++) {
            ActionData actionData = actionDataList.get(i);
            ActionType actionType = ActionType.getActionType(actionData.getActionKey());
            if (actionType == null)
                new InvalidActionTypeException(actionData.getActionKey()).printStackTrace();
            else if (actionType instanceof DelayActionType && ConfigSystem.plugin != null){
                if (actionData.getParameters().size() == 0) continue;
                int ticks;
                try {
                    ticks = Integer.parseInt(actionData.getParameters().get(0));
                }catch (Exception e){
                    new InvalidActionParameterException("Invalid wait time '"+actionData.getParameters().get(0)).printStackTrace();
                    continue;
                }

                int finalI = i;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        handleAll(finalI +1, placeholders);
                    }
                }.runTaskLater(ConfigSystem.plugin, ticks);
                return;
            }
            else
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    try {
                        actionType.run(onlinePlayer, actionData.getParameters(), placeholders);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        }
    }

    public List<ActionData> getActionDataList() {
        return actionDataList;
    }
}
