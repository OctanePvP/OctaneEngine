package com.octanepvp.splityosis.octaneengine.function;

import java.util.*;

public abstract class Function {

    private String[] data;
    private Map<String, Double> variables;

    public Function(String... data) {
        this.data = data;
        this.variables = new HashMap<>();
    }

    public Function variables(String... variables){
        for (String variable : variables)
            this.variables.put(variable, null);
        return this;
    }

    public Function setVariable(String variable, double value){
        if (!variables.containsKey(variable))
            throw new IllegalArgumentException("Variable '"+variable+"' isn't defined.");
        runSetVariable(variable, value);
        variables.put(variable, value);
        return this;
    }

    public Function compile(){
        runCompile();
        return this;
    }

    protected abstract void runCompile();

    protected abstract void runSetVariable(String variable, double value);

    public abstract double evaluate(); 
    public Map<String, Double> getVariables() {
        return variables;
    }

    public String[] getData() {
        return data;
    }
}
