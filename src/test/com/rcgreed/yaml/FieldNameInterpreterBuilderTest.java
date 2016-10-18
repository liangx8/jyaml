package test.com.rcgreed.yaml;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.rcgreed.yaml.loader.FieldNameInterpreter;
import com.rcgreed.yaml.loader.FieldNameInterpreterBuilder;

public class FieldNameInterpreterBuilderTest {

	@Before
	public void setUp() throws Exception {
		map=new HashMap<>();
		map.put(s1, s2);
	}

	@Test
	public void testMapFieldNameInterpreter() {
		FieldNameInterpreter fni=FieldNameInterpreterBuilder.mapFieldNameInterpreter(map);
		assertEquals(fni.interpret(s1), s2);
		assertEquals(fni.deinterpret(s2), s1);
	}
	String s1="userName";
	String s2="user-name";
	HashMap<String,String> map;

	@Test
	public void testHyphenFieldNameInterpreter() {
		FieldNameInterpreter fni=FieldNameInterpreterBuilder.hyphenFieldNameInterpreter();
		assertEquals(fni.interpret(s1), s2);
		assertEquals(fni.deinterpret(s2), s1);
		assertEquals(fni.interpret("AabC"), "aab-c");
		assertEquals(fni.deinterpret("ab"), "ab");
	}

}
