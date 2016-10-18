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
	public YamlExecption(long lineNumber,String message){
		super(String.format("Parse input error at line %d, message: %s", lineNumber,message));
	}
}
