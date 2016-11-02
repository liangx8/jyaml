package com.rcgreed.yaml.dump;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;

public class MappingRepresenter implements Representer {

	@Override
	public Node represent(Class<?> clz, Object obj, Context ctx, Representer base) throws YamlExecption {
		if (Map.class.isAssignableFrom(clz))
			return null;
		Field fds[] = clz.getFields();
		if (fds.length == 0)
			return null;
		Tag tag = new Tag() {

			@Override
			public Kind kind() {
				return Tag.Kind.Mapping;
			}

			@Override
			public String getName() {
				return "!" + clz.getName();
			}
		};
		final Tag ftag = ctx.incept(clz, tag);
		if (ftag.kind() != Tag.Kind.Mapping) {
			throw new YamlExecption("difference kind of Node");
		}
		MappingNode mnode = new MappingNode() {

			@Override
			public PresenterConfig presenterConfig() {
				return ctx.getConfig(clz);
			}

			@Override
			public Tag getTag() {
				return ftag;
			}
		};
		try {
			for (Field fd : fds) {
				String skey = fd.getName();
				String fname = ctx.incept(clz, skey);
				Node key = ScalarNode.newInstance(Tag.StrTag, fname, ctx.getConfig(null));
				Class<?> subTy = fd.getType();
				Tag tval = null;
				if (subTy == int.class || subTy == short.class || subTy == long.class || subTy == Integer.class
						|| subTy == Short.class || subTy == Long.class)
					tval = Tag.IntTag;
				if (subTy == float.class || subTy == double.class || subTy == Float.class || subTy == Double.class)
					tval = Tag.FloatTag;
				if (subTy == boolean.class || subTy == Boolean.class)
					tval = Tag.BoolTag;
				if (subTy == String.class) {
					tval = Tag.StrTag;
				}
				if (subTy == Date.class) {
					tval = Tag.DateTag;
				}
				if (tval != null) {
					String value = ctx.incept(clz, skey, subTy, fd.get(obj));
					Node nval = ScalarNode.newInstance(tval, value,ctx.getConfig(null));
					mnode.put(key, nval);
					continue;
				}
			}
			return mnode;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new YamlExecption(e);
		}
	}
}