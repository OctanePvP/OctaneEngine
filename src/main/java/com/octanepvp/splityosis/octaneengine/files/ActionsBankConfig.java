package com.octanepvp.splityosis.octaneengine.files;

import com.octanepvp.splityosis.octaneengine.files.logics.ActionsMap;
import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import dev.splityosis.configsystem.configsystem.InvalidConfigFileException;
import dev.splityosis.configsystem.configsystem.actionsystem.ActionData;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;

import java.io.File;
import java.util.Arrays;

public class ActionsBankConfig extends AnnotatedConfig {
    public ActionsBankConfig(File file) throws InvalidConfigFileException {
        super(file);
    }

    @ConfigField(path = "actions")
    public ActionsMap actionsMap = getDefault();


    public static ActionsMap getDefault(){
        ActionsMap actionsMap = new ActionsMap();
        actionsMap.put("example-1", new Actions(Arrays.asList(
                new ActionData("MESSAGE", Arrays.asList("3")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("2")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("1")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("BOOM"))
                )));

        actionsMap.put("example-2", new Actions(Arrays.asList(
                new ActionData("MESSAGE", Arrays.asList("5")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("4")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("3")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("2")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("1")),
                new ActionData("WAIT", Arrays.asList("20")),
                new ActionData("MESSAGE", Arrays.asList("BOOM"))
        )));
        return actionsMap;
    }
}
