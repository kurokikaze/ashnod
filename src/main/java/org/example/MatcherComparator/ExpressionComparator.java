package org.example.MatcherComparator;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedResultValue;
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
        ResultValue leftValue = left.getValue(context);
        ResultValue rightValue = right.getValue(context);

        // If any of the operands resolve to Undefined, comparison fails
        // Except maybe something != UndefinedValue should return true?
        if (leftValue instanceof UndefinedResultValue || rightValue instanceof UndefinedResultValue) {
            return false;
        }

        switch (operator) {
            case "=": {
                Object left = leftValue.get();
                Object right = rightValue.get();

                // Here goes comparison casting
                // If any one is a string, cast both to strings
                if (left instanceof String || right instanceof String) {
                    return left.toString().equals(right.toString());
                }
                // There can be additional options of comparing arrays for example
                return (left == right);
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
