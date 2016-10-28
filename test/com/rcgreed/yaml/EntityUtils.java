package com.rcgreed.yaml;

import java.util.Calendar;
import java.util.Random;

import com.rcgreed.yaml.utils.Pair;

public class EntityUtils {
	private static Random r=new Random();
	public static Expense sampleExpense(){
		Expense e=new Expense();
		e.amount=r.nextInt(1000)+100;
		e.countIn=r.nextBoolean();
		Calendar cal=Calendar.getInstance();
		e.when=cal.getTime();
		cal.add(Calendar.MINUTE, -10);
		e.update=cal.getTime();
		return e;
	}
}
