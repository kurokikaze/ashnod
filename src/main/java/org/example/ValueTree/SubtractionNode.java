package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class SubtractionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public SubtractionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public NumericResultValue getValue(HashMap<String, ResultValue> variables) {
        ResultValue leftValueNode = left.getValue(variables);
        double leftValue = (double) leftValueNode.get();
        ResultValue rightValueNode = right.getValue(variables);
        double rightValue = (double) rightValueNode.get();
        if (!leftValueNode.getUnits().equals(rightValueNode.getUnits())) {
            System.out.println("Subtracting different units of measure: " + leftValueNode.getUnits() + " and " + rightValueNode.getUnits());
        }
        return new NumericResultValue(leftValue - rightValue, leftValueNode.getUnits());
    }
}
