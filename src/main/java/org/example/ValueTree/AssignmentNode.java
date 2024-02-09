package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class AssignmentNode {
    private final String variableName;
    private final ValueNode value;

    public AssignmentNode(String variableName, ValueNode value) {
        this.variableName = variableName;
        this.value = value;
    }

    public void run(CalculationContext context) {
        ResultValue result = this.value.getValue(context);
        context.put(variableName, result);
    }
}
