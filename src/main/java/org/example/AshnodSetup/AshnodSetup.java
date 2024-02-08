package org.example.AshnodSetup;

import org.example.ValueTree.RuleFile;

public class AshnodSetup {
    public final InitialVariables initialVariables;
    public final RuleFile ruleFile;

    public AshnodSetup(RuleFile ruleFile, InitialVariables initialVariables) {
        this.ruleFile = ruleFile;
        this.initialVariables = initialVariables;
    }
}
