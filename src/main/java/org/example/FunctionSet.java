package org.example;

import org.example.ValueTree.ValueNode;

public class FunctionSet {
    static ValueNode Sum(ValueNode value) {
        return value;
    }

    static ValueNode sub(ValueNode value) {
        return value;
    }

    /* It looks like sub is more of a modifier to the Sum call,
       than a separate function with its own output.
     */
    static ValueNode SumSub(ValueNode value) {
        return value;
    }
}
