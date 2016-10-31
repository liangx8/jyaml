package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class ScalarNode implements Node {
	@Override
	public void present(YamlWriter writer){
	}
	@Override
	public boolean showQuestionMask() {
		return true;
	}
	public abstract String getValue();
	@Override
	public String toString() {
		return getValue();
	}
	public static ScalarNode newInstance(final Tag tag,final String value){
		if(tag.kind()!= Tag.Kind.Scalar){
			throw new RuntimeException("wrong logical");
		}
		return new ScalarNode(){

			@Override
			public Tag getTag() {
				return tag;
			}

			@Override
			public PresenterConfig presenterConfig() {
				throw new RuntimeException("未搞定");
			}

			@Override
			public String getValue() {
				return value;
			}
			
		};
	}
	
}
