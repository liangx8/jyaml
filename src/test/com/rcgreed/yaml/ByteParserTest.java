package test.com.rcgreed.yaml;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.loader.ByteParser;
import com.rcgreed.yaml.loader.Line;

public class ByteParserTest {
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test() throws UnsupportedEncodingException, YamlExecption {
		assertArrayEquals(ByteParser.slice("0123456789".getBytes(),new ByteParser.Range(2, 7)), "23456".getBytes());
		Line line=sampleLine("   键值    : adkkfds",0);
		assertEquals("键值", ByteParser.parseMapKey(line));
		assertEquals(14, line.position);
		line = sampleLine("键值  :",1);
		assertEquals("键值", ByteParser.parseMapKey(line));
		line = sampleLine("键值 1   :",2);
		assertEquals("键值 1", ByteParser.parseMapKey(line));
		line = sampleLine("键值    ",3);
		try {
			ByteParser.parseMapKey(line);
			assertFalse("expecting a execption", true);
		} catch (Throwable e) {	}
		line = sampleLine("\"键值 1\" :",4);
		assertEquals("键值 1", ByteParser.parseMapKey(line));
		assertTrue(line.done);
	}
	@Test
	public void testTrimComment(){
		//        01234567890123 45678901234 567890
		String s="123'45678#90' \"##########\"#xxxx";
		assertEquals(26, ByteParser.trimComment(s.getBytes()));
		s="123'45678#90' \"###########xxxx";
		assertEquals(s.length(),ByteParser.trimComment(s.getBytes()));
	}
	private static Line sampleLine(String string,int n) {
		Line line=new Line(string.getBytes(utf8), n);
		line.position=ByteParser.leadSpace(line.line, new ByteParser.Range(0,line.line.length));
		return line;
	}
	private static Charset utf8=Charset.forName("UTF-8");
}
