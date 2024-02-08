package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;

import java.util.HashMap;

public class VariableNode implements ValueNode {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public NumericResultValue getValue(HashMap<String, Integer> variables) {
        return new NumericResultValue(variables.get(variableName));
    }
}
