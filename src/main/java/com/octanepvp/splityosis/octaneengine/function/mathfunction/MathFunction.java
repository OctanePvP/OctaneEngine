package com.octanepvp.splityosis.octaneengine.function.mathfunction;

import com.octanepvp.splityosis.octaneengine.function.Function;
import com.octanepvp.splityosis.octaneengine.function.exceptions.VariableNotInDomainException;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MathFunction extends Function {

    private List<Domain> domains;

    public MathFunction(String... data) {
        super(data);
    }

    @Override
    public void runCompile() {
        domains = new ArrayList<>();
        for (String line : getData()) {
            String[] split = line.split(Pattern.quote(":"));
            if (split.length == 1){
                domains.add(new Domain(new ArrayList<>(),
                        new ExpressionBuilder(line)
                        .variables(getVariables().keySet())
                                .build()));
                continue;
            }
            if (split.length == 2){
                List<Range> ranges = new ArrayList<>();
                String[] rangeSplit = split[0].split(Pattern.quote("&&"));
                for (String s : rangeSplit)
                    ranges.add(new Range(s, this));
                domains.add(new Domain(ranges, new ExpressionBuilder(split[1])
                        .variables(getVariables().keySet())
                        .build()));
            }
        }
    }

    @Override
    public void runSetVariable(String variable, double value) {
        for (Domain domain : domains) {
            domain.getExpression().setVariable(variable, value);
            for (Range range : domain.getRanges())
                range.getExpression().setVariable(variable, value);
        }
    }

    @Override
    public double evaluate() {
        domains:
        for (Domain domain : domains) {
            for (Range range : domain.getRanges())
                if (!range.check()) continue domains;
            return domain.getExpression().evaluate();
        }
        throw new VariableNotInDomainException("Function undefined for "+variableMap(getVariables()));
    }

    private static String variableMap(Map<String, Double> variables){
        StringBuilder str = new StringBuilder("(");
        for (Map.Entry<String, Double> stringDoubleEntry : variables.entrySet())
            str.append(stringDoubleEntry.getKey()).append(" = ").append(stringDoubleEntry.getValue()).append(",");
        return str.substring(0, str.length()-1) + ")";
    }
}
