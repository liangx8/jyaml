package com.rcgreed.yaml.loader;

public interface FieldNameInterpreter {
	String interpret(String name);
	String deinterpret(String name);
}
