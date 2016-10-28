package com.rcgreed.yaml.utils;

import com.rcgreed.yaml.YamlExecption;

public interface YamlIterator<T> {
	boolean hasNext();
	T next() throws YamlExecption;
}
