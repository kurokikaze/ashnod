package org.example.ValueTree;

import java.util.HashMap;

public class AdditionNode implements ValueNode {
    private final ValueNode left;
    private final ValueNode right;
    public AdditionNode(ValueNode left, ValueNode right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public int getValue(HashMap<String, Integer> variables) {
        System.out.println(left.getValue(variables) + " + " + right.getValue(variables));
        return left.getValue(variables) + right.getValue(variables);
    }
}
