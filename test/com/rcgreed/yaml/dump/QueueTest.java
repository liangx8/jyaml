package com.rcgreed.yaml.dump;

import com.rcgreed.yaml.inceptor.Linked;

public class QueueTest {

	public static void main(String[] args) {
		Linked<Integer> v=new Linked<>();
		for(int i=0;i<10;i++){
			v.add(i);
		}
		for(Integer i:v){
			System.out.println(i);
		}
	}

}
