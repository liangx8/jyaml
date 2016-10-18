package com.rcgreed.yaml.loader;

import java.io.IOException;
import java.util.Date;

import com.rcgreed.yaml.YamlExecption;

/**
 * for scalar only handle primitive type and {@link Short}, {@link Integer},
 * {@link Long}, {@link Float}, {@link Double}, {@link Boolean}, {@link String}
 * {@link Date}
 * 
 * @author arm
 *
 */
public class ScalarLoader implements Loader {

	@Override
	public Object load(Class<?> targetClass, YamlReader reader, LoaderChain chain, FieldNameInterpreter fni, boolean cr)
			throws YamlExecption {
		try {
			Line line = reader.readLineBare();
			if (targetClass.isPrimitive()) {
				Object obj= primitiveLoad(targetClass,ByteParser.slice(line.line, new ByteParser.Range(line.position, line.line.length)));
				line.done=true;
				return obj;
			}
			if (targetClass == String.class) {
				
			}
			if (targetClass == Date.class) {

			}
		} catch (IOException e) {
			throw new YamlExecption(e);
		}
		return null;
	}

	private static Object primitiveLoad(Class<?> targetClass, byte[] slice) {
		if(targetClass == short.class){
			return Short.parseShort(new String(slice));
		}
		if(targetClass == int.class){
			return Integer.parseInt(new String(slice));
		}
		if(targetClass == long.class){
			return Long.parseLong(new String(slice));
		}
		if(targetClass == float.class){
			return Float.parseFloat(new String(slice));
		}
		if(targetClass == double.class){
			return Double.parseDouble(new String(slice));
		}
		if(targetClass == boolean.class){
			return Boolean.parseBoolean(new String(slice));
		}
		return null;
	}
}
