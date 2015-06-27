package com.owo.base.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectHelper {
	// Create

	public static <T> T create(Class<T> cls, Object... args) {
		return create(cls, resolveArgsTypes(args), args);
	}

	public static <T> T create(Class<T> cls, Class<?>[] types, Object... args) {
		try {
			Constructor<T> ctr = cls.getDeclaredConstructor(types);
			ctr.setAccessible(true);
			return ctr.newInstance(args);
		} catch (Throwable e) {
			return null;
		}
	}

	// Getter

	public static Method getMethod(Class<?> targetClass, String methodName,
			Class<?>... types) {
		try {
			Method method = targetClass.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			return method;
		} catch (Throwable e) {
			return null;
		}
	}

	// Invoke

	public static Object invokeStatic(String className, String methodName,
			Object... args) {
		return invokeStatic(className, methodName, resolveArgsTypes(args), args);
	}

	public static Object invokeStatic(String className, String methodName,
			Class<?>[] argTypes, Object... args) {
		try {
			return invokeStatic(Class.forName(className), methodName, argTypes,
					args);
		} catch (Throwable e) {
			return null;
		}
	}

	public static Object invokeStatic(Class<?> classType, String methodName,
			Object... args) {
		return invokeStatic(classType, methodName, resolveArgsTypes(args), args);
	}

	public static Object invokeStatic(Class<?> classType, String methodName,
			Class<?>[] argTypes, Object... args) {
		try {
			return invoke(null, classType, methodName, argTypes, args);
		} catch (Throwable e) {
			return null;
		}
	}

	public static Object invokeStatic(Method method, Object... args) {
		return invoke(null, method, args);
	}

	/**
	 * @param args
	 *            (Note:there must be a clear distinction between int type and
	 *            float, double, etc.)
	 */
	public static Object invoke(Object obj, String methodName, Object... args) {
		return invoke(obj, obj.getClass(), methodName, resolveArgsTypes(args),
				args);
	}

	public static Object invoke(Object obj, Method method, Object... args) {
		try {
			return method.invoke(obj, args);
		} catch (Throwable e) {
			return null;
		}
	}

	public static Object invoke(Object obj, Class<?> targetClass,
			String methodName, Class<?>[] argTypes, Object... args) {
		try {
			Method method = targetClass.getDeclaredMethod(methodName, argTypes);
			method.setAccessible(true);
			return method.invoke(obj, args);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * @return resolve primitive type for all primitive wrapper types.
	 */
	public static Class<?> rawType(Class<?> type) {
		if (type.equals(Boolean.class)) {
			return boolean.class;
		} else if (type.equals(Integer.class)) {
			return int.class;
		} else if (type.equals(Float.class)) {
			return float.class;
		} else if (type.equals(Double.class)) {
			return double.class;
		} else if (type.equals(Short.class)) {
			return short.class;
		} else if (type.equals(Long.class)) {
			return long.class;
		} else if (type.equals(Byte.class)) {
			return byte.class;
		} else if (type.equals(Character.class)) {
			return char.class;
		}

		return type;
	}

	private static Class<?>[] resolveArgsTypes(Object... args) {
		Class<?>[] types = null;
		if (args.length > 0) {
			types = new Class<?>[args.length];
			for (int i = 0; i < args.length; ++i) {
				types[i] = rawType(args[i].getClass());
			}
		}
		return types;
	}
}