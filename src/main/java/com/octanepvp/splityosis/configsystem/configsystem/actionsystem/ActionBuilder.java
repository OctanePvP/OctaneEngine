package com.octanepvp.splityosis.configsystem.configsystem.actionsystem;

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

    /**
     * Builds the Actions object with the actions added.
     * @return the Action
     */
    public Actions build(){
        return new Actions(actionDataList);
    }





}
