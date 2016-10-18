package com.rcgreed.yaml;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class BaseConversion implements Conversion {
	private final ArrayList<Conversion> listConversion=new ArrayList<>();
	@Override
	public YamlObject convert(Object obj,Conversion parent) throws YamlExecption {
		
		YamlObject yobj=null;
		yobj=any(obj);
		if(yobj==null){
			throw new YamlExecption(String.format("BaseConversion: Unable to convert class %s", obj.getClass().getName()));
		}
		return yobj;
	}
	public void register(Conversion c){
		listConversion.add(c);
	}

	private YamlObject any(Object obj) throws YamlExecption {
		for(Conversion c:listConversion){
			YamlObject yobj = c.convert(obj,this);
			if(yobj != null){
				return yobj;
			}
		}
		if (obj instanceof Integer || obj instanceof Short || obj instanceof Long) {
			Number n = (Number) obj;
			return new YamlScalar(n.longValue());
		}
		if (obj instanceof Float || obj instanceof Double) {
			Number n = (Number) obj;
			return new YamlScalar(n.doubleValue());
		}
		if (obj instanceof Boolean) {
			return new YamlScalar((Boolean) obj);
		}
		if (obj instanceof String) {
			return new YamlScalar((String) obj);
		}
		
		if (obj instanceof Date){
			return new YamlScalar((Date)obj);
		}
		if (obj.getClass().isArray()) {
			YamlSequence seq = sequence(obj);
			if (seq != null) {
				return seq;
			}
		} else {
			YamlMapping map = mapping(obj);
			if (map != null) {
				return map;
			}
		}
		return null;
	}

	private YamlMapping mapping(Object obj) throws YamlExecption {
		Class<?> clz = obj.getClass();
		YamlMapping map = new YamlMapping(obj.getClass().getName());
		Field fields[] = clz.getFields();
		if(fields.length==0){
			throw new YamlExecption(String.format("No field in class %s", clz.getName()));
		}
		try {
			for (Field f : fields) {
				Class<?> vt = f.getType();
				String key=f.getName();
				if (vt.isPrimitive()) {
					if (vt == short.class) {
						map.put(key, new YamlScalar(f.getShort(obj)));
						continue;
					}
					if (vt == int.class) {
						map.put(key, new YamlScalar(f.getInt(obj)));
						continue;
					}
					if (vt == long.class) {
						map.put(key, new YamlScalar(f.getLong(obj)));
						continue;
					}
					if (vt == float.class) {
						map.put(key, new YamlScalar(f.getFloat(obj)));
						continue;
					}
					if (vt == double.class) {
						map.put(key, new YamlScalar(f.getDouble(obj)));
						continue;
					}
					if (vt == boolean.class) {
						map.put(key, new YamlScalar(f.getBoolean(obj)));
						continue;
					}
					throw new YamlExecption(String.format("Unable to convert class %s at field %s@%s", vt.getName(),
							key, clz.getName()));
				} else {
					Object val = f.get(obj);
					if(val==null){
						map.put(key, YamlScalar.NullScalar);
						continue;
					}
					map.put(key, any(val));
				}
			}
		} catch (IllegalArgumentException e) {
			throw new YamlExecption(e);
		} catch (IllegalAccessException e) {
			throw new YamlExecption(e);
		}
		return map;
	}

	private YamlSequence sequence(Object obj) throws YamlExecption {
		Class<?> clz = obj.getClass();
		if (!clz.isArray()) {
			throw new RuntimeException("give me Array");
		}
		Class<?> aType = clz.getComponentType();
		YamlSequence seq = new YamlSequence();
		if (aType.isPrimitive()) {
			if (populatePrimitive(seq, aType, obj)) {
				return seq;
			}
			throw new YamlExecption(String.format("unable to convert type %s of Array", aType.getName()));
		}
		int length=Array.getLength(obj);
		for(int i=0;i<length;i++){
			Object val=Array.get(obj, i);
			if(val==null){
				seq.add(YamlScalar.NullScalar);
			} else {
				seq.add(any(val));
			}
		}
		return seq;
	}

	private interface CB {
		YamlScalar call(int idx) throws YamlExecption;
	}

	private boolean populatePrimitive(YamlSequence seq, Class<?> pType, final Object obj) throws YamlExecption {
		int length = Array.getLength(obj);
		CB cb = null;
		if (pType == int.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					int v = Array.getInt(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		if (pType == short.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					short v = Array.getShort(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		if (pType == long.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					long v = Array.getLong(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		if (pType == float.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					float v = Array.getFloat(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		if (pType == double.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					double v = Array.getDouble(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		if (pType == boolean.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					boolean v = Array.getBoolean(obj, idx);
					return new YamlScalar(v);
				}
			};
		}
		/*
		if (pType == String.class) {
			cb = new CB() {

				@Override
				public YamlScalar call(int idx) throws YamlExecption {
					long v = Array.getLong(obj, idx);
					return new YamlScalar(v);
				}
			};
		}*/
		if (cb == null)
			return false;
		for (int i = 0; i < length; i++) {
			seq.add(cb.call(i));
		}
		return true;
	}

}
