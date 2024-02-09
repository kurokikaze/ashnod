package org.example;

import org.example.AshnodSetup.AshnodSetup;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class MathTest extends AbstractTest {
    @Test
    public void SimpleMath() throws Exception {
        testScript("math-mul");
    }

    @Test
    public void SimpleMathAddition() throws Exception {
        testScript("math-add");
    }

    @Test
    public void SimpleMathSubtraction() throws Exception {
        testScript("math-sub");
    }

    @Test
    public void SimpleMathDivision() throws Exception {
        testScript("math-div");
    }

    @Test
    public void MathFormula() throws Exception {
        // Lol, works
        testScript("math-formula");
    }
}
