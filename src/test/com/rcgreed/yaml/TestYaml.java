package test.com.rcgreed.yaml;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.rcgreed.yaml.DumpConfig;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.YamlScalar;
import com.rcgreed.yaml.YamlWriter;

public class TestYaml {
	DumpConfig cfg;
	@Before
	public void setUp() throws Exception {
		cfg=DumpConfig.defaultConfig;
	}

	@Test
	public void test() throws YamlExecption, UnsupportedEncodingException {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		YamlWriter writer=cfg.newWriter(out);
		writer.tag("seq");
		assertArrayEquals(out.toByteArray(), "!!seq".getBytes("UTF-8"));
	}
	@Test
	public void testYamlScalar() throws YamlExecption{
		YamlScalar scalar=new YamlScalar(12345);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		YamlWriter writer=cfg.newWriter(out);
		scalar.dump(writer);
		assertArrayEquals(out.toByteArray(),"12345\n".getBytes());
	}
}
