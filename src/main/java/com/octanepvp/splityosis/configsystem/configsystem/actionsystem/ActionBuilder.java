package com.octanepvp.splityosis.configsystem.configsystem.actionsystem;

import org.bukkit.Sound;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionBuilder {

    private List<ActionData> actionDataList = new ArrayList<>();

    public ActionBuilder(){};

    /**
     * Adds an action to the list of actions.
     * @param actionKey {@link ActionType}
     * @param parameters
     * @return the ActionBuilder
     */
    public ActionBuilder addAction(String actionKey, String... parameters){
        actionDataList.add(new ActionData(actionKey, Arrays.asList(parameters)));
        return this;
    }

    public ActionBuilder addAction(List<ActionData> data){
        actionDataList.addAll(data);
        return this;
    }

    public ActionBuilder addAction(ActionData data){
        actionDataList.add(data);
        return this;
    }

    public ActionBuilder addAction(Actions actions){
        actionDataList.addAll(actions.getActionDataList());
        return this;
    }

    public ActionBuilder message(String message){
        return addAction("MESSAGE", message);
    }

    public ActionBuilder sound(@NotNull Sound sound){
        return addAction("SOUND", sound.name());
    }

    public ActionBuilder title(String... params){
        return addAction("TITLE", params);
    }

    public ActionBuilder actionBar(String... params){
        return addAction("ACTIONBAR", params);
    }

    public ActionBuilder wait(int ticks){
        return addAction("WAIT", String.valueOf(ticks));
    }


    /**
     * Builds the Actions object with the actions added.
     * @return the Action
     */
    public Actions build(){
        return new Actions(actionDataList);
    }

    /**
     * Creates a simple action with a message and sound.
     * @param sound
     * @param title
     * @param subtitle
     * @param msg
     * @return title Actions
     */
    public static Actions simpleTitle(@Nullable Sound sound, @Nullable String title, @Nullable String subtitle, @Nullable String... msg){
        ActionBuilder builder = new ActionBuilder();
        if (msg != null){
            builder.addAction("MESSAGE", msg);
        }
        if (sound != null){
            builder.addAction("SOUND", sound.name());
        }
        if (title != null && subtitle != null)
            builder.addAction("TITLE", title, subtitle, "20", "20", "20");
        return builder.build();
    }

    /**
     * Creates a simple action with a message.
     * @param message
     * @return message Actions
     */
    public static Actions simpleMessage(@NotNull String message){
        return new ActionBuilder().addAction("MESSAGE", message).build();
    }

}
