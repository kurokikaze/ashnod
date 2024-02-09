package org.example.MatcherComparator;

import org.example.CalculationContext;

public class AnyComparator implements AbstractComparator {
    @Override
    public boolean compare(CalculationContext context) {
        return true;
    }
}
