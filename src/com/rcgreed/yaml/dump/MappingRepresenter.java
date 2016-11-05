package com.rcgreed.yaml.dump;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.Context;
import com.rcgreed.yaml.node.MappingNode;
import com.rcgreed.yaml.node.MappingNode.YamlIterator;
import com.rcgreed.yaml.node.Node;
import com.rcgreed.yaml.node.ScalarNode;
import com.rcgreed.yaml.utils.Helper;
import com.rcgreed.yaml.utils.Pair;

public class MappingRepresenter implements Representer {

	@Override
	public Node represent(ClazzValue cv,final Context ctx,final Representer base) throws YamlExecption {
		final Class<?> clz=cv.first();
		if (Map.class.isAssignableFrom(clz))
			return null;
		final ArrayList<Field> ads = new ArrayList<>();
		for(Field fd: clz.getFields()){
			if((fd.getModifiers() & Modifier.STATIC)==0){
				ads.add(fd);
			}
		}
		
		if (ads.size() == 0)
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
		final Object targetObj=cv.second();
		final YamlIterator itr=new YamlIterator() {
			Iterator<Field> itr=ads.iterator();
			@Override
			public Pair<Node, Node> next() throws YamlExecption {
				try {
					Field fd=itr.next();
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
					ClazzValue sub=new ClazzValue(subTy, fd.get(targetObj));
					if (tval != null) {
						String value = ctx.incept(clz, skey, sub);
						Node nval = ScalarNode.newInstance(tval, value,ctx.getConfig(null));
						return Helper.newPair(key, nval);
					}
					Node nval=base.represent(sub, ctx, base);
					return Helper.newPair(key, nval);
				} catch (IllegalArgumentException e) {
					throw new YamlExecption(e);
				} catch (IllegalAccessException e) {
					throw new YamlExecption(e);
				}
			}
			
			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}
		};
			return new MappingNode() {

				@Override
				public PresenterConfig presenterConfig() {
					return ctx.getConfig(clz);
				}

				@Override
				public Tag getTag() {
					return ftag;
				}

				@Override
				protected YamlIterator getData() {
					return itr;
				}

				@Override
				public void put(Node key, Node value) {
					throw new RuntimeException("do call me");
				}
			};
	}
}