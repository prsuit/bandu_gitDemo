/*******************************************************************************
 * Copyright
 ******************************************************************************/
package com.tt;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * JSON put/get utils
 */
public class JSONUtil {

    /**
     * Put key/value to JSONObject with no JSONException throws
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @param value
     *            value
     */
    public static void putQuietly(JSONObject json, String key, Object value) {
        try {
            json.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Put Map entries to JSONObject with no JSONException throws
     * 
     * @param json
     *            JSONObject
     * @param map
     *            key/value map
     */
    public static void putQuietly(JSONObject json, Map<String, Object> map) {
        if (map == null)
            return;
        for (Map.Entry<String, Object> e : map.entrySet()) {
            putQuietly(json, e.getKey(), e.getValue());
        }
    }

    /**
     * Get value from JSONObject
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @return value of key
     */
    public static final Object getQuietly(JSONObject json, String key) {
        try {
            return json.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get String value from JSONObject
     * 
     * @param json
     *            JSONObject
     * @param key
     *            key
     * @return value of key
     */
    public static final String getStringQuietly(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return a new JSONObject and put Bundle key/values
     * 
     * @param bundle
     *            bundle
     * @return JSONObject
     */
    public static JSONObject bundleToJSON(Bundle bundle) {
        JSONObject json = new JSONObject();
        for (Iterator<String> it = bundle.keySet().iterator(); it.hasNext();) {
            String key = it.next();
            putQuietly(json, key, bundle.get(key));
        }
        return json;
    }

    /**
     * Return a new JSONObject and put a pair key/value
     * 
     * @param key
     * @param value
     * @return JSONObject
     */
    public static JSONObject pairToJSON(String key, Object value) {
        JSONObject json = new JSONObject();
        putQuietly(json, key, value);
        return json;
    }

    /**
     * Return a new JSONObject and put map key/values
     * 
     * @param map
     * @return JSONObject
     */
    public static JSONObject mapToJSON(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        putQuietly(json, map);
        return json;
    }

}
