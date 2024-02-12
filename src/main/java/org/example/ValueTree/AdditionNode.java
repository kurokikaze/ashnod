package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedResultValue;

public class AdditionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public AdditionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public ResultValue getValue(CalculationContext context) {
        ResultValue leftValueNode = left.getValue(context);
        ResultValue rightValueNode = right.getValue(context);

        // If either of components is undefined, result is undefined
        if (leftValueNode instanceof UndefinedResultValue || rightValueNode instanceof UndefinedResultValue) {
            return new UndefinedResultValue();
        }

        double leftValue = (double) leftValueNode.get();
        double rightValue = (double) rightValueNode.get();

        if (!leftValueNode.getType().equals(rightValueNode.getType())) {
            System.out.println("Adding different units of measure: " + leftValueNode.getType() + " and " + rightValueNode.getType());
        };
        return new NumericResultValue(leftValue + rightValue, leftValueNode.getUnits());
    }
}
