package com.rcgreed.yaml.dump;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class DumpConfig {
	public int indent;

	public YamlWriter newWriter(OutputStream out) {
		try {
			return new InnerWriter(indent, new OutputStreamWriter(out, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("impossible", e);
		}

	}

	private static class InnerWriter implements YamlWriter {
		private final OutputStreamWriter w;
		private final int indent, level;
		private final Line current;

		public InnerWriter(int ind, OutputStreamWriter writer) {
			indent = ind;
			w = writer;
			level = 0;
			current = new Line();
		}

		private InnerWriter(Line l, int ind, int lvl, OutputStreamWriter writer) {
			w = writer;
			indent = ind;
			level = lvl;
			current = l;
		}

		@Override
		public YamlWriter next() {
			return new InnerWriter(current, indent, level+1, w);
		}

		@Override
		public void writeText(CharSequence text) {
			if (current.lineDone)
				current.comment.append(text);
			else
				current.line.append(text);
			current.indentOk = false;
		}

		@Override
		public int write(byte[] buf) {
			throw new RuntimeException("don't call me");
		}

		@Override
		public void write(int ch) {
			if (current.lineDone)
				current.comment.append((char) ch);
			else
				current.line.append((char) ch);
			current.indentOk = false;
		}

		@Override
		public void newLine() {
			try {
				w.write(current.line.toString());
				if (current.comment.length() > 0) {
					w.write('#');
					w.write(current.comment.toString());
				}
				w.write('\n');
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			current.reset();
			for (int i = 0; i < indent * level; i++) {
				current.line.append(' ');
			}
		}

		@Override
		public boolean nextLineIndent() {
			if (current.indentOk) {
				for (int i = 2; i < indent; i++) {
					current.line.append(' ');
				}
				return true;
			}
			return false;
		}

		@Override
		public void lineDone() {
			current.lineDone = true;

		}

		@Override
		public void writeIndentNumber() {
			if (current.lineDone) {
				throw new RuntimeException("当前行已经结束.不要进行该操作");
			}
			current.line.append((char) (indent + '0'));
		}

		@Override
		public void writeSeqEntry() {
			if (current.lineDone) {
				throw new RuntimeException("当前行已经结束.不要进行该操作");
			}
			current.line.append("- ");
		}

		@Override
		public void writeMappingKey() {
			if (current.lineDone) {
				throw new RuntimeException("当前行已经结束.不要进行该操作");
			}
			current.line.append("? ");
		}

		@Override
		public void writeMappingValue() {
			if (current.lineDone) {
				throw new RuntimeException("当前行已经结束.不要进行该操作");
			}
			current.line.append(": ");
		}

		@Override
		public void writeStartMap() {
			throw new UnsupportedOperationException("没实现");
		}

		@Override
		public void writeEndMap() {
			throw new UnsupportedOperationException("没实现");
		}

		@Override
		public void writeStartSeq() {
			throw new UnsupportedOperationException("没实现");
		}

		@Override
		public void writeEndSeq() {
			throw new UnsupportedOperationException("没实现");
		}

		private static class Line {
			final public StringBuilder line = new StringBuilder();
			final public StringBuilder comment = new StringBuilder();
			public boolean lineDone = false;
			public boolean indentOk = true;

			public void reset() {
				line.setLength(0);
				comment.setLength(0);
				lineDone = false;
				indentOk = true;
			}
		}

		@Override
		public void close() throws IOException {
			w.close();
		}
	}
}
