package test.com.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.rcgreed.yaml.DumpConfig;
import com.rcgreed.yaml.Yaml;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.YamlMapping;
import com.rcgreed.yaml.YamlObject;
import com.rcgreed.yaml.YamlScalar;
import com.rcgreed.yaml.YamlSequence;

public class TestRun {

	public static void main(String[] args) throws YamlExecption, IOException {
		OutputStream out=System.out;
		DumpConfig cfg=DumpConfig.defaultConfig;
//		YamlObject yobj=oldTest();
//		yobj.dump(cfg.newWriter(out));
		new Yaml().dump(out, exampleObj());
		out.flush();
	}
	private static Object exampleObj() {
		short[] shortary   = new short[]{1,2,3};
		int[] intary       = new int[]{10,20,30};
		long[] longary     = new long[]{100,200,300};
		float[] floatary   = new float[] {10.0f,20.0f,30.0f};
		double[] doubleary = new double[] {100.0d,200.0d,300.0d};
		String[] strary    = new String[]{mstr,"音乐电视","八九六四"};
		boolean[] boolary  = new boolean[]{false,true};
		Integer[] integerary = new Integer[] {1,2,3,4};
		Date[] dateary     = new Date[]{new Date(),new Date()};
		Expense e=new Expense();
		e.amount=100;
		e.countIn=true;
		e.when=new Date();
		e.remark=mstr;
		ArrayList<Float> lf=new ArrayList<>();
		lf.add(2.17f);
		lf.add(3.14f);
		lf.add(1.414f);
		ArrayList<Object> any=new ArrayList<>();
		any.add(213.4);
		any.add("雪山狮子");
		any.add(lf);
		e.any=any;
		Object[] ary=new Object[]{1,3,shortary,5,new Date(),intary,longary,floatary,doubleary,strary,new Date(),boolary,integerary,dateary,e};
		return ary;
	}
	private static YamlObject oldTest() throws YamlExecption{
		YamlMapping map=example();
		map.put("map", example());
		map.putString("string", mstr);
		map.put("list", array());
		YamlSequence seq=array();
		seq.add(map);
		seq.add(array().setTagShow(true));
		return seq;
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
	public static class Expense{
		public int amount,miles;
		public boolean countIn;
		public Date when,update;
		public String remark;
		public ArrayList<Object> any;
	}
}
