package test.com.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;

import com.rcgreed.yaml.DumpConfig;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.YamlMapping;
import com.rcgreed.yaml.YamlScalar;
import com.rcgreed.yaml.YamlSequence;

public class TestRun {

	public static void main(String[] args) throws YamlExecption, IOException {
		YamlMapping map=example();
		OutputStream out=System.out;
		DumpConfig cfg=DumpConfig.defaultConfig;
		map.put("map", example());
		map.putString("string", mstr);
		map.put("list", array());
		YamlSequence seq=array();
		seq.add(map);
		seq.add(array().setTagShow(true));
		seq.dump(cfg.newWriter(out));

		out.flush();
	}
	private static YamlMapping example() throws YamlExecption {
		YamlMapping map=new YamlMapping();
		map.putLong("integer", 123);
		map.putBoolean("boolean", false);
		map.putDouble("double", 3.1415926);
		map.setTagShow(true);
		map.put("has tag", new YamlScalar(12.3).setTagShow(true));
		return map;
	}
	private static YamlSequence array() {
		YamlSequence seq=new YamlSequence();
		seq.addBoolean(true);
		seq.addDouble(3.14);
		seq.addLong(123444);
		seq.addString("正體中文");
		return seq;
	}
	final static String mstr="jjj:\n中文漢字，簡體s; dsf \n  dsfjklasd\n  sdkj; k;asd  k;ds  ksdf";
}
