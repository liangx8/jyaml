package com.rcgreed.yaml.node;

import com.rcgreed.yaml.Tag;
import com.rcgreed.yaml.dump.Presenter;

public interface Node extends Presenter {
	Tag getTag();
}
