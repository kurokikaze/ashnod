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
                String type = LuaValue.TYPE_NAMES[value.type()];
                System.out.println(identifier + ' ' + type);
                switch (type) {
                    case "number": {
                        item.put(identifier, value.optdouble(0));
                        break;
                    }
                    case "string": {
                        item.put(identifier, value);
                        break;
                    }
                    case "boolean": {
                        item.put(identifier, value.toboolean());
                    }
                }
            } else if (value.istable()) {
                // Skip Lua's internal String object
                if (!identifier.equals(LuaValue.TYPE_NAMES[LuaValue.TSTRING])) {
                    JSONObject subItem = new JSONObject();
                    this.transformTable((LuaTable) value, subItem);
                    item.put(identifier, subItem);
                }
            }
        }

        return item;
    }

    protected void transformTable(LuaTable table, JSONObject item) {
        for (LuaValue val: table.keys()) {
            String identifier = val.tojstring();
            LuaValue value = table.get(identifier);
            if (!value.isnil() && !value.istable()) {
                String type = LuaValue.TYPE_NAMES[value.type()];
                System.out.println(identifier + ' ' + type);
                switch (value.type()) {
                    case LuaValue.TNUMBER: {
                        item.put(identifier, value.optdouble(0));
                        break;
                    }
                    case LuaValue.TSTRING: {
                        item.put(identifier, value);
                        break;
                    }
                    case LuaValue.TBOOLEAN: {
                        item.put(identifier, value.toboolean());
                    }
                }
            } else if (value.istable()) {
                // Skip Lua's internal object
                if (!identifier.equals("string")) {
                    JSONObject subItem = new JSONObject();
                    // We know it's a table
                    this.transformTable((LuaTable) value, subItem);
                    item.put(identifier, subItem);
                }
            }
        }
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
            } else if (value instanceof JSONArray) {
                System.out.println("Array encountered at identifier " + key);
            } else if (value == JSONObject.NULL) {
                globals.set(key, LuaValue.NIL);
            } else {
                System.out.println("Object inside");
                System.out.print(value);
                // That's where the objects live
                // Extract the sub-object as a JSONObject
                JSONObject subObject = obj.getJSONObject(key);
                LuaTable resultingTable = this.convertObjectToTable(subObject);

                LuaValue k = LuaValue.NIL;
                while ( true ) {
                    Varargs n = resultingTable.next(k);
                    if ( (k = n.arg1()).isnil() )
                        break;
                    LuaValue v = n.arg(2);
                    System.out.println(k + " > " + v);
                }
                globals.set(LuaValue.valueOf(key), resultingTable);
            }
        }
    }

    protected LuaTable convertObjectToTable(JSONObject sourceObject) {
        LuaTable result = LuaValue.tableOf();
//        result.
        System.out.println("Creating the table");
        Iterator<String> metaKeys = sourceObject.keys();
        while(metaKeys.hasNext()) {
            String key = metaKeys.next();
            Object value = sourceObject.get(key);

            if (value instanceof Integer) {
                System.out.println("Integer at " + key);
                result.hashset(LuaValue.valueOf(key), LuaValue.valueOf((Integer) value));
            } else if (value instanceof String) {
                System.out.println("String at " + key);
                result.hashset(LuaValue.valueOf(key), LuaValue.valueOf(value.toString()));
            } else if (value instanceof JSONArray) {
                System.out.println("Array encountered in sub-object at identifier " + key);
            } else if (value == JSONObject.NULL) {
                result.hashset(LuaValue.valueOf(key), LuaValue.NIL);
            } else {
                System.out.println("Object inside");
                System.out.print(value);
                // That's where the objects live
                // Extract the sub-object as a JSONObject
                JSONObject subObject = sourceObject.getJSONObject(key);
                result.hashset(LuaValue.valueOf(key), this.convertObjectToTable(subObject));
            }
        }
        return result;
    }
}
