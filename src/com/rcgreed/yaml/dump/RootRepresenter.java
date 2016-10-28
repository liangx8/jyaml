package com.rcgreed.yaml.dump;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rcgreed.yaml.Definition;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;

/**
 * Representer controller
 * 
 * @author arm
 *
 */
public class RootRepresenter {
	final private AtomicDataFormat dataFormat;
	final private List<Representer> tailRepreseterList;
	final private List<Representer> representerList;

	private RootRepresenter(RootRepresenter p, AtomicDataFormat df, List<Representer> irl, List<Representer> rl) {
		dataFormat = df;
		tailRepreseterList = irl;
		representerList = rl;
		parent = p;
	}

	final private RootRepresenter parent;

	public RootRepresenter next(AtomicDataFormat df) {
		return new RootRepresenter(this, df, tailRepreseterList, representerList);
	}

	public RootRepresenter() {
		parent = null;
		representerList = new ArrayList<>();
		dataFormat = new BaseAtomicDataFormat();
		ArrayList<Representer> base = new ArrayList<>();
		base.add(new AtomicRepresenter()); // seq 1
		base.add(new AbstractObjectRepresenter() {

			@Override
			protected boolean pass(Class<?> ty) {
				// skip Collection,Map,Array
				return Collection.class.isAssignableFrom(ty) || Map.class.isAssignableFrom(ty) || ty.isArray();
			}

			@Override
			protected TypeDescription typeDescription(Class<?> ty) {
				return defaultTypeDescription(ty);
			}
		}); // seq 2
		base.add(new SequenceRepresenter());
		base.add(new CollectionRepresenter());
		tailRepreseterList = Collections.unmodifiableList(base);
	}

	/**
	 * 对象按照 NullRepresenter-> 用户 representer > 内置的representer 的顺序转化 会以加入的顺序处理
	 * 
	 * @param representer
	 */
	public void add(Representer representer) {
		representerList.add(representer);

	}

	public Node scalarBoolean(boolean val) {
		return regularScalar(ScalarNode.TAG_BOOL, String.valueOf(val));
	}

	public Node scalarDouble(double val) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.floatFormat(val);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_FLOAT, str);
	}

	public Node scalarFloat(float val) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.floatFormat(val);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_FLOAT, str);
	}

	public Node scalarLong(long val) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.intFormat(val);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_INT, str);
	}

	public Node scalarShort(short val) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.intFormat(val);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_INT, str);
	}

	public Node scalarInt(int val) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.intFormat(val);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_INT, str);
	}

	public Node scalarString(String str) {
		return regularScalar(ScalarNode.TAG_STR, str);
	}

	public Node scalarDate(Date date) {
		String str = null;
		RootRepresenter rr = this;
		while (true) {
			str = rr.dataFormat.dateFormat(date);
			if (str != null)
				break;
			rr = rr.parent;
		}
		return regularScalar(ScalarNode.TAG_DATE, str);
	}

	private static Node regularScalar(final String tagName, final String value) {
		return new ScalarNode() {

			@Override
			protected String getValue() {
				return value;
			}

			@Override
			public String getName() {
				return tagName;
			}

		};
	}

	public Node represent(Object obj) throws YamlExecption {
		if (obj == null) {
			return regularScalar("!!null", "~");
		}

		for (Representer rpr : representerList) {
			Node n = rpr.represent(obj, this);
			if (n != null) {
				return n;
			}
		}
		for (Representer rpr : tailRepreseterList) {
			Node n = rpr.represent(obj, this);
			if (n != null) {
				return n;
			}
		}
		throw YamlExecption.unableHandle(obj.getClass());
	}

	private static class AtomicRepresenter implements Representer {
		/*
		 * int, 原始类型被boxing以后，就变成了非原始类型。因此这里无需判断primitive (non-Javadoc)
		 * 
		 * @see com.rcgreed.yaml.dump.Representer#represent(java.lang.Object,
		 * com.rcgreed.yaml.dump.RootRepresenter)
		 */
		@Override
		public Node represent(final Object obj, RootRepresenter root) {
			if (obj instanceof Short)
				return root.scalarShort((Short) obj);
			if (obj instanceof Integer)
				return root.scalarInt((Integer) obj);
			if (obj instanceof Long)
				return root.scalarLong((Long) obj);
			if (obj instanceof Float)
				return root.scalarFloat((Float) obj);
			if (obj instanceof Double)
				return root.scalarDouble((Double) obj);
			if (obj instanceof Boolean)
				return root.scalarBoolean((Boolean) obj);
			if (obj instanceof String)
				return root.scalarString((String) obj);
			if (obj instanceof Date)
				return root.scalarDate((Date) obj);

			return null;
		}
	}

	/**
	 * 转化基本类型的值对象,并且只在{@link RootRepresenter}范围内使用 如果需要自定义,可以自己定义
	 * {@link Representer},生成的 {@link ScalarNode} 重写getValue 方法实现
	 * 
	 * @author arm
	 *
	 */
	public static interface AtomicDataFormat {
		// 只接受scalar对象的class, int,float,Date
		String dateFormat(Date t);

		String intFormat(long v);

		String floatFormat(double v);
	}

	public static class BaseAtomicDataFormat implements AtomicDataFormat {

		@Override
		public String dateFormat(Date t) {
			return Definition.timestampFormat.format(t);
		}

		@Override
		public String intFormat(long v) {
			return String.valueOf(v);
		}

		@Override
		public String floatFormat(double v) {
			return String.valueOf(v);
		}
	}
}
