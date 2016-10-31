package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.dump.PresenterConfig;

public interface PresenterConfigInceptor {
	PresenterConfig getConfig(Class<?> clz);
}
