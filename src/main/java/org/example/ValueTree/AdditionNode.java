package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class AdditionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public AdditionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public NumericResultValue getValue(HashMap<String, ResultValue> variables) {
        ResultValue leftValueNode = left.getValue(variables);
        double leftValue = (double) leftValueNode.get();
        ResultValue rightValueNode = right.getValue(variables);
        double rightValue = (double) rightValueNode.get();

        if (!leftValueNode.getType().equals(rightValueNode.getType())) {
            System.out.println("Adding different units of measure: " + leftValueNode.getType() + " and " + rightValueNode.getType());
        };
        return new NumericResultValue(leftValue + rightValue, leftValueNode.getUnits());
    }
}
