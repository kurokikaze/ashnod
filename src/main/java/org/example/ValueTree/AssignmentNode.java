package org.example.ValueTree;

import org.luaj.vm2.ast.Str;

import java.util.HashMap;

public class AssignmentNode {
    private String variableName;
    private ValueNode value;

    public AssignmentNode(String variableName, ValueNode value) {
        this.variableName = variableName;
        this.value = value;
    }

    public void run(HashMap<String, Integer> variables) {
        Integer result = this.value.getValue(variables);
        variables.put(variableName, result);
    }
}
