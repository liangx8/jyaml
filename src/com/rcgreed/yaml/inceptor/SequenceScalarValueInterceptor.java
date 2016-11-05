package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.ClazzValue;

public interface SequenceScalarValueInterceptor {
	String incept(Class<?> targetClz,ClazzValue value) throws YamlExecption;
}
