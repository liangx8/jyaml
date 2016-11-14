package com.rcgreed.yaml.loader;

import java.io.InputStream;

import com.rcgreed.yaml.YamlExecption;

public class YamlReader {
	public static int EOF     = -1; // end of file
	public static int EOL     = -2; // end of line
	public static int COMMENT = -3; // comment data
	private int lineNumber,col;
	//true 当还没读取数据内容时,否则 false
	// true, getCol() 的内容是有意义的
	private boolean vaildCol; 
	public YamlReader() {
		lineNumber=0;
		col=0;
		vaildCol=true;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public int getCol() {
		if(!vaildCol){
			throw new RuntimeException("col数据已经不可靠");
		}
		return col;
	}
	public boolean isVaildCol() {
		return vaildCol;
	}
	/**
	 * 
	 * @param in
	 * @throws YamlExecption
	 */
	public YamlReader(InputStream in) throws YamlExecption {
		
	}
	/**
	 * 当前的位置必须是：<br />
	 *   * 行开始 或者 ":","?","-"后面<br />
	 * @return
	 */
	public int nextNoneSpace(){
		return 0;
	}
}
