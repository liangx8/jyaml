package com.rcgreed.yaml.dump;

import java.util.Collection;
import java.util.Iterator;

import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public class CollectionRepresenter extends AbstractSequenceRepresenter {

	@Override
	protected Iterator<Pair<Class<?>, Object>> iterate(Object obj) {
		@SuppressWarnings("unchecked")
		Collection<Object> co=(Collection<Object>) obj;
		final Iterator<Object> itr=co.iterator();
		return new Iterator<Pair<Class<?>,Object>>() {
			
			@Override
			public Pair<Class<?>, Object> next() {
				Object o=itr.next();
				if(o==null){
					return Helper.newPair(null, null);
				}
				return Helper.newPair(o.getClass(), o);
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return itr.hasNext();
			}
		};
	}

	@Override
	protected boolean pass(Class<?> type) {
		
		return ! Collection.class.isAssignableFrom(type);
	}

}
