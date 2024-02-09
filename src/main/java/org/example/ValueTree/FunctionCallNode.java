package org.example.ValueTree;

import org.example.FunctionSet;
import org.example.ResultValue.ResultValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FunctionCallNode implements ValueNode {
    private final String functionName;
    private final ValueNode args[];
    public FunctionCallNode(String functionName, ValueNode[] args) {
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public ResultValue getValue(HashMap<String, ResultValue> variables) {
        switch (this.functionName) {
            case "Sum": {
                return FunctionSet.Sum(new ArrayList<>(Arrays.asList(args)), variables);
            }
            case "sub": {
                return FunctionSet.sub(new ArrayList<>(Arrays.asList(args)), variables);
            }
        }
        return this.args[0].getValue(variables);
    }
}
