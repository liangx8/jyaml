package com.rcgreed.yaml.dump;

import java.io.IOException;
import java.util.Date;

import com.rcgreed.yaml.EntityUtils;
import com.rcgreed.yaml.Expense;
import com.rcgreed.yaml.Month;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.node.Node;

public class DumperRun {
	private static void test1() throws YamlExecption, IOException{
		RootRepresenter rr=new RootRepresenter();
		
/*		rr.add(new AbstractObjectRepresenter() {
			
			@Override
			protected TypeDescription typeDescription(Class<?> ty) {
				return new TypeDescription() {
					
					@Override
					public String representTagName() {
						return MappingNode.MapTag;
					}
					
					@Override
					public String representFieldName(String fieldName) {
						if(fieldName=="countIn") return "计算金额";
						if(fieldName=="amount") return "  lkewr\ndkkekwer\n   dkllslsdf";
						return fieldName;
					}
					
					@Override
					public AtomicDataFormat dataFormat(String fieldName) {
						if(fieldName.equals("amount")){
							return new BaseAtomicDataFormat(){
								@Override
								public String intFormat(long v) {
									return String.format("%.2f", v/100.0);
								}
							};
						}
						if(fieldName.equals("when")){
							return new BaseAtomicDataFormat(){
								public String dateFormat(Date t) {return Definition.dateFormat.format(t);};
							};
						}
						return null;
					}
				};
			}
			
			@Override
			protected boolean pass(Class<?> ty) {
				return ! (ty==Expense.class);
			}
		});
*/
		Expense es[]=new Expense[4];
		for(int i=0;i<es.length-1;i++){
			es[i]=EntityUtils.sampleExpense();
		}
		es[3]=null;
		Month m=new Month();
		m.expenses=es;
		m.head=new Date();
		Node n=rr.represent(m);
		DumpConfig cfg=new DumpConfig();
		cfg.indent=2;
		YamlWriter w=cfg.newWriter(System.out);
		n.present(w);
		w.close();
		
	}
	public static void main(String[] args) throws YamlExecption, IOException {
		test1();
	}

}
