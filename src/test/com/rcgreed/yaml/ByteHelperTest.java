package test.com.rcgreed.yaml;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rcgreed.yaml.ByteHelper;

public class ByteHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		byte[] buf=new ByteHelper().fromLong(12345);
		assertArrayEquals("12345".getBytes(), buf);
	}
	@Test
	public void testMultipleLine(){
		List<byte[]> actual=ByteHelper.multipleLine(mstr.getBytes());
		assertEquals(strs.length, actual.size());
		for(int i=0;i<strs.length;i++){
			assertArrayEquals(strs[i].getBytes(), actual.get(i));
		}
	}
	@Test
	public void testPrintf(){
		assertArrayEquals(mstr.getBytes(), new ByteHelper().printf("%d\n%d", 1234,5678));
	}
	final static String mstr="1234\n5678";
	final static String strs[]={"1234","5678"};
}
