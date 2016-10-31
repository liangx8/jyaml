package com.rcgreed.yaml.inceptor;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.YamlExecption;

public interface TagInceptor {
	Tag incept(Class<?> clz, Tag org) throws YamlExecption;
}
