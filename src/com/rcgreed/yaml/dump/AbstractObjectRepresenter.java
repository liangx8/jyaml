package com.rcgreed.yaml.dump;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.RootRepresenter.AtomicDataFormat;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.Node;

/**
 * @author arm
 */
public abstract class AbstractObjectRepresenter implements Representer {
	@Override
	final public Node represent(Object obj, RootRepresenter root) throws YamlExecption {
		if (obj == null) {
			throw new NullPointerException("impossible");
		}
		Class<?> ty = obj.getClass();
		if (pass(ty))
			return null;
		final TypeDescription td = typeDescription(ty);
		Field fds[] = ty.getFields();
		if (fds.length == 0) {
			throw YamlExecption.unableHandle(ty);
		}
		HashSet<Entry<Node, Node>> entries = new HashSet<>();
		try {
			for (Field f : fds) {
				AtomicDataFormat df=td.dataFormat(f.getName());
				RootRepresenter current=root;
				if (df!=null){
					current=root.next(df);
				}
				Node key = current.scalarString(td.representFieldName(f.getName()));
				Class<?> subTy = f.getType();
				Node value = null;
				if (subTy.isPrimitive()) {
					if (subTy == int.class){
						int v=f.getInt(obj);
						value = current.scalarInt(v);
					}
					if (subTy == short.class)
						value = current.scalarShort(f.getShort(obj));
					if (subTy == long.class)
						value = current.scalarLong(f.getLong(obj));
					if (subTy == float.class)
						value = current.scalarFloat(f.getFloat(obj));
					if (subTy == double.class)
						value = current.scalarDouble(f.getDouble(obj));
					if (subTy == boolean.class)
						value = current.scalarBoolean(f.getBoolean(obj));
					if (value == null)
						throw YamlExecption.unableHandle(subTy);
				} else {
					value = current.represent(f.get(obj));
				}
				entries.add(newEntry(key,value));
			}

		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return new MappingNode() {
			@Override
			public String getName() {
				// 缺省不会返回null, 如果是自定义继承类.如果不重写这个方法,也不会返回null,
				// 如果重写然后返回null就抛异常.不做处理
				return td.representTagName();
			}

			@Override
			protected Set<Entry<Node, Node>> getEntries() {
				return Collections.unmodifiableSet(entries);
			}

		};
	}

	private static Entry<Node, Node> newEntry(final Node key, final Node value) {
		return new Entry<Node, Node>() {
			
			@Override
			public Node setValue(Node arg0) {
				throw new UnsupportedOperationException("don't call me");
			}
			
			@Override
			public Node getValue() {
				return value;
			}
			
			@Override
			public Node getKey() {
				return key;
			}
		};
	}

	/**
	 * this method can be ignore if pass() return true
	 * 
	 * @param ty
	 * @return
	 */
	protected abstract TypeDescription typeDescription(Class<?> ty);

	protected abstract boolean pass(Class<?> ty);

	public static interface TypeDescription {
		String representFieldName(String fieldName);
		/**
		 * do not return null
		 * @return
		 */
		String representTagName();
		AtomicDataFormat dataFormat(String fieldName);
	}

	public static TypeDescription defaultTypeDescription(final Class<?> clz) {
		return new TypeDescription() {

			@Override
			public String representTagName() {
				return "!" + clz.getName();
			}

			@Override
			public String representFieldName(String fieldName) {
				return fieldName;
			}

			@Override
			public AtomicDataFormat dataFormat(String fieldName) {
				// return null use default
				return null;
			}
		};
	}
}
