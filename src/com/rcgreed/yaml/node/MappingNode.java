package com.rcgreed.yaml.node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class MappingNode implements Node {
	final private HashMap<Node,Node> data=new HashMap<>();

	@Override
	public boolean showQuestionMask() {
		return true;
	}
	private void blockStyle(YamlWriter writer) throws YamlExecption{
		if(explictTag()){
			writer.writeText(getTag().getName());
			writer.write(' ');
		}
		if(!writer.nextLineIndent()){
			writer.lineDone();
			writer.newLine();
		}
		Iterator<Map.Entry<Node, Node> >itr = data.entrySet().iterator();
		if(!itr.hasNext()) return;
		while(true){
			Map.Entry<Node, Node> entry=itr.next();
			Node key=entry.getKey();
			boolean questionMask=key.showQuestionMask();
			if(key.getTag().kind()==Tag.Kind.Scalar){
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
	public void put(Node key,Node value){
		data.put(key, value);
	}
	private boolean explictTag() {
		// TODO Auto-generated method stub
		return false;
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
	public static MappingNode newInstance(){
		return new MappingNode() {
			
			@Override
			public PresenterConfig presenterConfig() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Tag getTag() {
				return Tag.MapTag;
			}
		};
	}
}
