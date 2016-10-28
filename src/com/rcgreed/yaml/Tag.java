package com.rcgreed.yaml;

public interface Tag {

	String getName();
	Kind kind();
	enum Kind{
		Sequence,
		Mapping,
		Scalar;

		@Override
		public String toString() {
			switch(this){
			case Sequence:
				return "Sequence";
			case Mapping:
				return "Mapping";
			case Scalar:
				return "Scalar";
			}
			throw new RuntimeException("never reach");
		}
		
	}
}
