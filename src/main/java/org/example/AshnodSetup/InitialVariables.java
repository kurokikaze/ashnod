package org.example.AshnodSetup;

import java.util.HashMap;

public class InitialVariables {
    public HashMap<String, Integer> intVariables;
    public HashMap<String, String> stringVariables;

    public void addVariable(String varName, String value) {
        this.stringVariables.put(varName, value);
    }

    public void addVariable(String varName, Integer value) {
        this.intVariables.put(varName, value);
    }

    public InitialVariables() {
        this.intVariables = new HashMap<String, Integer>();
    }
}
