package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.node.ScalarNode;

public interface MapScalarValueInceptor {
	String incept(Class<?> targetClz,ScalarNode key,Class<?> clz,Object value) throws YamlExecption;
}
