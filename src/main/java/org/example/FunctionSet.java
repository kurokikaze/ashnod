package org.example;

import org.example.ResultValue.ArrayResultValue;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedValue;
import org.example.ValueTree.ValueNode;
import org.example.ValueTree.VariableNode;

import java.util.ArrayList;
import java.util.HashMap;

/*
    This is where you store the functions you can call from the script file
 */
public class FunctionSet {
    public static ResultValue Sum(ArrayList<ValueNode> values, CalculationContext context) {
        ResultValue result = values.get(0).getValue(context);
        // If sum sees undefined as an incoming value, we know the item doesn't have sub items at all
        // It's not the same as seeing 0 here
        if (result instanceof UndefinedValue) {
            return result;
        }
        if (result instanceof ArrayResultValue) {
            int sum = 0;
            for(double value: ((ArrayResultValue<Double>) result).get()) {
                sum += value;
            }

            return new NumericResultValue(sum, result.getUnits());
        }
        // Default result is a zero of unspecified unit
        return new NumericResultValue(0, "");
    }

    public static ResultValue sub(ArrayList<ValueNode> values, CalculationContext context) {
        // Early return if we know for sure there are no subItems
        // We specifically send out undefined
        if (!context.hasSubItems) {
            return new UndefinedValue();
        }
        ValueNode arg = values.get(0);
        // Here we're moving dangerously close to a sort of reflection
        // Usually the VariableNode should resolve into its value
        // Here we specifically get its variable name and use it as a string
        if (arg instanceof VariableNode) {
            String fieldName = ((VariableNode) arg).getVariableName();
            return context.pick(fieldName);
        }

        // Default value is an empty array of unspecified units
        return new ArrayResultValue<>(new ArrayList<>(), "");
    }
}
