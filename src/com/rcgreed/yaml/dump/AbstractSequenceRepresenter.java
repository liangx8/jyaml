package com.rcgreed.yaml.dump;

import java.sql.Date;
import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;
import com.rcgreed.yaml.node.SequenceNode;
import com.rcgreed.yaml.node.SequenceNode.YamlIterator;

public abstract class AbstractSequenceRepresenter implements Representer {

	@Override
	public Node represent(final ClazzValue cv,final Context ctx,final Representer base) throws YamlExecption {
		final Class<?> clz =cv.first();
		if(pass(clz)) return null;
		Tag tag=getTag(clz);
		final Tag ftag=ctx.incept(clz, tag);
		if (ftag.kind()!=Tag.Kind.Sequence){
			throw new YamlExecption("Only Sequence kind is allow");
		}
		
		return new SequenceNode() {
			

			
			@Override
			public PresenterConfig presenterConfig() {
				return ctx.getConfig(clz);
			}
			
			@Override
			public Tag getTag() {
				return ftag;
			}

			@Override
			protected YamlIterator iterator() {
				return new InnerIterator(clz, getData(cv), ctx, base);
			}
		};
	}
	protected abstract boolean pass(Class<?> targetClass);
	protected abstract Iterator<ClazzValue >getData(ClazzValue array);
	protected abstract Tag getTag(Class<?> clz);
	private static class InnerIterator implements YamlIterator{
		final Iterator<ClazzValue > data;
		final Context ctx;
		final Representer base;
		final Class<?> parentClass;
		public InnerIterator(Class<?> pClass,Iterator<ClazzValue > v,Context context,Representer rpr) {
			data=v;
			ctx=context;
			base=rpr;
			parentClass=pClass;
		}
		@Override
		public boolean hasNext() {
			return data.hasNext();
		}

		@Override
		public Node next() throws YamlExecption {
			ClazzValue p=data.next();
			Tag tag = null;
//			if (subTy.isArray())
//				tag = Tag.SeqTag;
			Class<?> subTy=p.first();
			if (subTy == int.class || subTy == short.class || subTy == long.class || subTy == Integer.class
					|| subTy == Short.class || subTy == Long.class)
				tag = Tag.IntTag;
			if (subTy == float.class || subTy == double.class || subTy == Float.class || subTy == Double.class)
				tag = Tag.FloatTag;
			if (subTy == boolean.class || subTy == Boolean.class)
				tag = Tag.BoolTag;
			if (subTy == String.class) {
				tag = Tag.StrTag;
			}
			if (subTy == Date.class) {
				tag = Tag.DateTag;
			}
			if (tag != null) {
				return	ScalarNode.newInstance(tag, ctx.incept(parentClass, p), ctx.getConfig(subTy));
			}

			return base.represent(p, ctx, base);
		}
		
	}
}
