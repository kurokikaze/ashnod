package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import org.json.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        String scriptContent = "";
        try {
            scriptContent = new String(Files.readAllBytes(Paths.get("src/script.lua")));
        } catch(Exception e) {}

        Properties props = System.getProperties();
        props.setProperty("org.luaj.luajc", "true");

        Ashnod calculator = new Ashnod();
        System.out.println("Calculation result:");

        JSONObject result = calculator.calculate(scriptContent, readData("src/data/in.json"));
        System.out.println(result.toString(4));
    }

    protected static JSONObject readData(String filename) {
        String jsonContent = "";
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(filename)));
        } catch(Exception e) {}

        return new JSONObject(jsonContent);
    }
}