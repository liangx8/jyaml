package com.rcgreed.yaml.dump;

import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.MappingNode.YamlIterator;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.utils.Pair;

public abstract class AbstractMappingRepresenter implements Representer {

	@Override
	public Node represent(final ClazzValue value,final Context ctx,final Representer base) throws YamlExecption {
		if(pass(value.first())) return null;
		Tag tag=getTag(value.first());
		final Tag ftag=ctx.incept(value.first(), tag);
		if(ftag.kind()!=Tag.Kind.Mapping){
			throw new YamlExecption("不同的类型");
		}
		final YamlIterator yitr=new YamlIterator() {
			final Iterator<Pair<ClazzValue,ClazzValue>> itr=getData(value);
			@Override
			public Pair<Node, Node> next() throws YamlExecption {
				Pair<ClazzValue,ClazzValue> p=itr.next();
				//FIXME: 实现 Map类型的 MappingNode
				return null;
			}
			
			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}
		};
		return new MappingNode() {
			
			@Override
			public PresenterConfig presenterConfig() {
				return ctx.getConfig(value.first());
			}
			
			@Override
			public Tag getTag() {
				return ftag;
			}
			
			@Override
			public void put(Node key, Node value) {
				throw new RuntimeException("do call me");
			}
			
			@Override
			protected YamlIterator getData() {
				return yitr;
			}
		};
	}
	protected abstract boolean pass(Class<?> clz);
	protected abstract Iterator<Pair<ClazzValue,ClazzValue> > getData(ClazzValue cv);
	protected abstract Tag getTag(Class<?> clz);
}
