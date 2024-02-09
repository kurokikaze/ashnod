package org.example.ValueTree;

import org.example.CalculationContext;
import org.example.ResultValue.ResultValue;

import java.util.HashMap;

public interface ValueNode {
    public ResultValue getValue(CalculationContext context);
}
