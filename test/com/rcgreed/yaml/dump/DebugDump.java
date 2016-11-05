package com.rcgreed.yaml.dump;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import com.rcgreed.yaml.EntityUtils;
import com.rcgreed.yaml.Expense;
import com.rcgreed.yaml.Month;
import com.rcgreed.yaml.Yaml;
import com.rcgreed.yaml.YamlExecption;
import com.rcgreed.yaml.inceptor.ToString;

public class DebugDump {

	public static void main(String[] args) throws YamlExecption, IOException {
		Yaml yaml=new Yaml();
		Expense es[]=new Expense[4];
		for(int i=0;i<4;i++){
			es[i]=EntityUtils.sampleExpense();
		}
		Month m=new Month();
		m.费用=es;
		m.head=new Date();
		yaml.fieldNameMapping(Expense.class,"countIn", "排除金额");
		yaml.tagHide(Expense.class);
		yaml.valueToString(Expense.class, "when",new ToString() {
			
			@Override
			public String string(ClazzValue cv) {
				Date d=(Date) cv.second();
				return String.valueOf(d.getTime());
			}
		});
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		yaml.dump(bout,m);
		bout.close();
		byte []buf=bout.toByteArray();
		loop(buf);
	}

	private static void loop(byte[] buf) throws UnknownHostException {
		int count=0;
		while(true){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Socket socket;
			System.out.printf("--- %d \n",count++);
			try {
				socket = new Socket("192.168.30.10",31415);
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}
			OutputStream out;
			try {
				out = socket.getOutputStream();
				PrintWriter print=new PrintWriter(out);
				print.printf("--- # %d\n",count);
				print.flush();
				out.write(buf);
				print.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}

}
