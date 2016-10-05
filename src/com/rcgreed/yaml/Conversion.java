package com.rcgreed.yaml;

public interface Conversion {
	YamlObject convert(Object obj,Conversion parent) throws YamlExecption;
}
