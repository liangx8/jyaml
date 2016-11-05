package com.rcgreed.yaml.dump;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.rcgreed.yaml.Tag;

public class SequenceRepresenter extends AbstractSequenceRepresenter {
	@Override
	protected boolean pass(Class<?> targetClass) {
		return !targetClass.isArray();
	}

	@Override
	protected Iterator<ClazzValue> getData(ClazzValue carray) {
		final Class<?> comType=carray.first().getComponentType();
		final int total=Array.getLength(carray.second());
		final Object array=carray.second();
		return new Iterator<ClazzValue>() {
			int idx=0;
			@Override
			public ClazzValue next() {
				final Object v=Array.get(array, idx);
				idx++;
				if(comType==Object.class){
					return new ClazzValue(v.getClass(),v);
				}
				return new ClazzValue(comType,v);
			}
			
			@Override
			public boolean hasNext() {
				return idx<total;
			}
		};
	}

	@Override
	protected Tag getTag(Class<?> clz) {
		return Tag.SeqTag;
	}

}
