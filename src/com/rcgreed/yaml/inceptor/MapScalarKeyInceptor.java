package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface MapScalarKeyInceptor {
	/**
	 * 键名转换,只有当键类型是Scalar时候触发此事件
	 * @param targetClz
	 * @param mapKey
	 * @return
	 * @throws YamlExecption
	 */
	String incept(Class<?> targetClz,Object mapKey) throws YamlExecption;
}

