package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface SequenceScalarInterceptor {
	String incept(Class<?> targetClz,Class<?> clz,Object seqEntry) throws YamlExecption;
}
