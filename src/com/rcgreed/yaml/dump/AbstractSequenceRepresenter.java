package com.rcgreed.yaml.dump;

import java.util.Iterator;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.SequenceNode;
import com.rcgreed.yaml.utils.Pair;
import com.rcgreed.yaml.utils.YamlIterator;

public abstract class AbstractSequenceRepresenter implements Representer {

	@Override
	public Node represent(Object obj, final RootRepresenter root) throws YamlExecption {
		Class<?> type=obj.getClass();
		if(pass(type))return null;
		final Iterator<Pair<Class<?>,Object> >itr=iterate(obj);
		
		return new SequenceNode() {
			
			@Override
			protected YamlIterator<Node> iterateNode() {
				return new YamlIterator<Node>() {

					@Override
					public boolean hasNext() {
						return itr.hasNext();
					}

					@Override
					public Node next() throws YamlExecption{
						Pair<Class<?>,Object> p=itr.next();
						return root.represent(p.second());
					}
				};
			}
		};
	}
	protected abstract Iterator<Pair<Class<?>,Object> >iterate(Object obj);
	protected abstract boolean pass(Class<?> type);
}
