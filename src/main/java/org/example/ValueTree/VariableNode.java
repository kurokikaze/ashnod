package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public class VariableNode implements ValueNode {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {return this.variableName; }
    public ResultValue getValue(CalculationContext context) {
        return context.get(variableName);
    }
}
