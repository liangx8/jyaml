package com.rcgreed.yaml.inceptor;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.rcgreed.yaml.Definition;
import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.node.ScalarNode;

public class Context implements MapScalarKeyInceptor, MapScalarValueInceptor, SequenceScalarInterceptor, TagInceptor,
		PresenterConfigInceptor {
	final private Linked<MapScalarKeyInceptor> keyInceptors = new Linked<>();
	final private Linked<MapScalarValueInceptor> valueInceptors = new Linked<>();
	final private Linked<SequenceScalarInterceptor> sequenceScalarIncepteros = new Linked<>();
	final private Linked<TagInceptor> tagInceptors = new Linked<>();
	private PresenterConfig defaultConfig;
	private PresenterConfigInceptor pci = new PresenterConfigInceptor() {

		@Override
		public PresenterConfig getConfig(Class<?> clz) {
			return PresenterConfig.defaultPresenterConfig;
		}
	};

	public void setPresenterConfig(PresenterConfig cfg) {
		defaultConfig = cfg;
	}

	public void setPresenterConfigInceptor(PresenterConfigInceptor inceptor) {
		pci = inceptor;
	}

	public void addMapScalarKeyInceptor(MapScalarKeyInceptor inceptor) {
		keyInceptors.add(inceptor);
	}

	public void addMapScalarValueInceptor(MapScalarValueInceptor inceptor) {
		valueInceptors.add(inceptor);
	}

	public void addSequenceScalarInceptor(SequenceScalarInterceptor inceptor) {
		sequenceScalarIncepteros.add(inceptor);
	}

	public void addTagInceptor(TagInceptor inceptor) {
		tagInceptors.add(inceptor);
	}

	public Context() {
		keyInceptors.add(new MapScalarKeyInceptor() {

			@Override
			public String incept(Class<?> targetClz, Object mapKey) {
				return (String) mapKey;
			}
		});
		valueInceptors.add(new MapScalarValueInceptor() {

			@Override
			public String incept(Class<?> targetClz, ScalarNode key, Class<?> clz, Object value) {
				if (clz == Date.class)
					return Definition.timestampFormat.format((Date) value);

				if (clz == String.class)
					return (String) value;
				return
						value.toString();
			}
		});
		sequenceScalarIncepteros.add(new SequenceScalarInterceptor() {

			@Override
			public String incept(Class<?> targetClz, Class<?> clz, Object value) {
				if (clz == Date.class)
					return Definition.timestampFormat.format((Date) value);

				if (clz == String.class)
					return (String) value;
				return value.toString();
			}
		});
		tagInceptors.add(new TagInceptor() {

			@Override
			public Tag incept(Class<?> clz,Tag org) {
/*				if (Collection.class.isAssignableFrom(clz) || Map.class.isAssignableFrom(clz)) {
					return null;
				}
				if (clz.isArray())
					return Tag.SeqTag;
				if (clz == int.class || clz == short.class || clz == long.class ||
						clz == Integer.class || clz == Short.class || clz == Long.class)
					return Tag.IntTag;
				if (clz == float.class || clz == double.class || clz == Float.class || clz == Double.class)
					return Tag.FloatTag;
				if (clz == boolean.class || clz==Boolean.class)
					return Tag.BoolTag;
				if (clz == String.class) {
					return Tag.StrTag;
				}
				if (clz == Date.class) {
					return Tag.DateTag;
				}
				if (clz.getFields().length > 0) {
					return new Tag() {

						@Override
						public String getName() {
							return "!" + clz.getName();
						}

						@Override
						public Kind kind() {
							return Tag.Kind.Mapping;
						}

					};
				}
*/
				return org;
			}
		});
	}

	@Override
	public Tag incept(Class<?> clz,Tag org) throws YamlExecption {
		for (TagInceptor ti : tagInceptors) {
			Tag t = ti.incept(clz,org);
			if (t != null)
				return t;
		}
		return org;
	}

	@Override
	public String incept(Class<?> targetClz, Class<?> clz, Object seqEntry) throws YamlExecption {
		for (SequenceScalarInterceptor ssi : sequenceScalarIncepteros) {
			String v = ssi.incept(targetClz, clz, seqEntry);
			if (v != null)
				return v;
		}
		if (clz == String.class)
			return (String) seqEntry;
		return seqEntry.toString();
	}

	@Override
	public String incept(Class<?> targetClz, ScalarNode key, Class<?> clz, Object value) throws YamlExecption {
		for (MapScalarValueInceptor msvi : valueInceptors) {
			String v = msvi.incept(targetClz, key, clz, value);
			if (v != null) {
				return v;
			}
		}
		if (clz == String.class)
			return (String) value;
		return value.toString();
	}

	@Override
	public String incept(Class<?> targetClz, Object mapKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PresenterConfig getConfig(Class<?> clz) {
		PresenterConfig pc = pci.getConfig(clz);
		if (pc == null)
			return defaultConfig;
		return pc;
	}
}
