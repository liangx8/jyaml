package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.dump.Presenter;
import com.rcgreed.yaml.dump.PresenterConfig;

public abstract class Node implements Tag,Presenter {
	protected abstract boolean explictTag();
	@Override
	public PresenterConfig presenterConfig() {
		return PresenterConfig.defaultPresenterConfig;
	}

}
