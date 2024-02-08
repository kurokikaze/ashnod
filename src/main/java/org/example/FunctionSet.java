package org.example;

import org.example.ValueTree.ValueNode;

import java.util.ArrayList;
import java.util.HashMap;

/*
    This is where you store the functions you can call from the script file
 */
public class FunctionSet {
    public static int Sum(ArrayList<ValueNode> values, HashMap<String, Integer> variables) {
        System.out.println("Function call Sum on " + values.size() + " argument(s)");

        return values.get(0).getValue(variables);
    }

    public static int sub(ArrayList<ValueNode> values, HashMap<String, Integer> variables) {
        System.out.println("Function call Sum on " + values.size() + " argument(s)");

        return 0;
    }

    /* It looks like sub is more of a modifier to the Sum call,
       than a separate function with its own output.
     */
    static ValueNode SumSub(ValueNode value) {
        return value;
    }
}
