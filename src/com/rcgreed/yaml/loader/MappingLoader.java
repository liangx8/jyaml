package com.rcgreed.yaml.loader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import com.rcgreed.yaml.YamlExecption;

public class MappingLoader implements Loader {

	@Override
	public Object load(Class<?> targetClass, YamlReader reader, LoaderChain chain, FieldNameInterpreter fni, boolean cr)
			throws YamlExecption {
		// reader 中 line的position应该在0位置或者符号'-'/':'后面的空白上
		try {
			Line line;
			// cr 是否需要换新的一行
			if(cr){
				line=reader.readLine();
				int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
				if(ind >= 0){
					throw new YamlExecption(line.lineNumber, "Expecting end of line here");
				}
				line.done=true;
			}
//			line = reader.readLineBare();
//			int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
//			if(ind <= reader.indent){
//				return null;
//			}
			YamlReader rdr=reader.objStart();
			if(rdr==null){
				return null;
			}
			if(rdr.indent<reader.indent){
				// 无下一级的上下文
				return null;
			}
			Object obj = targetClass.newInstance();
			HashMap<String, Field> fields = new HashMap<>();
			Field fs[] = targetClass.getFields();
			for (Field f : fs) {
				fields.put(fni.interpret(f.getName()), f);
			}
			while(true){
				line = rdr.readLineBare();
				int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(0, line.line.length));
				if(ind < rdr.indent){
					return obj;
				}
				if(ind == rdr.indent){
					String key=ByteParser.parseMapKey(line);
					Field f=fields.get(key);
					if(f==null){
						// 找不到对应的字段,舍弃这个上下文段
						while(true){
							line.done=true;
							line=rdr.readLineBare();
							ind= ByteParser.leadSpace(line.line, new ByteParser.Range(0, line.line.length));
							if(ind <= rdr.indent) break;
						}
						continue;
					}
					f.set(obj, chain.load(f.getType(), rdr, true));
				}
			}

			// never reach here
		} catch (InstantiationException e) {
			throw new YamlExecption(e);
		} catch (IllegalAccessException e) {
			throw new YamlExecption(e);
		} catch (IOException e) {
			throw new YamlExecption(e);
		}
	}

}
