package com.rcgreed.yaml.loader;

import java.util.ArrayList;

import com.rcgreed.yaml.YamlExecption;

public class LoaderChain{
	private final ArrayList<Loader> loaders=new ArrayList<>();
	
	private LoaderConfig config=new LoaderConfig();
	public LoaderChain() {
		loaders.add(new ScalarLoader());
		loaders.add(new SequenceLoader());
		loaders.add(new MappingLoader());
	}
	public void add(Loader loader){
		loaders.add(loader);
	}
	public LoaderChain setConfig(LoaderConfig cfg){
		config=cfg;
		return this;
	}
	public Object load(Class<?> targetClass, YamlReader reader,boolean cr) throws YamlExecption {
		Object obj;
		FieldNameInterpreter fni=config.fieldMap.get(targetClass);
		if(fni == null){
			fni=FieldNameInterpreterBuilder.defaultFieldNameInterpreter();
		}
		for(Loader l:loaders){
			obj=l.load(targetClass, reader, this, fni,cr);
			if(obj!=null){
				return obj;
			}
		}
		throw new YamlExecption(String.format("We don't know how to handle class %s",targetClass.getName()));
	}
	
}
