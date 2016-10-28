package com.rcgreed.yaml.node;

import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class ScalarNode extends Node {
	public static String TAG_INT    = "!!int";
	public static String TAG_FLOAT  = "!!float";
	public static String TAG_BOOL   = "!!bool";
	public static String TAG_STR    = "!!str";
	public static String TAG_BINARY = "!!binary";
	public static String TAG_DATE   = "!date";
	public static String TAG_NULL   = "!null";
	private static String SCALAR_TAGS[]={TAG_INT,TAG_FLOAT,TAG_BOOL,TAG_STR,TAG_BINARY,TAG_DATE,TAG_NULL};

	@Override
	public Kind kind() {
		return Kind.Scalar;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder(getName());
		sb.append(' ');
		sb.append(getValue());
		return sb.toString();
	}
	// Presenter implements
	private String strs[]=null;
	@Override
	public void present(YamlWriter writer){
		if(explictTag())
			writer.writeText(getName());
		if(strs==null){
			strs=getValue().split("\n");
		}
		if(strs.length==1){
			writer.writeText(strs[0]);
			return;
		}
		if(!writer.nextLineIndent()){
			writer.lineDone();
			writer.newLine();
		}
		writer.writeText("|");
		writer.writeIndentNumber();
		writer.lineDone();

		
		for(int i=0;i<strs.length;i++){
			writer.newLine();
			writer.nextLineIndent();
			writer.writeText(strs[i]);
			writer.lineDone();
		}
	}
	@Override
	public boolean explictTag(){
		PresenterConfig cfg=presenterConfig();
		if(cfg.tagMode()==PresenterConfig.TAG_SHOW) return true;
		if(cfg.tagMode()==PresenterConfig.TAG_AUTO){
			String tag=getName();
			for(int i=0;i<SCALAR_TAGS.length;i++){
				if(tag==SCALAR_TAGS[i]) return false;
			}
			
		}
		return false;
	}
	@Override
	public boolean showQuestionMask() {
		if (strs==null){
			strs=getValue().split("\n");
		}
		if(strs.length>1) return true;
		// 作为 key显示时,如果tag被显示,就必须同时显示 '?'
		return explictTag();
	}
	
	protected abstract String getValue();
}
