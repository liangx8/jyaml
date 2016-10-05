package com.rcgreed.yaml;

import java.util.Collection;
import java.util.Iterator;

public class CollectionConversion implements Conversion {
	@Override
	public YamlObject convert(Object obj,Conversion parent) throws YamlExecption {
		if (obj instanceof Collection){
			Collection<?> c=(Collection<?>) obj;
			YamlSequence ys=new YamlSequence(obj.getClass().getName());
			Iterator<?> itr=c.iterator();
			while(itr.hasNext()){
				Object o=itr.next();
				if(o==null){
					ys.add(YamlScalar.NullScalar);
				} else {
					ys.add(parent.convert(o,null));
				}
			}
			return ys;
		}
		return null;
	}

}
