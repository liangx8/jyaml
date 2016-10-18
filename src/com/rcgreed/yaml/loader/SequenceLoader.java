package com.rcgreed.yaml.loader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.rcgreed.yaml.YamlExecption;

public class SequenceLoader implements Loader {

	@Override
	public Object load(Class<?> targetClass, YamlReader reader, LoaderChain chain, FieldNameInterpreter fni, boolean cr)
			throws YamlExecption {
		if(!targetClass.isArray()){
			return null;
		}
		Line line;
		try {
			if (cr) {
				line = reader.readLine();
				int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
				if (ind >= 0) {
					throw new YamlExecption(line.lineNumber, "Expecting a line end here");
				}
				line.done = true;

			}
			line = reader.readLineBare();
			int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
			if(ind <= reader.indent){
				return null;
			}
			YamlReader rdr=reader.objStart();
			Class<?> comClass=targetClass.getComponentType();
			ArrayList<Object> result=new ArrayList<>();
			while(true){
				line = rdr.readLineBare();
				if(line==null)break;
				ind=ByteParser.leadSpace(line.line, new ByteParser.Range(0, line.line.length));
				if(ind==rdr.indent){
					result.add(chain.load(comClass, rdr, false));
					continue;
				}
				if(ind < rdr.indent) break;
				throw new YamlExecption(line.lineNumber,"Indention is incorrect!");
			}
			if(result.size()==0){
				return null;
			}
			Object obj=Array.newInstance(comClass, result.size());
			for(int i=0;i<result.size();i++){
				Array.set(obj,i,result.get(i));
			}
			return obj;
		} catch (IOException e) {
			throw new YamlExecption(e);
		}
	}

}
