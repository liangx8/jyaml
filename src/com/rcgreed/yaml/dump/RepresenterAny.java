package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.Linked;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;

public class RepresenterAny implements Representer {
	final private Linked<Representer> representers=new Linked<>();
	public RepresenterAny() {
		representers.add(new TopScalarRepresenter());
	}
	@Override
	public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
		for(Representer rp:representers){
			Node n=rp.represent(clz, obj, ctx, base);
			if(n!=null){
				return n;
			}
		}
		throw YamlExecption.unableHandle(clz);
	}
	private static class TopScalarRepresenter implements Representer{
		@Override
		public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
			//skip if not top level
			if(base!=null) return null;
			Tag tag=ctx.incept(clz);
			if(tag.kind()==Tag.Kind.Scalar){
				return ScalarNode.newInstance(tag, ctx.incept(null, clz, obj));
			}
			return null;
		}
	}
}
