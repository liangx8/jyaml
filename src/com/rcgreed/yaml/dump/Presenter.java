package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.YamlExecption;

public interface Presenter {
	void present(YamlWriter writer) throws YamlExecption;
	/**
	 * Does '?' show when as key field?  
	 * @return
	 */
	boolean showQuestionMask();
	PresenterConfig presenterConfig();
}
