package org.example.ValueTree;

import java.util.HashMap;

public class FunctionCallNode implements ValueNode {
    private final String functionName;
    private final ValueNode args[];
    public FunctionCallNode(String functionName, ValueNode[] args) {
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public int getValue(HashMap<String, Integer> variables) {
        int value = this.args[0].getValue(variables);
        System.out.println("Function call " + functionName + " on " + value);
        return this.args[0].getValue(variables);
    }
}
