package org.example;

import org.example.ResultValue.ArrayResultValue;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ValueTree.ValueNode;

import java.util.ArrayList;
import java.util.HashMap;

/*
    This is where you store the functions you can call from the script file
 */
public class FunctionSet {
    public static NumericResultValue Sum(ArrayList<ValueNode> values, HashMap<String, Integer> variables) {
        System.out.println("Function call Sum on " + values.size() + " argument(s)");

        ResultValue result = values.get(0).getValue(variables);
        if (result instanceof ArrayResultValue) {
            int sum = 0;
            for(int value: ((ArrayResultValue<Integer>) result).get()) {
                sum += value;
            }

            return new NumericResultValue(sum);
        }
        return new NumericResultValue(0);
    }

    public static ArrayResultValue<Integer> sub(ArrayList<ValueNode> values, HashMap<String, Integer> variables) {
        System.out.println("Function call Sum on " + values.size() + " argument(s)");

        return new ArrayResultValue<>(new ArrayList<Integer>());
    }
}
