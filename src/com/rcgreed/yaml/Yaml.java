package com.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;

import com.rcgreed.yaml.dump.DumpConfig;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.Representer;
import com.rcgreed.yaml.dump.RepresenterAny;
import com.rcgreed.yaml.dump.YamlWriter;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.MapScalarKeyInceptor;
import com.rcgreed.yaml.inceptor.TagInceptor;
import com.rcgreed.yaml.node.Node;

public class Yaml {
	final private DumpConfig dumpConfig = new DumpConfig();
	final private Context ctx = new Context();
	final private Representer any = new RepresenterAny();

	public void dump(OutputStream out, Object target) throws YamlExecption {
		YamlWriter w = dumpConfig.newWriter(out);
		Node n = any.represent(target.getClass(), target, ctx, null);
		n.present(w);
		try {
			w.close();
		} catch (IOException e) {
			throw new YamlExecption(e);
		}

	}

	public Yaml() {
		dumpConfig.indent = 2;
	}

	public void mapToTag(Class<?> target, String name) {
		ctx.addTagInceptor(new TagInceptor() {

			@Override
			public Tag incept(Class<?> clz, Tag org) throws YamlExecption {
				if (target == clz) {
					return new Tag() {

						@Override
						public Kind kind() {
							return org.kind();
						}

						@Override
						public String getName() {
							return "!" + name;
						}
					};
				}
				return null;
			}
		});
	}
	public void fieldNameMapping(Class<?> clz,String name,String mapTo){
		ctx.addMapScalarKeyInceptor(new MapScalarKeyInceptor() {
			
			@Override
			public String incept(Class<?> targetClz, Object mapKey) throws YamlExecption {
				if(targetClz==clz && mapTo.equals(mapTo)){
					return name;
				}
				return null;
			}
		});
	}

	public void tagHide(Class<?> target) {
		ctx.putPresenterConfigInceptor(target,
				PresenterConfig.newPresenterConfig(PresenterConfig.TAG_HIDDEN, PresenterConfig.FLOW_STYLE_BLOCK));
	}
}
