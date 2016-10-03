package com.rcgreed.yaml;

import java.util.ArrayList;
import java.util.HashSet;

import com.rcgreed.yaml.YamlWriter.Indention;

public class YamlMapping extends YamlObject {
	
	public static class Entry{
		public String key;
		public YamlObject value;
	}
	private final ArrayList<Entry> entries=new ArrayList<>();
	private final HashSet<String> keys=new HashSet<>();
	public YamlMapping(String tagName) {
		super(tagName);
	}
	public YamlMapping() {
		super(TYPE_MAPPING);
	}
	@Override
	public void dump(YamlWriter writer) throws YamlExecption {
		if(isTagShow()){
			writer.tag(getTagName());
		}
		Indention ind=writer.indent(YamlWriter.MAPPING_INDENT);

		for(Entry ety:entries){
			ind.indent();
			writer.writeKey(ety.key);
			ety.value.dump(writer.next(true));
		}
	}
	public void put(String key,YamlObject obj) throws YamlExecption{
		if(keys.contains(key)){
			throw new YamlExecption(String.format("Duplicate key %s", key));
		}
		keys.add(key);
		Entry entry=new Entry();
		entry.key=key;
		entry.value=obj;
		entries.add(entry);
	}

	public void putString(String key,String v) throws YamlExecption{
		if(keys.contains(key)){
			throw new YamlExecption(String.format("Duplicate key %s", key));
		}
		keys.add(key);
		Entry entry=new Entry();
		entry.key=key;
		entry.value=new YamlScalar(v);
		entries.add(entry);
	}

	public void putBoolean(String key,boolean v) throws YamlExecption{
		if(keys.contains(key)){
			throw new YamlExecption(String.format("Duplicate key %s", key));
		}
		keys.add(key);
		Entry entry=new Entry();
		entry.key=key;
		entry.value=new YamlScalar(v);
		entries.add(entry);
	}

	public void putDouble(String key,double v) throws YamlExecption{
		if(keys.contains(key)){
			throw new YamlExecption(String.format("Duplicate key %s", key));
		}
		keys.add(key);
		Entry entry=new Entry();
		entry.key=key;
		entry.value=new YamlScalar(v);
		entries.add(entry);
	}
	public void putLong(String key,long v) throws YamlExecption{
		if(keys.contains(key)){
			throw new YamlExecption(String.format("Duplicate key %s", key));
		}
		keys.add(key);
		Entry entry=new Entry();
		entry.key=key;
		entry.value=new YamlScalar(v);
		entries.add(entry);
	}
}
