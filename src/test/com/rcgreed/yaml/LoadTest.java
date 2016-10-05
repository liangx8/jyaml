package test.com.rcgreed.yaml;

import java.io.FileInputStream;
import java.io.IOException;

import com.rcgreed.yaml.loader.YamlReader;
import com.rcgreed.yaml.loader.YamlReader.Line;

public class LoadTest {

	public static void main(String[] args) throws IOException {
		FileInputStream f=new FileInputStream("sample/sample.yaml");
		YamlReader r=new YamlReader(f);
		Line b=r.readLine();
		while(b != null){
			System.out.print(b.lineNumber);
			System.out.print(' ');
			System.out.write(b.line);
			System.out.println();
			b=r.readLine();
		}
		f.close();

	}

}
