package com.rcgreed.yaml;

import java.io.OutputStream;

public class Yaml {
	private DumpConfig dcfg=DumpConfig.defaultConfig;
	private Convertion convertion=new BaseConversion();
	public Yaml withDumpConfig(DumpConfig cfg){
		Yaml y=new Yaml();
		y.dcfg=cfg;
		return y;
	}
	public void dump(OutputStream out,Object obj) throws YamlExecption{
		YamlObject yobj=convertion.convert(obj);
		yobj.dump(dcfg.newWriter(out));
	}
	public Yaml setConvertion(Convertion convertion) {
		this.convertion = convertion;
		return this;
	}
}
