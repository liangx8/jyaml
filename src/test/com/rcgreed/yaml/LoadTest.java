package test.com.rcgreed.yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.loader.Line;
import com.rcgreed.yaml.loader.YamlReader;

public class LoadTest {

	public static void main(String[] args) throws YamlExecption, IOException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		FileInputStream f = new FileInputStream("sample/sample.yaml");
		YamlReader r = new YamlReader(f);
		Line b = r.readLine();
		while (b != null) {
			System.out.print(b.lineNumber);
			System.out.print(' ');
			System.out.write(b.line);
			System.out.println();
			b.done = true;
			b = r.readLine();
		}
		f.close();
		A a = new A();
		Class<A> ac = A.class;
		Field ff = ac.getField("a");
		ff.set(a, 100);
		System.out.println(a.a);
	}

	public static class A {
		public long a;
	}
}
