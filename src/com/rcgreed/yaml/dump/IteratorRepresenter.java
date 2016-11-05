package com.rcgreed.yaml.dump;

import java.util.Iterator;

import com.rcgreed.yaml.Tag;

public class IteratorRepresenter extends AbstractSequenceRepresenter {

	@Override
	protected boolean pass(Class<?> targetClass) {
		return !Iterator.class.isAssignableFrom(targetClass);
	}

	@Override
	protected Iterator<ClazzValue> getData(final ClazzValue cv) {
		return new Iterator<ClazzValue>() {
			
			@Override
			public ClazzValue next() {
				final Object v=((Iterator<?>)cv.second()).next();
				return new ClazzValue(v.getClass(),v);
			}
			
			@Override
			public boolean hasNext() {
				return ((Iterator<?>)cv.second()).hasNext();
			}
		};
	}

	@Override
	protected Tag getTag(Class<?> clz) {
		return Tag.SeqTag;
	}
	

}
