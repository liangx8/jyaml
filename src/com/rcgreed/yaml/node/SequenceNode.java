package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;
import com.rcgreed.yaml.utils.YamlIterator;

public abstract class SequenceNode extends Node {
	public static String SeqTag = "!!seq";

	@Override
	public String getName() {
		return SeqTag;
	}

	@Override
	public Kind kind() {
		return Kind.Sequence;
	}

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
			w.writeText(getName());
		}
		if (!w.nextLineIndent()) {
			w.lineDone();
			w.newLine();
		}
		YamlIterator<Node> itr = iterateNode();
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

	@Override
	public boolean explictTag() {
		if (presenterConfig().tagMode() == PresenterConfig.TAG_SHOW)
			return true;
		if (presenterConfig().tagMode() == PresenterConfig.TAG_AUTO) {
			return getName() != SeqTag;
		}
		return false;
	}

	@Override
	public boolean showQuestionMask() {
		return true;
	}

	@Override
	public String toString() {
		boolean first = true;
		YamlIterator<Node> itr = iterateNode();
		try {
			StringBuilder sb = new StringBuilder(getName());
			sb.append('[');
			while (itr.hasNext()) {
				if (first) {
					first = false;
				} else {
					sb.append(",\n");
				}
				sb.append(itr.next().toString());
			}
			sb.append(']');
			return sb.toString();
		} catch (YamlExecption e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract YamlIterator<Node> iterateNode();
}
