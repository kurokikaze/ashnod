package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class VariableNode implements ValueNode {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    public ResultValue getValue(HashMap<String, ResultValue> variables) {
        return variables.get(variableName);
    }
}
