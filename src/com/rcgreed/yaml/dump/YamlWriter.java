package com.rcgreed.yaml.dump;

import java.io.Closeable;

public interface YamlWriter extends Closeable{
	/**
	 * 
	 * @param cr carry return if possible
	 * @return
	 */
	YamlWriter next();
	/**
	 * 
	 * @param text do not allow including '\n' in
	 */
	void writeText(CharSequence text);
	int write(byte[] buf);
	void write(int ch);
	/**
	 * will call lineDone() to close previous line
	 */
	void newLine();
	/**
	 * 
	 * @return false current has print out some characters, can't find next indent position accurately
	 * lineDone() must be call for next indent position 
	 */
	boolean nextLineIndent();
	/**
	 * any write after this call will treat as comment
	 */
	void lineDone();
	/**
	 * for block/fold scalar indent indicate "|*"
	 */
	void writeIndentNumber();
	/**
	 * write "- "
	 */
	void writeSeqEntry();
	/**
	 * write "? "
	 */
	void writeMappingKey();
	/**
	 * write ": "
	 */
	void writeMappingValue();
	/**
	 * "{ "
	 */
	void writeStartMap();
	/**
	 * " }"
	 */
	void writeEndMap();
	/**
	 * "[ "
	 */
	void writeStartSeq();
	/**
	 * " ]"
	 */
	void writeEndSeq();
}
