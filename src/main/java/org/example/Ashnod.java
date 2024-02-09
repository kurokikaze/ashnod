package org.example;

import org.example.AshnodSetup.AshnodSetup;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.StringResultValue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Ashnod {
    private final AshnodSetup setup;

    public Ashnod(AshnodSetup setup) {
        this.setup = setup;
    }

    public JSONObject calculate(JSONObject data) {
        // From the beginning

        JSONObject cart = data.getJSONObject("cart");
        JSONArray items = cart.getJSONArray("items");
        JSONArray resultItems = new JSONArray();
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemResult = this.processItem(items.getJSONObject(i));
            resultItems.put(itemResult);
        }

        JSONObject resultCart = new JSONObject(cart.toMap());
        resultCart.put("items", resultItems);

        JSONObject resultData = new JSONObject(data.toMap());
        resultData.put("cart", resultCart);

        return resultCart;
    }

    protected JSONObject processItem(JSONObject item) {
        JSONObject result = new JSONObject(item);

        boolean itemHasSubItems = item.has("items");
        // This array is created for CalculationContext
        // even if the item itself has no sub-items
        JSONArray items = new JSONArray();

        if (itemHasSubItems) {
            items = item.getJSONArray("items");
            JSONArray resultItems = new JSONArray();
            for (int i = 0; i < items.length(); i++) {
                JSONObject itemResult = this.processItem(items.getJSONObject(i));
                resultItems.put(itemResult);
            }
        }

        result.put("items", items);

        // Load the values
        HashMap<String, ResultValue> variables = this.loadAttributes(item);

        CalculationContext context = new CalculationContext(variables, itemHasSubItems, items);
        // Run the calculations
        if (!setup.ruleFile.rules.isEmpty()) {
            setup.ruleFile.rules.get(0).run(context);
        }

        // Convert the result
        item.put("attributes", saveAttributes(variables));
        return item;
    }

    private HashMap<String, ResultValue> loadAttributes(JSONObject item) {
        HashMap<String, ResultValue> variables = new HashMap<>();
        if (item.has("attributes")) {
            JSONArray attributes = item.getJSONArray("attributes");
            for (int i = 0; i < attributes.length(); i++) {
                JSONObject attribute = attributes.getJSONObject(i);
                String attributeType = attribute.getString("type");
                String attributeName = attribute.getString("name");
                String attributeUom = attribute.getString("uom");
                String attributeValue = attribute.getString("value");

                if (attributeType.equals("dec")) {
                    variables.put(
                            attributeName,
                            new NumericResultValue(Double.parseDouble(attributeValue), attributeUom)
                    );
                } else {
                    variables.put(
                            attributeName,
                            new StringResultValue(attributeValue, attributeUom)
                    );
                }
            }
        }
        return variables;
    }
    private JSONArray saveAttributes(HashMap<String, ResultValue> variables) {
        JSONArray attributes = new JSONArray();
        for (Map.Entry<String, ResultValue> entry : variables.entrySet()) {
            String attributeName = entry.getKey();
            ResultValue value = entry.getValue();
            String attributeUom = value.getUnits();
            String attributeType = value.getType();
            String attributeValue = value.get().toString();

            JSONObject attribute = new JSONObject();
            attribute.put("name", attributeName);
            attribute.put("type", attributeType);
            attribute.put("uom", attributeUom);
            attribute.put("value", attributeValue);

            attributes.put(attribute);
        }

        return attributes;
    }
}
