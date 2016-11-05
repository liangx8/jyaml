package com.rcgreed.yaml.node;

import java.util.ArrayList;
import java.util.Iterator;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.YamlWriter;
import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public abstract class MappingNode implements Node {
	protected abstract YamlIterator getData();

	@Override
	public boolean showQuestionMask() {
		return true;
	}

	private void blockStyle(YamlWriter writer) throws YamlExecption {
		if (explictTag()) {
			writer.writeText(getTag().getName());
			writer.newLine();
		}
		YamlIterator itr = getData();
		if (!itr.hasNext())
			return;
		while (true) {
			Pair<Node,Node> entry = itr.next();
			Node key = entry.first();
			boolean questionMask = key.showQuestionMask();

			if (questionMask) {
				writer.writeMappingKey();
				if (!writer.nextLineIndent()) {
					throw new RuntimeException("程序错误");
				}
			}
			key.present(writer.next());
			if (questionMask) {
				writer.newLine();
			}
			writer.writeMappingValue();
			if(questionMask&&(!writer.nextLineIndent())){
				throw new RuntimeException("程序错误");
			}
			entry.second().present(writer.next());
			if (!itr.hasNext())
				return;
			writer.lineDone();
			writer.newLine();
		}

	}

	public abstract void put(Node key, Node value);

	private boolean explictTag() {
		PresenterConfig cfg = presenterConfig();
		if (cfg.tagMode() == PresenterConfig.TAG_HIDDEN)
			return false;
		if (cfg.tagMode() == PresenterConfig.TAG_SHOW)
			return true;
		return getTag() != Tag.MapTag;
	}

	private void flowStyle(YamlWriter w) throws YamlExecption {
	}

	@Override
	public void present(YamlWriter writer) throws YamlExecption {
		if (presenterConfig().getFlowStyle() == PresenterConfig.FLOW_STYLE_BLOCK) {
			blockStyle(writer);
			return;
		}
		flowStyle(writer);
	}

	public static MappingNode newInstance(final PresenterConfig cfg) {
		final ArrayList<Pair<Node,Node>> list=new ArrayList<>();
		return new MappingNode() {

			@Override
			public PresenterConfig presenterConfig() {
				return cfg;
			}

			@Override
			public Tag getTag() {
				return Tag.MapTag;
			}

			@Override
			protected YamlIterator getData() {
				final Iterator<Pair<Node,Node>> itr=list.iterator();
				return new YamlIterator() {
					
					@Override
					public Pair<Node, Node> next() throws YamlExecption {
						return itr.next();
					}
					
					@Override
					public boolean hasNext() {
						return itr.hasNext();
					}
				};
			}

			@Override
			public void put(Node key, Node value) {
				list.add(Helper.newPair(key, value));
				
			}
		};
	}
	public static interface YamlIterator{
		boolean hasNext();
		Pair<Node,Node> next() throws YamlExecption;
	}
}
