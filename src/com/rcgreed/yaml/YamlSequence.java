package com.rcgreed.yaml;

import java.util.ArrayList;

import com.rcgreed.yaml.YamlWriter.Indention;

public class YamlSequence extends YamlObject {
	final private ArrayList<YamlObject> data=new ArrayList<>();
	public YamlSequence(String tagName) {
		super(tagName);
	}
	public YamlSequence(){
		super(TYPE_SEQUENCE);
	}
	@Override
	public void dump(YamlWriter writer) throws YamlExecption {
		if(isTagShow()){
			writer.tag(getTagName());
		}
		Indention ind=writer.indent(YamlWriter.SEQUENCE_INDENT);
		for(YamlObject obj:data){
			ind.indent();
			obj.dump(writer.next(false));
		}
	}
	public void add(YamlObject obj){
		data.add(obj);
	}
	public void addBoolean(boolean v){
		data.add(new YamlScalar(v));
	}
	public void addDouble(double v){
		data.add(new YamlScalar(v));
	}
	public void addLong(long v){
		data.add(new YamlScalar(v));
	}
	public void addString(String v){
		data.add(new YamlScalar(v));
	}


}
