package com.rcgreed.yaml.dump;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.Linked;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;

public class RepresenterAny implements Representer {
	final private Linked<Representer> representers=new Linked<>();
	public RepresenterAny() {
		representers.add(new ScalarRepresenter());
		representers.add(new MappingRepresenter());
		representers.add(new SequenceRepresenter());
		representers.add(new IteratorRepresenter());
	}
	@Override
	public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
		for(Representer rp:representers){
			Node n=rp.represent(clz, obj, ctx, this);
			if(n!=null){
				return n;
			}
		}
		throw YamlExecption.unableHandle(clz);
	}
	private static class ScalarRepresenter implements Representer{
		@Override
		public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
			if (Collection.class.isAssignableFrom(clz) || Map.class.isAssignableFrom(clz) || clz.isArray()) {
				return null;
			}
			Tag tag=null;
			if (clz == int.class || clz == short.class || clz == long.class ||
					clz == Integer.class || clz == Short.class || clz == Long.class)
				tag= Tag.IntTag;
			if (clz == float.class || clz == double.class || clz == Float.class || clz == Double.class)
				tag= Tag.FloatTag;
			if (clz == boolean.class || clz==Boolean.class)
				tag= Tag.BoolTag;
			if (clz == String.class) {
				tag= Tag.StrTag;
			}
			if (clz == Date.class) {
				tag= Tag.DateTag;
			}
			if (tag==null) return null;
			return ScalarNode.newInstance(tag, ctx.incept(null, clz, obj),ctx.getConfig(clz));

		}
	}
}
