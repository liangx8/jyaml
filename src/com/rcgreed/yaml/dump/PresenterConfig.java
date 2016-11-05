package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.node.Node;

/**
 * a configuration for individual {@link Node}
 * 
 * @author arm
 *
 */
public interface PresenterConfig {
	public static int FLOW_STYLE_BLOCK = 1;
	public static int FLOW_STYLE_FLOW  = 2;
	public static int TAG_SHOW         = 3;
	public static int TAG_HIDDEN       = 4;
	public static int TAG_AUTO         = 5;

	public int tagMode();

	public int getFlowStyle();
	public static class Builder{
	public static PresenterConfig newPresenterConfig(final int tm,final int fs) {
		return new PresenterConfig() {

			@Override
			public int tagMode() {
				return tm;
			}

			@Override
			public int getFlowStyle() {
				return fs;
			}
		};
	}
	
	public static PresenterConfig defaultPresenterConfig =
			newPresenterConfig(TAG_AUTO, FLOW_STYLE_BLOCK);
	}
}
