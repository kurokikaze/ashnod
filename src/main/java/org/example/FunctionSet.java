package org.example;

import org.example.ResultValue.ArrayResultValue;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedResultValue;
import org.example.ValueTree.ValueNode;
import org.example.ValueTree.VariableNode;

import java.util.ArrayList;

/*
    This is where you store the functions you can call from the script file.
    Each function takes an array of its arguments as well as the CalculationContext
 */
public class FunctionSet {
    public static ResultValue Sum(ArrayList<ValueNode> values, CalculationContext context) {
        ResultValue result = values.get(0).getValue(context);
        // If sum sees undefined as an incoming value, we know the item doesn't have sub items at all
        // It's not the same as seeing 0 here
        if (result instanceof UndefinedResultValue) {
            return result;
        }
        if (result instanceof ArrayResultValue) {
            double sum = 0;
            for(double value: ((ArrayResultValue<Double>) result).get()) {
                sum += value;
            }

            return new NumericResultValue(sum, result.getUnits());
        }
        // Default result is a zero of unspecified unit
        return new NumericResultValue(0, "");
    }

    // This is just a test implementation of the Avg()
    public static ResultValue Avg(ArrayList<ValueNode> values, CalculationContext context) {
        ResultValue result = values.get(0).getValue(context);
        // If sum sees undefined as an incoming value, we know the item doesn't have sub items at all
        // It's not the same as seeing 0 here
        if (result instanceof UndefinedResultValue) {
            return result;
        }
        if (result instanceof ArrayResultValue) {
            double sum = 0;
            ArrayList<Double> numberValues = ((ArrayResultValue<Double>) result).get();

            // Early return in case we have 0 items in the ArrayResult
            if (numberValues.size() == 0) {
                return new NumericResultValue(0, "");
            }

            for(double value: numberValues) {
                sum += value;
            }

            return new NumericResultValue(sum / numberValues.size(), result.getUnits());
        }
        // Default result is a zero of unspecified unit
        return new NumericResultValue(0, "");
    }

    public static ResultValue sub(ArrayList<ValueNode> values, CalculationContext context) {
        // Early return if we know for sure there are no subItems
        // We specifically send out undefined
        if (!context.hasSubItems) {
            return new UndefinedResultValue();
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
