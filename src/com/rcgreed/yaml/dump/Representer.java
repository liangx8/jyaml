package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.node.Node;

public interface Representer {
	Node represent(Object obj,RootRepresenter root) throws YamlExecption;
}
