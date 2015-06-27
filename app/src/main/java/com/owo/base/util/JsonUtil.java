package com.owo.base.util;

import java.util.Collection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.owo.app.common.Check;

public class JsonUtil {
	public static final String CLASS_NAME = "JsonUtil";

	public static String getJsonString(JSONObject jObject, String key, String defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				String ret = jObject.getString(key);
				if (!ret.equalsIgnoreCase("null"))
					return ret;
				else
					return defaultValue;
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static int getJsonInt(JSONObject jObject, String key, int defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getInt(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static long getJsonLong(JSONObject jObject, String key, long defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getLong(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static boolean getJsonInt(JSONObject jObject, String key, boolean defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getBoolean(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static double getJsonDouble(JSONObject jObject, String key, double defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getDouble(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static JSONObject getJsonObject(JSONObject jObject, String key, JSONObject defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getJSONObject(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static JSONArray getJsonArray(JSONObject jObject, String key, JSONArray defaultValue) {
		if (jObject != null && jObject.has(key))
			try {
				return jObject.getJSONArray(key);
			} catch (JSONException e) {
			}
		return defaultValue;
	}

	public static String collectionToJsonStr(Collection<String> collection) {
		if (collection == null) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();
		for (String item : collection) {
			jsonArray.put(item);
		}

		return jsonArray.toString();
	}

	/**
	 * HashMap<String, String>转{@JSONObject}
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToJSONStr(HashMap<String, String> map) {
		if (null == map) {
			return null;
		}
		JSONObject jsonObj = new JSONObject();
		try {
			for (String key : map.keySet()) {
				jsonObj.put(key, map.get(key));
			}
		} catch (JSONException e) {
		}
		return jsonObj.toString();
	}

	private static JSONObject moveToLeaf(JSONObject obj, String... keysHierarchy) {
		int maxCount = keysHierarchy.length - 1;
		for (int i = 0; i < maxCount && obj != null; ++i) {
			obj = obj.optJSONObject(keysHierarchy[i]);
		}
		return obj;
	}

	/**
	 * 从JsonObject获取树状子节点数组
	 * 
	 * @param obj
	 *            当前节点
	 * @param keysHierarchy
	 *            节点key的层级关系，如 parent children grand-children
	 * */
	public static JSONArray getArrayInTree(JSONObject obj, String... keysHierarchy) {
		if (obj == null || keysHierarchy == null || keysHierarchy.length < 1) {
			return null;
		}

		obj = moveToLeaf(obj);
		return (obj != null) ? obj.optJSONArray(keysHierarchy[keysHierarchy.length - 1]) : null;
	}

	/**
	 * 从JsonObject获取树状子节点
	 * 
	 * @param obj
	 *            当前节点
	 * @param keysHierarchy
	 *            节点key的层级关系，如parent, children, grand-children
	 * */
	public static JSONObject getObjectInTree(JSONObject obj, String... keysHierarchy) {
		if (obj == null || keysHierarchy == null || keysHierarchy.length < 1) {
			return null;
		}

		obj = moveToLeaf(obj);
		return (obj != null) ? obj.optJSONObject(keysHierarchy[keysHierarchy.length - 1]) : null;
	}

	/**
	 * JSONArray转换为Collection String
	 * */
	public static Collection<String> appendString(Collection<String> target, JSONArray array) {
		try {
			for (int i = 0; i < array.length(); i++) {
				target.add(array.getString(i));
			}
		} catch (JSONException e) {
		}
		return target;
	}

	public static JSONObject build(Object... params) {
		return safePut(new JSONObject(), params);
	}

	public static JSONObject safePut(JSONObject json, Object... params) {
		Check.d(json != null);
		final int paramLen = params.length;
		if (paramLen % 2 != 0) {
			// 必须是成对出现
			return json;
		}

		try {
			for (int i = 0; i < paramLen; i += 2)
				json.put(params[i].toString(), params[i + 1]);
		} catch (JSONException e) {
		}

		return json;
	}

	public static JSONObject from(String jsonString) {
		JSONObject json = null;
		try {
			json = new JSONObject(jsonString);
		} catch (Exception e) {
		}
		return json;
	}
}