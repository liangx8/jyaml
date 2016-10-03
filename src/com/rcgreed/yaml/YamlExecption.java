package com.rcgreed.yaml;

public class YamlExecption extends Throwable {
	/**
	 * Eliminate IDE complaint
	 */
	private static final long serialVersionUID = 1L;

	public YamlExecption(Throwable e) {
		super(e);
	}

	public YamlExecption(String message) {
		super(message);
	}
}
