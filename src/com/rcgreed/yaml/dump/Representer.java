package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.Node;

public interface Representer{
	Node represent(ClazzValue valaue,Context ctx,Representer base) throws YamlExecption;
}
