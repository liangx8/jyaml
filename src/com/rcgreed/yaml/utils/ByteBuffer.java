package com.rcgreed.yaml.utils;

import java.util.Arrays;

public class ByteBuffer {
	private static int ATOM_SIZE = 1024;
	private static int DEFAULT_SIZE = 4 * ATOM_SIZE;
	private int size = 0;
	private byte[] buffer = new byte[DEFAULT_SIZE];

	private void enlargeIf(int enlargeSize) {
		if (enlargeSize > buffer.length) {
			buffer = Arrays.copyOf(buffer, ((enlargeSize / ATOM_SIZE) + 1) * ATOM_SIZE);
		}
	}

	public void append(byte[] data) {
		enlargeIf(size + data.length);
		int idx = size;
		for (int i = 0; i < data.length; i++) {
			buffer[idx] = data[i];
			idx++;
		}
		size = idx;
	}

	public void append(byte b) {
		enlargeIf(size + 1);
		buffer[size] = b;
		size++;
	}

	public void reset() {
		size = 0;
	}

	public int getSize() {
		return size;

	}
}
