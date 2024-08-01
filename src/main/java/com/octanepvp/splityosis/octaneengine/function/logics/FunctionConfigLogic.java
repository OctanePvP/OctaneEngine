package com.octanepvp.splityosis.octaneengine.function.logics;

import com.octanepvp.splityosis.octaneengine.function.Function;
import com.octanepvp.splityosis.octaneengine.function.exceptions.InvalidFunctionTypeException;
import com.octanepvp.splityosis.octaneengine.function.mathfunction.MathFunction;
import com.octanepvp.splityosis.configsystem.configsystem.ConfigTypeLogic;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FunctionConfigLogic extends ConfigTypeLogic<Function> {

    @Override
    public Function getFromConfig(ConfigurationSection config, String path) {
        List<String> data = config.getStringList(path + ".data");
        String type = config.getString(path + ".type");
        String variablesStr = config.getString(path + ".variables-DONT-TOUCH");
        String[] variables = new String[0];
        if (variablesStr != null && !variablesStr.isEmpty())
            variables = variablesStr.trim().split(Pattern.quote(","));

        if (type == null)
            throw new InvalidFunctionTypeException("Missing function type at '"+path+"'");
        type = type.trim();
        if (type.equalsIgnoreCase("MathFunction")){
            return new MathFunction(data.toArray(new String[0])).variables(variables).compile();
        }
        else
            throw new InvalidFunctionTypeException("Unknown function type '"+type+"'.");
    }

    @Override
    public void setInConfig(Function function, ConfigurationSection config, String path) {
        StringBuilder variablesBuilder = new StringBuilder();
        for (String s : function.getVariables().keySet())
            variablesBuilder.append(s).append(",");
        String variables = variablesBuilder.substring(0, variablesBuilder.length()-1);
        config.set(path + ".variables-DONT-TOUCH", variables);
        if (function instanceof MathFunction)
            config.set(path + ".type", "MathFunction");
        config.set(path + ".data", Arrays.asList(function.getData()));
    }
}
