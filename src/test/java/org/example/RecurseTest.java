package org.example;

import org.junit.Test;

public class RecurseTest extends AbstractTest {
    @Test
    public void SubTest() throws Exception {
        // Lol, works
        testScript("recurse-sub");
    }

    @Test
    public void SumTest() throws Exception {
        // Lol, works
        testScript("recurse-sum");
    }
}
