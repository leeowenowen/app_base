package com.owo.base.common;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

@SuppressWarnings("unchecked")
public class Param {
	private SparseArray<Object> mKVs = new SparseArray<Object>();

	public <T> T get(int key) {
		Object obj = mKVs.get(key);
		if (obj == null) {
			return null;
		}
		return (T) obj;
	}

	public <T> T get(int key, T defaultValue) {
		Object obj = mKVs.get(key);
		if (obj == null) {
			return defaultValue;
		}
		return (T) obj;
	}

	public Param put(int key, Object value) {
		mKVs.put(key, value);
		return this;
	}

	private static List<Param> sRecycled = new ArrayList<Param>();

	public void recycle() {
		mKVs.clear();
		sRecycled.add(this);
	}

	public static Param obtain() {
		if (sRecycled.size() > 0) {
			Param params = sRecycled.remove(0);
			return params;
		}
		return new Param();
	}

}
