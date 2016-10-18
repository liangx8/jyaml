package com.rcgreed.yaml.loader;
/**
 * Describe line in document
 * @author arm
 *
 */
public class Line {
	final public byte[] line;
	final public long lineNumber;
	public boolean done;
	public int position;
	/**
	 * Describe line in document
	 * @param line content
	 * @param lineNumber line number
	 */
	public Line(byte[] line, long lineNumber) {
		this.line = line;
		this.lineNumber = lineNumber;
		position=0;
		done = false;
	}
	
}