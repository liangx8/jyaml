package com.rcgreed.yaml.loader;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.rcgreed.yaml.YamlExecption;

public class ByteParser {
	public static class Range {
		final public int start,end;

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
	}
	public static int leadSpace(byte[]line,Range range){
		for(int i=range.start;i<range.end;i++){
			if(line[i]!=' '){
				return i;
			}
		}
		return -1;
	}
	public static int trimComment(byte[]line){
		byte c=0;
		boolean quota=false;
		for(int i=0;i<line.length;i++){
			if(quota){
				if(c==line[i]){
					quota=false;
				}
			} else {
				c=line[i];
				if(c=='"'||c=='\''){
					quota=true;
					continue;
				}
				if(c=='#'){
					return i;
				}
			}
		}
		return line.length;
	}
	public static byte[] slice(byte[]line,Range r){
		return Arrays.copyOfRange(line, r.start, r.end);
	}
	public static int tailSpace(byte[] line,Range r){
		for(int i=r.end-1;i>=r.start;i--){
			if(line[i]==' ') continue;
			return i+1;
		}
		return r.start;
	}
	public static boolean isEmptyLine(byte[] line,int start){
		int i=start;
		int end=line.length;
		for(;i<end;i++){
			if(line[i]!=' '){
				break;
			}
		}
		if(i==end){
			return true;
		}
		if(line[i]=='#'){
			return true;
		}
		return false;
	}
	public static String parseMapKey(Line line) throws YamlExecption{
		int start=line.position;
		int idx;
		byte c=line.line[start];
		// 读取的 Line 如果不是字符串的scalar,都会吧 '#'后面的内容丢弃
		int end=line.line.length;
		//int end= trimComment(line.line);
		if(c=='"'||c=='\''){
			int pos;
			start ++;
			idx=start;
			for (;idx<end;idx++){
				if(line.line[idx]==c){
					break;
				}
			}
			if(idx==end){
				throw new YamlExecption(line.lineNumber,String.format("expected key name at line %d", line.lineNumber));
			}
			pos=idx;
			idx++;
			// expecting ' ' or ':' here
			for(;idx<end;idx++){
				if(line.line[idx]==' '){
					continue;
				}
				if(line.line[idx]==':'){
					break;
				}
				throw new YamlExecption(line.lineNumber,String.format("unexpected char %c", line.line[idx]));
			}
			if(idx==end){
				throw new YamlExecption(line.lineNumber,"expected `:'");
			}
			idx++;
			if(idx<end){
				if (line.line[idx]!=' '){
					throw new YamlExecption(line.lineNumber,"a space should follow ':'");
				}
			}
			if(idx == end){
				line.done=true;
			} else {
				line.position=idx;
			}
			return new String(slice(line.line,new Range(start,pos)),utf8);
		} else {
			idx=start;
			for(;idx<end;idx++){
				c=line.line[idx];
				if(c=='"' || c=='\''){
					throw new YamlExecption(line.lineNumber,"unexpected a quota char here (`'` or `\"`)");
				}
				if(c == ':') {
					break;
				}
			}
			if(idx==end){
				throw new YamlExecption(line.lineNumber,"expected `:'");
			}
			int pos=idx;
			idx++;
			if(idx<end){
				if (line.line[idx]!=' '){
					throw new YamlExecption(line.lineNumber,"a space should follow ':'");
				}
			}
			if(idx == end){
				line.done=true;
			} else {
				line.position=idx;
			}
			return new String(slice(line.line,new Range(start,tailSpace(line.line, new Range(start,pos)))));
		}
	}
	final static Charset utf8=Charset.forName("UTF-8");
}
