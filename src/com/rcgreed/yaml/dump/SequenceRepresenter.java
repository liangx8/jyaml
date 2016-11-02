package com.rcgreed.yaml.dump;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public class SequenceRepresenter extends AbstractSequenceRepresenter {
	@Override
	protected boolean pass(Class<?> targetClass) {
		return !targetClass.isArray();
	}

	@Override
	protected Iterator<Pair<Class<?>, Object>> getDate(Class<?> arrayClass, Object array) {
		final Class<?> comType=arrayClass.getComponentType();
		final int total=Array.getLength(array);
		return new Iterator<Pair<Class<?>,Object>>() {
			int idx=0;
			@Override
			public Pair<Class<?>, Object> next() {
				Object v=Array.get(array, idx);
				idx++;
				if(comType==Object.class){
					return Helper.newPair(v.getClass(), v);
				}
				return Helper.newPair(comType, v);
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
