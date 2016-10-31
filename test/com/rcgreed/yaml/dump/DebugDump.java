package com.rcgreed.yaml.dump;

import java.util.Date;

import com.rcgreed.yaml.Definition;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.SequenceScalarInterceptor;
import com.rcgreed.yaml.node.Node;

public class DebugDump {

	public static void main(String[] args) throws YamlExecption {
		RepresenterAny any=new RepresenterAny();
		Context ctx=new Context();
		ctx.addSequenceScalarInceptor(new SequenceScalarInterceptor() {
			
			@Override
			public String incept(Class<?> targetClz, Class<?> clz, Object seqEntry) throws YamlExecption {
				if(clz==Date.class)
					return Definition.dateFormat.format(seqEntry);
				if(clz==Float.class){
					return String.format("%.2f", (Float)seqEntry);
				}
				if(clz==float.class){
					return String.format("%.4f", (Float)seqEntry);
				}
				return seqEntry.toString();
			}
		});
		Node n=any.represent(Date.class, new Date(), ctx, null);
		System.out.println(n.toString());
		n=any.represent(Float.class,new Float( 12.0), ctx, null);
		System.out.println(n.toString());
		n=any.represent(float.class,new Float( 12.0), ctx, null);
		System.out.println(n.toString());
	}

}
