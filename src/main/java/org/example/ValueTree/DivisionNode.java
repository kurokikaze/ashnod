package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class DivisionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public DivisionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public NumericResultValue getValue(CalculationContext context) {
        ResultValue leftValueNode = left.getValue(context);
        double leftValue = (double) leftValueNode.get();
        ResultValue rightValueNode = right.getValue(context);
        double rightValue = (double) rightValueNode.get();

        return new NumericResultValue(
            leftValue / rightValue,
            this.getResultingUnits(leftValueNode, rightValueNode)
        );
    }

    private String getResultingUnits(ResultValue left, ResultValue right) {
        // Empty uom is a sign of a plain numeric value
        // If one of units is plain, we always use the other one
        // (like, "eur" divided by two is still "eur")
        if (left.getUnits().isEmpty()) {
            // On the other hand, shouldn't dividing number by a unit
            // give us 1/unit or something?
            return right.getUnits();
        } else if (right.getUnits().isEmpty()) {
            return left.getUnits();
        }

        // This will get stupid if done more than once, but will do for a POC
        return left.getUnits() + "/" + right.getUnits();
    }
}
