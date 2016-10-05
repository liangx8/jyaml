package com.rcgreed.yaml.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class YamlReader {
	final private InputStream in;
	private long lineNumber=0;

	public YamlReader(InputStream in) {
		this.in = in;
	}
	public Line readLine() throws IOException{
		ByteArrayOutputStream buf=new ByteArrayOutputStream();
		int c;
		boolean empty=true;
		while(true){
			c=in.read();
			if(c==-1){
				if (empty){
					return null;
				}
				break;
			}
			empty=false;
			if(c=='\n'){
				break;
			}
			buf.write(c);
		}
		buf.close();
		lineNumber++;
		return new Line(buf.toByteArray(),lineNumber);
	}
	public static class Line{
		final public byte[] line;
		final public long lineNumber;
		public Line(byte[] line, long lineNumber) {
			this.line = line;
			this.lineNumber = lineNumber;
		}
		
	}
}
