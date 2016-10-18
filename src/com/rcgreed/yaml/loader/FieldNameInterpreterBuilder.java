package com.rcgreed.yaml.loader;

import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FieldNameInterpreterBuilder {
	public static FieldNameInterpreter mapFieldNameInterpreter(final Map<String,String> map){
		final HashMap<String,String> demap=new HashMap<>();
		Iterator<Map.Entry<String, String>> itr=map.entrySet().iterator();
		if(itr.hasNext()){
			Map.Entry<String, String> entry=itr.next();
			demap.put(entry.getValue(), entry.getKey());
		}
		return new FieldNameInterpreter() {
			
			@Override
			public String interpret(String name) {
				return map.get(name);
			}
			
			@Override
			public String deinterpret(String name) {
				return demap.get(name);
			}
		};
	}
	public static FieldNameInterpreter hyphenFieldNameInterpreter(){
		return new FieldNameInterpreter() {
			
			@Override
			public String interpret(String name) {
				CharArrayWriter out=new CharArrayWriter();
				for(int i=0;i<name.length();i++){
					int c=name.charAt(i);
					if(Character.isUpperCase(c)){
						if(i>0){
							out.write('-');
						}
						out.write(Character.toLowerCase(c));
						continue;
					}
					out.write(c);
				}
				out.close();
				return out.toString();
			}
			
			@Override
			public String deinterpret(String name) {
				CharArrayWriter out=new CharArrayWriter();
				boolean isHyphen=false;
				for(int i=0;i<name.length();i++){
					int c=name.charAt(i);
					if(c=='-'){
						isHyphen=true;
						continue;
					}
					if(isHyphen){
						out.write(Character.toUpperCase(c));
						isHyphen=false;
					} else {
						out.write(c);
					}
				}
				return out.toString();
			}
		};
	}
	public static FieldNameInterpreter defaultFieldNameInterpreter(){
		return new FieldNameInterpreter() {
			
			@Override
			public String interpret(String name) {
				return name;
			}
			
			@Override
			public String deinterpret(String name) {
				return name;
			}
		};
	}
}
