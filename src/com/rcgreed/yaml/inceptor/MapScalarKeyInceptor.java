package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.YamlExecption;

public interface MapScalarKeyInceptor {
	/**
	 * 键名转换,只有当键类型是Scalar时候触发此事件
	 * @param targetClz target class
	 * @param mapKey the map key of target class
	 * @return
	 * @throws YamlExecption
	 */
	String incept(Class<?> targetClz,Object mapKey) throws YamlExecption;
}

