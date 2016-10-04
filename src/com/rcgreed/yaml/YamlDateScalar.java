package com.rcgreed.yaml;

import java.util.Date;

public class YamlDateScalar extends YamlObject {
	final private Date date;
	public YamlDateScalar(Date dat) {
		super("date");
		date=dat;
	}

	@Override
	public void dump(YamlWriter writer) throws YamlExecption {
		if(isTagShow()){
			writer.tag(getTagName());
			writer.write(' ');
		}
		writer.writeDate(date);
	}

}
