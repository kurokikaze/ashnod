package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedValue;

import java.util.HashMap;

public class SubtractionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public SubtractionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public ResultValue getValue(CalculationContext context) {
        ResultValue leftValueNode = left.getValue(context);
        ResultValue rightValueNode = right.getValue(context);

        // If either of components is undefined, result is undefined
        if (leftValueNode instanceof UndefinedValue || rightValueNode instanceof UndefinedValue) {
            return new UndefinedValue();
        }

        double leftValue = (double) leftValueNode.get();
        double rightValue = (double) rightValueNode.get();

        if (!leftValueNode.getUnits().equals(rightValueNode.getUnits())) {
            System.out.println("Subtracting different units of measure: " + leftValueNode.getUnits() + " and " + rightValueNode.getUnits());
        }
        return new NumericResultValue(leftValue - rightValue, leftValueNode.getUnits());
    }
}
