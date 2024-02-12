package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.FunctionSet;
import org.example.ResultValue.ResultValue;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionCallNode implements ValueNode {
    private final String functionName;
    private final ValueNode args[];
    public FunctionCallNode(String functionName, ValueNode[] args) {
        this.functionName = functionName;
        this.args = args;
    }

    @Override
    public ResultValue getValue(CalculationContext context) {
        switch (this.functionName) {
            case "Sum": {
                // If sum sees undefined as an incoming value, we know the item doesn't have sub items at all
                // It's not the same as seeing 0 here
                return FunctionSet.Sum(new ArrayList<>(Arrays.asList(args)), context);
            }
            case "sub": {
                return FunctionSet.sub(new ArrayList<>(Arrays.asList(args)), context);
            }
        }
        return this.args[0].getValue(context);
    }
}
