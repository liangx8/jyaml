package com.rcgreed.yaml.loader;

public class Context {
	final private int indent;
	final private Type type;
	final private YamlReader reader;
	public Context(YamlReader r) {
		type=Type.STREAM;
		reader=r;
		indent=r.nextNoneSpace();
	}
	private Context(int ind,Type t,YamlReader r){
		indent=ind;
		type=t;
		reader=r;
	}
	public Context child(){
		
	}
	public static enum Type{
		STREAM,
		DOCUMENT,
		MAPPING,
		SEQUENCE,
		SCALAR,
	}

}
