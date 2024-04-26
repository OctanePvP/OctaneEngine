package com.octanepvp.splityosis.octaneengine.function.mathfunction;

import com.octanepvp.splityosis.octaneengine.function.Function;
import com.octanepvp.splityosis.octaneengine.function.exceptions.InvalidRangeFormatException;
import com.octanepvp.splityosis.octaneengine.function.exceptions.UnknownRangeVariableException;
import com.octanepvp.splityosis.octaneengine.function.exceptions.VariableNotSetException;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range {

    private String rawForm;
    private String variable;
    private Expression expression;
    private String operator;
    private Function function;
    private ConditionalOperator conditionalOperator;

    public Range(String rawForm, Function function) {
        rawForm = rawForm.trim();
        this.rawForm = rawForm;
        this.function = function;
        String[] split = processRawForm();
        variable = split[0];
        operator = split[1];
        expression = new ExpressionBuilder(split[2]).variables(function.getVariables().keySet()).build();
        if (!function.getVariables().containsKey(variable))
            throw new UnknownRangeVariableException("Unknown range variable '"+variable+"' at '"+rawForm+"'");

        if (operator.equals("<"))
            conditionalOperator = ConditionalOperator.SMALLER;
        else if (operator.equals("<="))
            conditionalOperator = ConditionalOperator.SMALLER_EQUALS;
        else if (operator.equals(">"))
            conditionalOperator = ConditionalOperator.BIGGER;
        else if (operator.equals(">="))
            conditionalOperator = ConditionalOperator.BIGGER_EQUALS;
        else if (operator.equals("=="))
            conditionalOperator = ConditionalOperator.EQUALS;
    }

    public boolean check(){
        Double variableValue = function.getVariables().get(variable);
        if (variableValue == null)
            throw new VariableNotSetException("Variable '"+variable+"' isn't assigned a value");
        return conditionalOperator.check(variableValue, expression.evaluate());
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getOperator() {
        return operator;
    }

    private static final Pattern pattern = Pattern.compile("([<>]=?|==)");
    private String[] processRawForm(){
        String[] split = new String[3];
        Matcher matcher = pattern.matcher(rawForm);

        int matchCount = 0;
        while (matcher.find()) {
            matchCount++;
            if (matchCount > 1) {
                throw new InvalidRangeFormatException("More than one operator at '"+rawForm+"', format should follow 'variable ><= expression'");
            }
            split[0] = rawForm.substring(0, matcher.start()).trim();
            split[1] = matcher.group(1);
            split[2] = rawForm.substring(matcher.end()).trim();
        }

        if (matchCount == 0)
            throw new InvalidRangeFormatException("Missing operator at '"+rawForm+"', format should follow 'variable ><= expression'");
        return split;
    }
}
