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
	public static Tag MapTag=new Tag(){

		@Override
		public String getName() {
			return "!!map";
		}

		@Override
		public Kind kind() {
			return Kind.Mapping;
		}
		
	};
	public static Tag SeqTag=new Tag(){

		@Override
		public String getName() {
			return "!!seq";
		}

		@Override
		public Kind kind() {
			return Kind.Sequence;
		}
		
	};
	public static Tag IntTag=new Tag(){

		@Override
		public String getName() {
			return "!!int";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
		
	};
	public static Tag FloatTag=new Tag(){

		@Override
		public String getName() {
			return "!!float";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
		
	};
	public static Tag BoolTag=new Tag(){

		@Override
		public String getName() {
			return "!!bool";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
		
	};
	public static Tag StrTag=new Tag(){

		@Override
		public String getName() {
			return "!!str";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
		
	};
	public static Tag DateTag=new Tag(){

		@Override
		public String getName() {
			return "!date";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
	};
	public static Tag NullTag=new Tag(){

		@Override
		public String getName() {
			return "!!null";
		}

		@Override
		public Kind kind() {
			return Kind.Scalar;
		}
		
	};

}
