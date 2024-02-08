package org.example.ValueTree;

import java.util.HashMap;

public class NumberNode implements ValueNode {
    private final Integer value;

    public NumberNode(Integer value) {
        this.value = value;
    }

    @Override
    public int getValue(HashMap<String, Integer> variables) {
        return this.value;
    }
}
