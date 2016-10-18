package test.com.rcgreed.yaml;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ByteHelperTest.class,
	TestYaml.class,
	ByteParserTest.class,
	FieldNameInterpreterBuilderTest.class})
public class AllTests {

}
