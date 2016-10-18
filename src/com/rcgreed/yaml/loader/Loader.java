package com.rcgreed.yaml.loader;

import com.rcgreed.yaml.YamlExecption;
/**
 * 
 * @author arm
 *
 */
public interface Loader {
	/**
	 * 从 {@link YamlReader} 中读取上下文,并且转化成对象
	 * @param targetClass 需要转化的对象的类型
	 * @param reader {@link YamlReader}
	 * @param chain 转化时,遇到下一层的上下文,需要调用这个以返回上一层去调用其他的 {@link Loader}
	 * @param fni 类的字段名字的翻译
	 * @param cr 下一层是否需要换行.
	 * @return 返回结果
	 * @throws YamlExecption
	 */
	Object load(Class<?> targetClass, YamlReader reader, LoaderChain chain, FieldNameInterpreter fni, boolean cr)
			throws YamlExecption;
}
