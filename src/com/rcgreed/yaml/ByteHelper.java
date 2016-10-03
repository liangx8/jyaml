package com.rcgreed.yaml;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ByteHelper {
	final private ByteArrayOutputStream out;

	public ByteHelper() {
		out = new ByteArrayOutputStream();

	}

	public byte[] fromLong(long value) {
		out.reset();
		PrintWriter printer = new PrintWriter(out);
		printer.print(value);
		printer.close();
		return out.toByteArray();
	}

	public byte[] fromDouble(double value) {
		out.reset();
		PrintWriter printer = new PrintWriter(out);
		printer.print(value);
		printer.close();
		return out.toByteArray();
	}

	public byte[] fromString(String value) {
		out.reset();
		PrintWriter printer = new PrintWriter(out);
		printer.print(value);
		printer.close();
		return out.toByteArray();
	}

	public byte[] fromBoolean(boolean value) {
		out.reset();
		PrintWriter printer = new PrintWriter(out);
		printer.print(value);
		printer.close();
		return out.toByteArray();
	}
	public byte[] printf(String fmt,Object... objects){
		out.reset();
		PrintWriter printer = new PrintWriter(out);
		printer.printf(fmt, objects);
		printer.close();
		return out.toByteArray();
	}

	public static byte[] space(int count) {
		byte[] sp = new byte[count];
		for (int i = 0; i < count; i++) {
			sp[i] = ' ';
		}
		return sp;
	}

	public static List<byte[]> multipleLine(byte[] str) {
		ArrayList<byte[]> lines=new ArrayList<>();
		int start=0;
		int idx;
		for(idx=0;idx<str.length;idx++){
			if(str[idx]=='\n'){
				lines.add(slice(str,start,idx));
				start=idx+1;
			}
		}
		lines.add(slice(str,start,idx));
		return lines;
	}
	public static boolean isMultipleLine(byte[] str){
		for(byte b:str){
			if (b=='\n') return true;
		}
		return false;
	}

	public static byte[] slice(byte[] buf, int start, int end) {
		if (start == end) {
			return new byte[0];
		}
		byte[] res = new byte[end - start];
		for (int i = start; i < end; i++) {
			res[i - start] = buf[i];
		}
		return res;
	}
}
