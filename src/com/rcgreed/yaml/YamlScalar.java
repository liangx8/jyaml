package com.rcgreed.yaml;

import java.util.List;

import com.rcgreed.yaml.YamlWriter.Indention;

public class YamlScalar extends YamlObject {
	final private byte[] data;
	public YamlScalar(long v) {
		super(TYPE_INT);
		data=new ByteHelper().fromLong(v);
	}
	public YamlScalar(double v){
		super(TYPE_FLOAT);
		data=new ByteHelper().fromDouble(v);
	}
	public YamlScalar(boolean v){
		super(TYPE_BOOLEAN);
		data=new ByteHelper().fromBoolean(v);
	}
	public YamlScalar(String v){
		super(TYPE_STRING);
		data=new ByteHelper().fromString(v);
	}

	@Override
	public void dump(YamlWriter writer) throws YamlExecption {
		Indention ind=writer.indent(YamlWriter.SCALAR_INDENT);
		ind.indent();
		if(isTagShow()){
			writer.tag(getTagName());
			writer.write(' ');
		}
		if(getTagName()==TYPE_STRING){
			if (ByteHelper.isMultipleLine(data)){
				writer.write('|');
				writer.stringModifier();
				writer.write('\n');
				List<byte[]> bufs=ByteHelper.multipleLine(data);
				for(byte[] bs:bufs){
					ind.indent();
					writer.write(bs);
				}
				return;
			}
		}
		writer.write(data);
	}
}
