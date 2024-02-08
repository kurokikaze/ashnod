package org.example.ValueTree;

import java.util.HashMap;

public class VariableNode implements ValueNode {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public int getValue(HashMap<String, Integer> variables) {
        return variables.get(variableName);
    }
}
