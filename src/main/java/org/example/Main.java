package org.example;

import com.carstenGrammar.CarstenLexer;
import com.carstenGrammar.CarstenParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.AshnodSetup.AshnodSetup;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.json.JSONArray;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        String scriptContent = "";
        try {
            scriptContent = new String(Files.readAllBytes(Paths.get("src/script.crs")));
        } catch(Exception e) {}

        CarstenLexer crsLexer = new CarstenLexer(CharStreams.fromString(scriptContent));
        CommonTokenStream tokens = new CommonTokenStream(crsLexer);
        CarstenParser parser = new CarstenParser(tokens);

        ValueTreeVisitor visitor = new ValueTreeVisitor();
        AshnodSetup setup = visitor.visitMatcherFile(parser.matcherFile());

        // Dump encountered top-level int variables into the map
        HashMap<String, ResultValue> variables = new HashMap<>();

        for(Map.Entry<String, Integer> set: setup.initialVariables.intVariables.entrySet()) {
            variables.put(set.getKey(), new NumericResultValue(set.getValue(), ""));
        }

        variables.put("singlePrice", new NumericResultValue(14, "eur"));
        variables.put("volume", new NumericResultValue(3, "m3"));
        variables.put("posSum", new NumericResultValue(2, "eur"));

        CalculationContext context = new CalculationContext(variables, false, new JSONArray());

        if (!setup.ruleFile.rules.isEmpty()) {
            setup.ruleFile.rules.get(0).run(context);

            System.out.println(variables);
        }
    }
}