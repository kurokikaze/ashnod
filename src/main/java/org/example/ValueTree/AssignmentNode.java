package org.example.ValueTree;

import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class AssignmentNode {
    private String variableName;
    private ValueNode value;

    public AssignmentNode(String variableName, ValueNode value) {
        this.variableName = variableName;
        this.value = value;
    }

    public void run(HashMap<String, Integer> variables) {
        ResultValue result = this.value.getValue(variables);
        if (result instanceof NumericResultValue) {
            variables.put(variableName, ((NumericResultValue) result).get());
        }
    }
}
