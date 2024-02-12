package org.example;

import org.example.ResultValue.ArrayResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.UndefinedResultValue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculationContext {
    private final HashMap<String, ResultValue> variables;
    private final JSONArray subItems;
    boolean hasSubItems;

    public CalculationContext(
            HashMap<String, ResultValue> variables,
            boolean hasSubItems,
            JSONArray subItems
    ) {
        this.variables = variables;
        this.hasSubItems = hasSubItems;
        this.subItems = subItems;
    }

    public ResultValue get(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        }

        // If we don't have the variable, it resolves to undefined
        return new UndefinedResultValue();
    }

    public void put(String key, ResultValue value) {
        variables.put(key, value);
    }

    public ArrayResultValue pick(String key) {
        ArrayList result = new ArrayList();
        String unitsOfMeasure = "";

        // Here we search for the attributes each time
        // We can benefit from some sort of the items as an ArrayList of HashMaps, maybe
        for (int i = 0; i < subItems.length(); i++) {
            JSONObject item = subItems.getJSONObject(i);
            JSONArray attributes = item.getJSONArray("attributes");
            for (int j = 0; j < attributes.length(); j++) {
                JSONObject attribute = attributes.getJSONObject(j);
                String attributeName = attribute.getString("name");
                if (attributeName.equals(key)) {
                    if (unitsOfMeasure.equals("")) {
                        unitsOfMeasure = attribute.getString("uom");
                    }
                    result.add(attribute.getDouble("value"));
                }
            }
        }
        return new ArrayResultValue(result, unitsOfMeasure);
    }
}
