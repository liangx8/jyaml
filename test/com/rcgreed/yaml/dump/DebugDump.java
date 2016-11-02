package com.rcgreed.yaml.dump;

import java.io.IOException;
import java.util.Date;

import com.rcgreed.yaml.Definition;
import com.rcgreed.yaml.EntityUtils;
import com.rcgreed.yaml.Expense;
import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.SequenceScalarValueInterceptor;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;
import com.rcgreed.yaml.node.SequenceNode;

public class DebugDump {

	public static void main(String[] args) throws YamlExecption, IOException {
		RepresenterAny any = new RepresenterAny();
		Context ctx = new Context();
		ctx.addSequenceScalarInceptor(new SequenceScalarValueInterceptor() {

			@Override
			public String incept(Class<?> targetClz, Class<?> clz, Object seqEntry) throws YamlExecption {
				if (clz == Date.class)
					return Definition.dateFormat.format(seqEntry);
				if (clz == Float.class) {
					return String.format("%.2f", (Float) seqEntry);
				}
				if (clz == float.class) {
					return String.format("%.4f", (Float) seqEntry);
				}
				return null;
			}
		});
		Object o[] = new Object[4];
		for (int i = 0; i < 3; i++) {
			o[i] = EntityUtils.sampleExpense();
		}
		o[3] = new Date();
		Node n = any.represent(o.getClass(), o, ctx, null);

		DumpConfig cfg = new DumpConfig();
		cfg.indent = 3;
		YamlWriter w = cfg.newWriter(System.out);
		n.present(w);
		w.close();
	}

	private static MappingNode sampleMap() {
		MappingNode n = new MappingNode() {

			@Override
			public PresenterConfig presenterConfig() {
				return PresenterConfig.defaultPresenterConfig;
			}

			@Override
			public Tag getTag() {
				return Tag.MapTag;
			}
		};
		n.put(ScalarNode.newInstance(Tag.StrTag, "  多行键值\n填充用\n填充\n填充\n填充\n填充",
				PresenterConfig.newPresenterConfig(PresenterConfig.TAG_SHOW, 0)),
				ScalarNode.newInstance(Tag.IntTag, "12", PresenterConfig.defaultPresenterConfig));
		return n;
	}

}
