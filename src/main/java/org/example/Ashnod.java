package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Iterator;

public class Ashnod {
    public JSONObject calculate(String script, JSONObject data) {
        // From the beginning

        JSONObject meta = data.getJSONObject("meta");
        JSONArray items = data.getJSONArray("data");
        JSONArray resultItems = new JSONArray();
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemResult = this.processItem(script, meta, items.getJSONObject(i));
            resultItems.put(itemResult);
        }

        JSONObject result = new JSONObject();
        result.put("meta", meta);
        result.put("data", resultItems);

        return result;
    }

    protected JSONObject processItem(String script, JSONObject meta, JSONObject item) {
        Globals globals = JsePlatform.standardGlobals();
        globals.set("foo", LuaValue.valueOf(12));

        addFieldsToGlobal(globals, meta);
        addFieldsToGlobal(globals, item);

        // Use the convenience function on the globals to load a chunk.
        LuaValue env = globals.load(script, "maven-example");
        Prototype proto = env.checkclosure().p;

        // Use any of the "call()" or "invoke()" functions directly on the chunk.
        env.call();

        LuaValue[] globalValues = proto.k;

        for (LuaValue val: globalValues) {
            String identifier = val.tojstring();
            LuaValue value = globals.get(identifier);
            if (!value.isnil() && !value.istable()) {
                LuaValue nakedValue = globals.get(identifier);
                String type = LuaValue.TYPE_NAMES[nakedValue.type()];
                // System.out.println(identifier + ' ' + type);
                switch (type) {
                    case "number": {
                        item.put(identifier, nakedValue.optdouble(0));
                        break;
                    }
                    case "string": {
                        item.put(identifier, nakedValue);
                        break;
                    }
                    case "boolean": {
                        item.put(identifier, nakedValue.toboolean());
                    }
                }
            }
        }

        return item;
    }

    protected void addFieldsToGlobal(LuaValue globals, JSONObject obj) {
        Iterator<String> metaKeys = obj.keys();
        while(metaKeys.hasNext()) {
            String key = metaKeys.next();
            Object value = obj.get(key);

            if (value instanceof Integer) {
                globals.set(key, LuaValue.valueOf((Integer) value));
            } else if (value instanceof String) {
                globals.set(key, LuaValue.valueOf(value.toString()));
            }
        }
    }
}
