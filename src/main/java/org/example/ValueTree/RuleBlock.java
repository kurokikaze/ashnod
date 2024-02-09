package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.MatcherComparator.AbstractComparator;
import java.util.List;

public class RuleBlock {
    private final List<AssignmentNode> actions;

    private final AbstractComparator rule;

    public RuleBlock(AbstractComparator rule, List<AssignmentNode> actions) {
        this.rule = rule;
        this.actions = actions;
    }

    public CalculationContext run(CalculationContext context) {
        for(AssignmentNode action: this.actions) {
            action.run(context);
        }

        return context;
    }

    public boolean check(CalculationContext context) {
        return rule.compare(context);
    }
}
