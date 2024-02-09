package org.example;

import org.junit.Test;
public class MatcherTest extends AbstractTest {

    @Test
    public void MatcherDefaultTest() throws Exception {
        testScript("matcher-default");
    }

    @Test
    public void MatcherCompareTest() throws Exception {
        testScript("matcher-compare");
    }

    @Test
    public void MatcherFieldCheckTest() throws Exception {
        testScript("matcher-field-check");
    }
}
