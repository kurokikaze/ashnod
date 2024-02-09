package org.example.MatcherComparator;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedValue;
import org.example.ValueTree.NumberNode;
import org.example.ValueTree.ValueNode;

public class ExpressionComparator implements AbstractComparator {
    private final ValueNode left;
    private final ValueNode right;

    private final String operator;

    public ExpressionComparator(ValueNode left, ValueNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public boolean compare(CalculationContext context) {
        System.out.println("Expression compare");
        ResultValue leftValue = left.getValue(context);
        ResultValue rightValue = right.getValue(context);

        // If any of the operands resolve to Undefined, comparison fails
        // Except maybe something != UndefinedValue should return true?
        if (leftValue instanceof UndefinedValue || rightValue instanceof UndefinedValue) {
            System.out.println("Undefined drop");
            return false;
        }

        switch (operator) {
            case "=": {
                return (leftValue.get() == rightValue.get());
            }
            case ">": {
                if (leftValue instanceof NumericResultValue && rightValue instanceof NumericResultValue) {
                    return (((NumericResultValue) leftValue).get() > ((NumericResultValue) rightValue).get());
                }
                return false;
            }
            case "<": {
                if (leftValue instanceof NumericResultValue && rightValue instanceof NumericResultValue) {
                    return (((NumericResultValue) leftValue).get() < ((NumericResultValue) rightValue).get());
                }
                return false;
            }
            case ">=": {
                if (leftValue instanceof NumericResultValue && rightValue instanceof NumericResultValue) {
                    return (((NumericResultValue) leftValue).get() >= ((NumericResultValue) rightValue).get());
                }
                return false;
            }
            case "<=": {
                if (leftValue instanceof NumericResultValue && rightValue instanceof NumericResultValue) {
                    return (((NumericResultValue) leftValue).get() <= ((NumericResultValue) rightValue).get());
                }
                return false;
            }
            case "!=": {
                return (leftValue.get() != rightValue.get());
            }
        }

        return false;
    }
}
