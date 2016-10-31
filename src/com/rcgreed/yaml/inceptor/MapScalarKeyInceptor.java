package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface MapScalarKeyInceptor {
	String incept(Class<?> targetClz,Object mapKey) throws YamlExecption;
}

