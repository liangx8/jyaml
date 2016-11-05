package com.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;

import com.rcgreed.yaml.dump.ClazzValue;
import com.rcgreed.yaml.dump.DumpConfig;
import com.rcgreed.yaml.dump.PresenterConfig;
import com.rcgreed.yaml.dump.Representer;
import com.rcgreed.yaml.dump.RepresenterAny;
import com.rcgreed.yaml.dump.YamlWriter;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.inceptor.MapScalarKeyInceptor;
import com.rcgreed.yaml.inceptor.MapScalarValueInceptor;
import com.rcgreed.yaml.inceptor.TagInceptor;
import com.rcgreed.yaml.inceptor.ToString;
import com.rcgreed.yaml.node.Node;

public class Yaml {
	final private DumpConfig dumpConfig = new DumpConfig();
	final private Context ctx = new Context();
	final private Representer any = new RepresenterAny();

	public void dump(OutputStream out, Object target) throws YamlExecption {
		YamlWriter w = dumpConfig.newWriter(out);
		Node n = any.represent(new ClazzValue(target.getClass(), target), ctx, null);
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

	public void mapToTag(final Class<?> target,final String name) {
		ctx.addTagInceptor(new TagInceptor() {

			@Override
			public Tag incept(Class<?> clz,final Tag org) throws YamlExecption {
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
	
	public void fieldNameMapping(final Class<?> clz,final String fieldName,final String mapTo){
		ctx.addMapScalarKeyInceptor(new MapScalarKeyInceptor() {
			
			@Override
			public String incept(Class<?> targetClz, Object mapKey) throws YamlExecption {
				if(targetClz==clz && mapKey.equals(fieldName)){
					return mapTo;
				}
				return null;
			}
		});
	}
	public void valueToString(final Class<?> clz,final String fieldName,final ToString ts){
		ctx.addMapScalarValueInceptor(new MapScalarValueInceptor() {
			
			@Override
			public String incept(Class<?> targetClz, Object key, ClazzValue value) throws YamlExecption {
				if(targetClz==clz && key.equals(fieldName)){
					return ts.string(value);
				}
				return null;
			}
		});
	}

	public void tagHide(Class<?> target) {
		ctx.putPresenterConfigInceptor(target,
				PresenterConfig.Builder.newPresenterConfig(PresenterConfig.TAG_HIDDEN, PresenterConfig.FLOW_STYLE_BLOCK));
	}
	
}
