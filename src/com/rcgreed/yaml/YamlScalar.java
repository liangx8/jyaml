package com.rcgreed.yaml;

import java.util.Date;
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
	public YamlScalar(Date v){
		super("date");
		long d=v.getTime();
		data=new ByteHelper().fromLong(d);
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
	public final static YamlObject NullScalar=new YamlObject(TYPE_NULL) {
		
		@Override
		public void dump(YamlWriter writer) throws YamlExecption {
			if(isTagShow()){
				writer.tag(getTagName());
				writer.write(' ');
			}
			//writer.write(new byte[]{'~','\n'});
			writer.write('~');
			writer.write('\n');
		}
	};
}
