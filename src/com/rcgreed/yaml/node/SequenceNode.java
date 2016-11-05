package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;

public abstract class SequenceNode implements Node{
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
		}
		if(!w.nextLineIndent()){
			w.newLine();
		}
		YamlIterator itr = iterator();
		while (itr.hasNext()) {
			w.writeSeqEntry();
			if (!w.nextLineIndent()) {
				throw new RuntimeException("代码有错");
			}
			Node n = itr.next();
			n.present(w.next());
			if(itr.hasNext()){
				w.lineDone();
				w.newLine();
			}
		}
	}

	private boolean explictTag() {
		PresenterConfig cfg=presenterConfig();
		if(cfg.tagMode()==PresenterConfig.TAG_HIDDEN)
			return false;
		if(cfg.tagMode()==PresenterConfig.TAG_SHOW) return true;
		return getTag()!=Tag.SeqTag;
	}

	@Override
	public boolean showQuestionMask() {
		return true;
	}
	protected abstract YamlIterator iterator();
	public static interface YamlIterator {
		boolean hasNext();
		Node next() throws YamlExecption;
	}
}
