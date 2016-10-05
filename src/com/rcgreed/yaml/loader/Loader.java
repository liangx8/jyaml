package com.rcgreed.yaml.loader;

import com.rcgreed.yaml.YamlExecption;

public interface Loader {
	void load(YamlReader reader) throws YamlExecption;
}
