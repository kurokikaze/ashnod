package org.example;

import org.junit.Test;

public class RecurseTest extends AbstractTest {
    @Test
    public void SubTest() throws Exception {
        // Huh, it works
        testScript("recurse-sub");
    }

    @Test
    public void SumTest() throws Exception {
        testScript("recurse-sum");
    }
}
