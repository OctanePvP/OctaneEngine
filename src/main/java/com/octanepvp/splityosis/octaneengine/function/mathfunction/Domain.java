package com.octanepvp.splityosis.octaneengine.function.mathfunction;

import net.objecthunter.exp4j.Expression;

import java.util.List;

public class Domain {

    private List<Range> ranges;
    private Expression expression;

    public Domain(List<Range> ranges, Expression expression) {
        this.ranges = ranges;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public List<Range> getRanges() {
        return ranges;
    }
}
