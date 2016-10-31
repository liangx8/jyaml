package com.rcgreed.yaml.dump;

import java.lang.reflect.Field;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.Node;

public class MappingRepresenter implements Representer {

	@Override
	public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
		Tag tag=ctx.incept(clz);
		if(tag.kind()!= Tag.Kind.Mapping)return null;
		MappingNode mnode=new MappingNode() {
			
			@Override
			public PresenterConfig presenterConfig() {
				throw new RuntimeException("未搞定");
			}
			
			@Override
			public Tag getTag() {
				return tag;
			}
		};
		Field fds[]=clz.getFields();
		for(Field fd:fds){
			String fname=ctx.incept(clz, fd.getName());
			Node key=base.represent(String.class, fname, ctx, base);
		}
		return mnode;
	}

}
