package com.owo.base.pattern;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * {@link #Instance} supports global and local singleton patterns:
 * 
 * <h1>Global Singleton</h1>
 * <p>
 * Directly invoke <code>Instance.of(X.class)</code> to get a global singleton
 * instance, but the X class must own a non-public non-parametric constructor.
 * </p>
 * 
 * <h1>Local Singleton</h1>
 * <p>
 * In a local object A, you invoke <code>Instance.createLocal(new X())</code> to
 * create a local singleton instance, and then you can only call
 * <code>Instance.of(X.class)</code> during the life-cycle of A.
 * </p>
 */
public class Singleton {
	private static final HashMap<Class<?>, Object> sInstanceMap = new HashMap<Class<?>, Object>();

	@SuppressWarnings("unchecked")
	public static <T> T of(Class<T> cls) {
		// 1) get existed instance
		Object instance = sInstanceMap.get(cls);
		if (instance == null) {
			synchronized (sInstanceMap) {
				if (instance == null) {
					try {
						Constructor<T> ctr = cls.getDeclaredConstructor();
						ctr.setAccessible(true);
						instance = ctr.newInstance();
						sInstanceMap.put(cls, instance);
					} catch (Throwable e) {

					}
				}
			}

		}

		return (T) instance;
	}

	public static void destroy(Class<?> cls) {
		if (sInstanceMap.containsKey(cls)) {
			sInstanceMap.remove(cls);
		}
	}

	public static void destroy() {
		sInstanceMap.clear();
	}
}