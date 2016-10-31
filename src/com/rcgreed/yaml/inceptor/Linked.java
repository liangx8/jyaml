package com.rcgreed.yaml.inceptor;

import java.util.Iterator;

public class Linked<T> implements Iterable<T> {
	private Box<T> head=null;
	public void add(T t){
		Box<T> box=new Box<>();
		box.value=t;
		box.next=head;
		head=box;
	}
	
	private static class Box<T>{
		public T value;
		public Box<T> next;
	}

	@Override
	public Iterator<T> iterator() {
		
		return new Iterator<T>() {
			private Box<T> pointer=head;
			@Override
			public boolean hasNext() {
				return pointer!=null;
			}

			@Override
			public T next() {
				T v = pointer.value;
				pointer=pointer.next;
				return v;
			}
		};
	}
}
