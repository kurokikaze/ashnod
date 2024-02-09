package org.example.MatcherComparator;

import org.example.CalculationContext;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedValue;
import org.example.ValueTree.ValueNode;

public class SimpleExpressionComparator implements AbstractComparator {
    private final ValueNode expr;

    public SimpleExpressionComparator(ValueNode expr) {
        this.expr = expr;
    }

    public boolean compare(CalculationContext context) {
        ResultValue result = expr.getValue(context);

        return (!(result instanceof UndefinedValue));
    }
}
