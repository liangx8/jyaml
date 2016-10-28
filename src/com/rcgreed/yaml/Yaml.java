package com.rcgreed.yaml;

import java.io.OutputStream;

import com.rcgreed.yaml.Yaml.Dumper;
import com.rcgreed.yaml.dump.DumpConfig;
import com.rcgreed.yaml.dump.RootRepresenter;
import com.rcgreed.yaml.dump.YamlWriter;
import com.rcgreed.yaml.node.Node;

public class Yaml {
	public interface Dumper{
		Dumper setConfig(DumpConfig config);
		void dump(OutputStream out,Object obj) throws YamlExecption;
	}
	public Dumper newDumper(){
		return new Dumper() {
			
			@Override
			public Dumper setConfig(final DumpConfig config) {
				return new Dumper() {
					
					@Override
					public Dumper setConfig(DumpConfig config) {
						throw new RuntimeException("do not call me");
					}
					
					@Override
					public void dump(OutputStream out, Object obj) throws YamlExecption {
						RootRepresenter rr=new RootRepresenter();
						Node n=rr.represent(obj);
						YamlWriter w=config.newWriter(out);
						n.present(w);
						
						
					}
				};
			}
			
			@Override
			public void dump(OutputStream out, Object obj) throws YamlExecption {
				RootRepresenter rr=new RootRepresenter();
				Node n=rr.represent(obj);
				DumpConfig config=new DumpConfig();
				YamlWriter w=config.newWriter(out);
				n.present(w);
				
			}
		};
	}
}
