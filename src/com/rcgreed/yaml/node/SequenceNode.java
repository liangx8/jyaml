package com.rcgreed.yaml.node;

import java.util.ArrayList;
import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class SequenceNode implements Node {
	private ArrayList<Node> list=new ArrayList<>();
	@Override
	public void present(YamlWriter writer) throws YamlExecption {
		if (presenterConfig().getFlowStyle() == PresenterConfig.FLOW_STYLE_BLOCK) {
			blockStyle(writer);
			return;
		}
		flowStyle(writer);
	}

	private void flowStyle(YamlWriter writer) {
		throw new RuntimeException("not implements");
	}

	private void blockStyle(YamlWriter w) throws YamlExecption {
		if (explictTag()) {
			w.writeText(getTag().getName());
			w.write(' ');
		}
		if (!w.nextLineIndent()) {
			w.lineDone();
			w.newLine();
		}
		Iterator<Node> itr = list.iterator();
		while (itr.hasNext()) {
			w.writeSeqEntry();
			Node n = itr.next();
			n.present(w.next());
			if(itr.hasNext()){
				w.lineDone();
				w.newLine();
			}
		}
	}
	public void add(Node n){
		list.add(n);
	}
	public Node get(int idx){
		return list.get(idx);
	}
	public int size(){
		return list.size();
	}


	private boolean explictTag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showQuestionMask() {
		return true;
	}
	public static SequenceNode newInstance(){
		return new SequenceNode() {
			
			@Override
			public PresenterConfig presenterConfig() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Tag getTag() {
				return Tag.SeqTag;
			}
		};
	}
}
