package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class MultiplicationNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public MultiplicationNode(ValueNode left, ValueNode right) {
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
