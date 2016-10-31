package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;

public abstract class AbstractScalarRepresenter implements Representer {

	@Override
	final public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
		return represent(clz, (String)obj, ctx);
	}
	protected abstract ScalarNode represent(Class<?>clz, String obj,Context ctx) throws YamlExecption;

}
