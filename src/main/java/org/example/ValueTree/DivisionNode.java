package org.example.ValueTree;

import java.util.HashMap;

public class DivisionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public DivisionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int getValue(HashMap<String, Integer> variables) {
        return left.getValue(variables) / right.getValue(variables);
    }
}
