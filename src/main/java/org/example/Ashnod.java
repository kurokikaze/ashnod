package org.example;

import org.example.AshnodSetup.AshnodSetup;
import org.example.ResultValue.NumericResultValue;
import org.example.ResultValue.ResultValue;
import org.example.ResultValue.StringResultValue;
import org.example.ResultValue.UndefinedValue;
import org.example.ValueTree.RuleBlock;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
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

        // Process items in the cart one-by-one
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

        // This goes recursively over sub-items, processing them one-by-one
        // It's important to do the sub-items before the item itself, so `sub()` will get the correct values
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
        for (RuleBlock rule : setup.ruleFile.rules) {
            // Check if the item fits the matcher rule
            if (rule.check(context)) {
                // If we want to exclude the sub-items when the parent items fails the matcher cherk,
                // just move the upper subItems processing block here

                // Otherwise, just run the rule on the item
                rule.run(context);

                // Convert the result
                item.put("attributes", saveAttributes(variables));
            }
        }

        return item;
    }

    private HashMap<String, ResultValue> loadAttributes(JSONObject item) {
        HashMap<String, ResultValue> variables = new HashMap<>();
        if (item.has("attributes")) {
            JSONArray attributes = item.getJSONArray("attributes");
            for (int i = 0; i < attributes.length(); i++) {
                JSONObject attribute = attributes.getJSONObject(i);
                String attributeName = attribute.getString("name");
                variables.put(
                    attributeName,
                    this.convertAttribute(attribute)
                );

            }
        }
        return variables;
    }

    /*
        Here we convert the JSON object into the ResultValue
        Right now it's missing the ArrayResultValue I guess
     */
    private ResultValue convertAttribute(JSONObject attribute) {
        String attributeType = attribute.getString("type");
        String attributeUom = attribute.getString("uom");
        String attributeValue = attribute.getString("value");

        if (attributeType.equals("dec")) {
            return new NumericResultValue(Double.parseDouble(attributeValue), attributeUom);
        } else {
            return new StringResultValue(attributeValue, attributeUom);
        }
    }

    private JSONArray saveAttributes(HashMap<String, ResultValue> variables) {
        JSONArray attributes = new JSONArray();
        for (Map.Entry<String, ResultValue> entry : variables.entrySet()) {
            String attributeName = entry.getKey();
            ResultValue value = entry.getValue();

            JSONObject attribute = new JSONObject();
            // We skip all variables that resolve to UndefinedValue
            // For example, this skips all Sum results on leaf items
            if (!(value instanceof UndefinedValue)) {
                String attributeUom = value.getUnits();
                String attributeType = value.getType();
                String attributeValue = value.get().toString();

                attribute.put("name", attributeName);
                attribute.put("type", attributeType);
                attribute.put("uom", attributeUom);
                attribute.put("value", attributeValue);

                attributes.put(attribute);
            }
        }

        return attributes;
    }
}
