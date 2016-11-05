package com.rcgreed.yaml.inceptor;

import java.util.Date;
import java.util.HashMap;

import com.rcgreed.yaml.Definition;
import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.ClazzValue;
import com.rcgreed.yaml.dump.PresenterConfig;

public class Context
		implements MapScalarKeyInceptor, MapScalarValueInceptor, SequenceScalarValueInterceptor, TagInceptor {
	final private Linked<MapScalarKeyInceptor> keyInceptors = new Linked<>();
	final private Linked<MapScalarValueInceptor> valueInceptors = new Linked<>();
	final private Linked<SequenceScalarValueInterceptor> sequenceScalarIncepteros = new Linked<>();
	final private Linked<TagInceptor> tagInceptors = new Linked<>();
	private PresenterConfig defaultConfig = PresenterConfig.Builder.defaultPresenterConfig;
	private HashMap<Class<?>, PresenterConfig> configMap = new HashMap<>();

	public void setPresenterConfig(PresenterConfig cfg) {
		defaultConfig = cfg;
	}

	public void putPresenterConfigInceptor(Class<?>key,PresenterConfig inceptor) {
		configMap.put(key, inceptor);
	}

	public void addMapScalarKeyInceptor(MapScalarKeyInceptor inceptor) {
		keyInceptors.add(inceptor);
	}

	public void addMapScalarValueInceptor(MapScalarValueInceptor inceptor) {
		valueInceptors.add(inceptor);
	}

	public void addSequenceScalarInceptor(SequenceScalarValueInterceptor inceptor) {
		sequenceScalarIncepteros.add(inceptor);
	}

	public void addTagInceptor(TagInceptor inceptor) {
		tagInceptors.add(inceptor);
	}

	// TagInceptor
	@Override
	public Tag incept(Class<?> clz, Tag org) throws YamlExecption {
		for (TagInceptor ti : tagInceptors) {
			Tag t = ti.incept(clz, org);
			if (t != null)
				return t;
		}
		return org;
	}

	// SequenceScalarValueIncerpter
	@Override
	public String incept(Class<?> targetClz, ClazzValue value) throws YamlExecption {
		for (SequenceScalarValueInterceptor ssi : sequenceScalarIncepteros) {
			String v = ssi.incept(targetClz, value);
			if (v != null)
				return v;
		}
		if (value.first() == String.class)
			return (String) value.second();
		if (value.first() == Date.class) {
			return Definition.timestampFormat.format(value.second());
		}
		return value.second().toString();
	}

	// MapScalarValueInceptor implements
	@Override
	public String incept(Class<?> targetClz, Object key, ClazzValue value) throws YamlExecption {
		for (MapScalarValueInceptor msvi : valueInceptors) {
			String v = msvi.incept(targetClz, key, value);
			if (v != null) {
				return v;
			}
		}
		if (value.first() == Date.class) {
			return Definition.timestampFormat.format(value.second());
		}
		if (value.first() == String.class)
			return (String) value.second();
		return value.second().toString();
	}

	// MapScalarKeyInceptor implements
	@Override
	public String incept(Class<?> targetClz, Object mapKey) throws YamlExecption {
		if (mapKey instanceof String) {
			for (MapScalarKeyInceptor mski : keyInceptors) {
				String v = mski.incept(targetClz, mapKey);
				if (v != null) {
					return v;
				}
			}
			return (String) mapKey;
		}
		return null; // 只有当 只 Map.class 中才有机会跑到这里.
	}

	public PresenterConfig getConfig(Class<?> clz) {
		if (clz == null)
			return defaultConfig;
		PresenterConfig pc=configMap.get(clz);
		if(pc==null) return defaultConfig;
		return pc;
	}
}
