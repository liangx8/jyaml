package com.rcgreed.yaml;

import java.util.Date;

public interface YamlWriter {
	/**
	 * 
	 * @param cr true indicate sub level need a carry-return if possible
	 * @return
	 */
	YamlWriter next(boolean cr);
	void tag(String tagName) throws YamlExecption;
	void write(byte[] data) throws YamlExecption;
	void write(int c) throws YamlExecption;
	void stringModifier() throws YamlExecption;
	void writeKey(String key) throws YamlExecption;
	void writeDate(Date date) throws YamlExecption;
	Indention indent(int type);
	public static interface Indention{
		void indent() throws YamlExecption;
	}
	final public static int SEQUENCE_INDENT = 1;
	final public static int MAPPING_INDENT  = 2;
	final public static int SCALAR_INDENT   = 3;
}
