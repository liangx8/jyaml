package com.rcgreed.yaml;

import java.io.InputStream;
import java.io.OutputStream;

public class Yaml {
	private DumpConfig dcfg=DumpConfig.defaultConfig;
	final private BaseConversion bc=new BaseConversion();
	public Yaml() {
		bc.register(new CollectionConversion());
	}
	public Yaml withDumpConfig(DumpConfig cfg){
		dcfg=cfg;
		return this;
	}
	public void dump(OutputStream out,Object obj) throws YamlExecption{
		YamlObject yobj=bc.convert(obj,null);
		yobj.dump(dcfg.newWriter(out));
	}
	public Yaml registerConvertion(Conversion convertion) {
		bc.register(convertion);;
		return this;
	}
	public void load(InputStream in,Class<?> clz) throws YamlExecption{
		
	}
}
