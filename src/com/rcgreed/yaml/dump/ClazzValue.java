package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.utils.Pair;

public class ClazzValue implements Pair<Class<?>, Object> {
	final private Class<?> clz;
	final private Object obj;
	@Override
	public Class<?> first() {
		return clz;
	}

	@Override
	public Object second() {
		return obj;
	}
	public ClazzValue(Class<?> c,Object o) {
		clz=c;
		obj=o;
	}

}
