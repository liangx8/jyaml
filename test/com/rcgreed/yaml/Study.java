package com.rcgreed.yaml;

import java.util.HashSet;

public class Study {

	public static void main(String[] args) {
		HashSet<Class<?>> is=new HashSet<>();
		is.add(A.class);
		is.add(A.class);
		is.add(new A().getClass());
		System.out.println(is);
	}
	public static class A{
		public int v;
		@Override
		public boolean equals(Object obj) {
			A a=(A) obj;
			
			return v>a.v;
		}
		@Override
		public int hashCode() {
			return 99;
		}
		@Override
		public String toString() {
			return String.valueOf(v);
		}
	}
}
