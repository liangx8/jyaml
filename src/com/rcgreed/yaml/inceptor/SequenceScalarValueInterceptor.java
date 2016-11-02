package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface SequenceScalarValueInterceptor {
	String incept(Class<?> targetClz,Class<?> clz,Object seqEntry) throws YamlExecption;
}
