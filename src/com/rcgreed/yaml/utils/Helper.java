package com.rcgreed.yaml.utils;


public class Helper {
	public static <S1,S2> Pair<S1,S2> newPair(S1 s1,S2 s2){
		return new PairImpl<S1, S2>(s1, s2);
	}
	private static class PairImpl<S1,S2> implements Pair<S1,S2>{
		private final S1 s1;
		private final S2 s2;
		public PairImpl(S1 s1,S2 s2){
			this.s1=s1;
			this.s2=s2;
		}
		@Override
		public S1 first() {
			return s1;
		}
		@Override
		public S2 second() {
			return s2;
		}
	}

}
