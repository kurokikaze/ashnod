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
        globals.set("foo", LuaValue.valueOf("bar"));

        // Use the convenience function on the globals to load a chunk.
        LuaValue env = globals.load(script, "maven-example");
        Prototype proto = env.checkclosure().p;

        env.set("foo", LuaValue.valueOf("bar"));
        Iterator<String> metaKeys = meta.keys();
        while(metaKeys.hasNext()) {
            String key = metaKeys.next();
            Object value = meta.get(key);
//            env.set(key, LuaValue.valueOf(value));
//            System.out.println(value);
        }

        // Use any of the "call()" or "invoke()" functions directly on the chunk.
        env.call();

        LuaValue[] globalValues = proto.k;

        for (LuaValue val: globalValues) {
            String identifier = val.tojstring();
            LuaValue value = globals.get(identifier);
            if (!value.isnil() && !value.istable()) {
                LuaValue nakedValue = globals.get(identifier);
                String type = LuaValue.TYPE_NAMES[nakedValue.type()];
                System.out.println(identifier + ' ' + type);
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
//                System.out.println(identifier);
//                System.out.println(globals.get(identifier));
//                System.out.println(LuaValue.TYPE_NAMES[globals.get(identifier).type()]);
//                item.put(identifier, globals.get(identifier));
            }
        }

        return item;
    }
}
