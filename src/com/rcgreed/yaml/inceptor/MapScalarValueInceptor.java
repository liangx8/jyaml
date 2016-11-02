package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface MapScalarValueInceptor {
	String incept(Class<?> targetClz,Object key,Class<?> clz,Object value) throws YamlExecption;
}
