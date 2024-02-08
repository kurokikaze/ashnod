package org.example.ValueTree;

import java.util.HashMap;
import java.util.List;

public class RuleBlock {
    private final List<AssignmentNode> actions;

    private final String rule;

    public RuleBlock(String rule, List<AssignmentNode> actions) {
        this.rule = rule;
        this.actions = actions;
    }

    public HashMap<String, Integer> run(HashMap<String, Integer> variables) {
        for(AssignmentNode action: this.actions) {
            action.run(variables);
        }

        return variables;
    }
}
