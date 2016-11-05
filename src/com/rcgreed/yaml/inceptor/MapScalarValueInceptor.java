package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.ClazzValue;

public interface MapScalarValueInceptor {
	String incept(Class<?> targetClz,Object key,ClazzValue value) throws YamlExecption;
}
