package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class ScalarNode implements Node {
	private String ss[]=null;
	@Override
	public void present(YamlWriter writer){
		if(presenterConfig().tagMode()==PresenterConfig.TAG_SHOW){
			writer.writeText(getTag().getName());
			writer.write(' ');
		}
		if(ss==null){
			String sv=getValue();
			if (sv==null){
				ss=new String[0];
			} else
				ss=sv.split("\n");
		}
		if(ss.length==0) return;
		if(ss.length==1){
			writer.writeText(ss[0]);
			return;
		}
		writer.write('|');
		writer.writeIndentNumber();
		for(String s:ss){
			writer.newLine();
			writer.writeText(s);
		}
	}
	@Override
	public boolean showQuestionMask() {
		if(ss==null){
			String sv=getValue();
			if (sv==null){
				ss=new String[0];
			} else
				ss=sv.split("\n");
		}
		if(ss.length>1) return true;
		return presenterConfig().tagMode()==PresenterConfig.TAG_SHOW;
	}
	public abstract String getValue();
	@Override
	public String toString() {
		return getValue();
	}
	public static ScalarNode newInstance(final Tag tag,final String value,final PresenterConfig cfg){
		if(tag.kind()!= Tag.Kind.Scalar){
			throw new RuntimeException("wrong logical"+tag.getName());
		}
		return new ScalarNode(){

			@Override
			public Tag getTag() {
				return tag;
			}

			@Override
			public PresenterConfig presenterConfig() {
				return cfg;
			}

			@Override
			public String getValue() {
				return value;
			}
			
		};
	}
	
}
