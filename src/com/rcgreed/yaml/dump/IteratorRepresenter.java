package com.rcgreed.yaml.dump;

import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public class IteratorRepresenter extends AbstractSequenceRepresenter {

	@Override
	protected boolean pass(Class<?> targetClass) {
		return !Iterator.class.isAssignableFrom(targetClass);
	}

	@Override
	protected Iterator<Pair<Class<?>, Object>> getDate(Class<?> arrayClass, Object array) {
		return new Iterator<Pair<Class<?>,Object>>() {
			
			@Override
			public Pair<Class<?>, Object> next() {
				Object v=((Iterator<?>)array).next();
				return Helper.newPair(v.getClass(), v);
			}
			
			@Override
			public boolean hasNext() {
				return ((Iterator<?>)array).hasNext();
			}
		};
	}

	@Override
	protected Tag getTag(Class<?> clz) {
		return Tag.SeqTag;
	}


}
