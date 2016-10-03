package com.rcgreed.yaml;

public abstract class YamlObject implements Dumper{
	final private String tagName;
	private boolean tagShow;
	
	public YamlObject(String tagName) {
		super();
		this.tagName = tagName;
		tagShow=false;
	}

	protected String getTagName(){
		return tagName;
	}
	protected boolean isTagShow(){
		return tagShow;
	}
	public YamlObject setTagShow(boolean v){
		tagShow=v;
		return this;
	}
	
	public static final String TYPE_SEQUENCE = "seq";
	public static final String TYPE_MAPPING  = "map";
	public static final String TYPE_INT      = "int";
	public static final String TYPE_STRING   = "str";
	public static final String TYPE_FLOAT    = "float";
	public static final String TYPE_BOOLEAN  = "bool";
	public static final String TYPE_BINARY   = "binary";
}
