package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;
import java.util.List;

public class RuleBlock {
    private final List<AssignmentNode> actions;

    private final String rule;

    public RuleBlock(String rule, List<AssignmentNode> actions) {
        this.rule = rule;
        this.actions = actions;
    }

    public CalculationContext run(CalculationContext context) {
        for(AssignmentNode action: this.actions) {
            action.run(context);
        }

        return context;
    }
}
