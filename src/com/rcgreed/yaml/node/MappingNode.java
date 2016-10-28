package com.rcgreed.yaml.node;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class MappingNode extends Node {
	public static String MapTag="!!map";
	
	@Override
	public String getName() {
		return MapTag;
	}
	@Override
	public Kind kind() {
		return Kind.Mapping;
	}
	@Override
	public boolean showQuestionMask() {
		return true;
	}
	private void blockStyle(YamlWriter writer) throws YamlExecption{
		if(explictTag())
			writer.writeText(getName());
		if(!writer.nextLineIndent()){
			writer.lineDone();
			writer.newLine();
		}
		Iterator<Map.Entry<Node, Node> >itr = getEntries().iterator();
		if(!itr.hasNext()) return;
		while(true){
			Map.Entry<Node, Node> entry=itr.next();
			Node key=entry.getKey();
			boolean questionMask=key.showQuestionMask();
			if(key.kind()==Tag.Kind.Scalar){
				if(questionMask){
					writer.writeMappingKey();
				}
				key.present(writer.next());
				if(questionMask){
					writer.newLine();
				}
				writer.writeMappingValue();
				entry.getValue().present(writer.next());
				if(!itr.hasNext())return;
				writer.lineDone();
				writer.newLine();
				continue;
			} 
			throw new UnsupportedOperationException("Unsupproted non-scalar kind as key field in mapping context");
		}
		
	}
	private void flowStyle(YamlWriter w) throws YamlExecption{
	}
	@Override
	public void present(YamlWriter writer) throws YamlExecption{
		if(presenterConfig().getFlowStyle()==PresenterConfig.FLOW_STYLE_BLOCK){
			blockStyle(writer);
			return;
		}
		flowStyle(writer);
	}
	@Override
	public boolean explictTag() {
		if(presenterConfig().tagMode()==PresenterConfig.TAG_SHOW) return true;
		if(presenterConfig().tagMode()==PresenterConfig.TAG_AUTO){
			return getName() != MapTag;
		}
		return false;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder(getName());
		sb.append(' ');
		for(Map.Entry<Node, Node> entry: getEntries()){
			sb.append(entry.getKey().toString());
			sb.append(": ");
			sb.append(entry.getValue().toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	protected abstract Set<Map.Entry<Node, Node> > getEntries(); 
	
}
