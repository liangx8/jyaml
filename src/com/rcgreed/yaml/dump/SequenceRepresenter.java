package com.rcgreed.yaml.dump;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public class SequenceRepresenter extends AbstractSequenceRepresenter {


	@Override
	protected boolean pass(Class<?> type) {
		return ! type.isArray();
	}

	@Override
	protected Iterator<Pair<Class<?>, Object>> iterate(Object obj) {
		final int length=Array.getLength(obj);
		Class<?> type=obj.getClass();
		final Class<?> cType=type.getComponentType();
		return new Iterator<Pair<Class<?>,Object>>() {
			private int idx=0;
			
			@Override
			public Pair<Class<?>, Object> next() {
				Object cObj=Array.get(obj, idx);
				idx ++;
				if(cObj==null)
					return Helper.newPair(cType, (Object)null);
				return Helper.newPair(
						cType==Object.class?cObj.getClass():cType,
						cObj);
			}
			
			@Override
			public boolean hasNext() {
				return idx < length;
			}
		};
	}
}
