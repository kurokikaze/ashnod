package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;

import java.util.HashMap;

public class SubtractionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public SubtractionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public NumericResultValue getValue(HashMap<String, Integer> variables) {
        int leftValue = (int) left.getValue(variables).get();
        int rightValue = (int) right.getValue(variables).get();
        System.out.println(leftValue + " - " + rightValue);
        return new NumericResultValue(leftValue - rightValue);
    }
}
