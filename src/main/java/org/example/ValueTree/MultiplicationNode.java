package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedValue;

import java.util.HashMap;

public class MultiplicationNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public MultiplicationNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public ResultValue getValue(CalculationContext context) {
        ResultValue leftValueNode = left.getValue(context);
        ResultValue rightValueNode = right.getValue(context);

        // If either of multipliers is undefined, result is undefined
        if (leftValueNode instanceof UndefinedValue || rightValueNode instanceof UndefinedValue) {
            return new UndefinedValue();
        }

        double leftValue = (double) leftValueNode.get();
        double rightValue = (double) rightValueNode.get();

        return new NumericResultValue(
            leftValue * rightValue,
            this.getResultingUnits(leftValueNode, rightValueNode));
    }

    private String getResultingUnits(ResultValue left, ResultValue right) {
        if (left.getUnits().isEmpty()) {
            return right.getUnits();
        } else if (right.getUnits().equals("plain")) {
            return left.getUnits();
        }

        return left.getUnits();
    }
}
