package org.example;

import com.carstenGrammar.CarstenLexer;
import com.carstenGrammar.CarstenParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.example.AshnodSetup.AshnodSetup;
import org.json.JSONObject;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;

abstract class AbstractTest {
    public static AshnodSetup createSetup(String name) throws Exception {
        String scriptContent = "";
        try {
            scriptContent = new String(Files.readAllBytes(Paths.get("src/test/java/org/example/TestData/" + name + ".crs")));
        } catch (Exception e){
            throw new Exception("Unknown test data: " + name);
        }

        CarstenLexer crsLexer = new CarstenLexer(CharStreams.fromString(scriptContent));
        CommonTokenStream tokens = new CommonTokenStream(crsLexer);
        CarstenParser parser = new CarstenParser(tokens);

        ValueTreeVisitor visitor = new ValueTreeVisitor();
        return visitor.visitMatcherFile(parser.matcherFile());

    }

    public static JSONObject loadInput(String name) throws Exception {
        String input;
        try {
            input = new String(Files.readAllBytes(Paths.get("src/test/java/org/example/TestData/" + name + ".input.json")));
        } catch (Exception e) {
            throw new Exception("Unknown test data file: " + name);
        }

        JSONObject result;
        try {
            result = new JSONObject(input);
        } catch (Exception e) {
            throw new Exception("Malformed json in input data " + name);
        }

        return result;
    }
    public static String loadResult(String name) throws Exception {
        String input;
        try {
            input = new String(Files.readAllBytes(Paths.get("src/test/java/org/example/TestData/" + name + ".result.json")));
        } catch (Exception e) {
            throw new Exception("Unknown result file: " + name);
        }

        return input;
    }

    public void testScript(String name) throws Exception {
        AshnodSetup setup = AbstractTest.createSetup(name);
        Ashnod engine = new Ashnod(setup);

        JSONObject data = AbstractTest.loadInput(name);

        JSONObject result = engine.calculate(data);

        assert(!result.toString().isEmpty());
        Assert.assertEquals(
                result.toString(2),
                AbstractTest.loadResult(name)
        );
    }
}