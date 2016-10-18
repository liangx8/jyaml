package com.rcgreed.yaml.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.LineUnavailableException;

import com.rcgreed.yaml.YamlExecption;

public class YamlReader {
	final private ReadLine rl;
	final public int indent;

	/**
	 * 
	 * @param in
	 * @throws YamlExecption
	 */
	public YamlReader(InputStream in) throws YamlExecption {
		rl = new ReadLine(in);
		indent = 0;
		int ind;
		try {
			while (true) {
				rl.read();
				if (rl.current == null) {
					return;
				}
				int commentPos = ByteParser.trimComment(rl.current.line);
				ind = ByteParser.leadSpace(rl.current.line, new ByteParser.Range(0, commentPos));
				if (ind < 0) {
					continue;
				}
				break;
			}
			rl.current.position = 0;
		} catch (IOException e) {
			throw new YamlExecption(e);
		}
	}

	private YamlReader(ReadLine rLine, int ind) {
		rl = rLine;
		indent = ind;
	}

	/**
	 * 
	 * @return null if InputStream was reach end, or return Line
	 * @throws IOException
	 */
	public Line readLine() throws IOException {
		if (rl.current != null && rl.current.done) {
			rl.read();
		}
		return rl.current;
	}

	/**
	 * 
	 * @return null if {@link InputStream} was reach end, or return Line without comment
	 *         section
	 * @throws IOException
	 */
	public Line readLineBare() throws IOException {
		if (rl.current != null && rl.current.done) {
			rl.bareRead();
		}
		return rl.current;
	}

	private static class ReadLine {
		final private InputStream in;
		public Line current;

		public ReadLine(InputStream in) {
			this.in = in;
		}

		public void bareRead() throws IOException {
			while (true) {
				read();
				if (current == null) {
					return;
				}
				int commentPos = ByteParser.trimComment(current.line);
				int space = ByteParser.leadSpace(current.line, new ByteParser.Range(0, commentPos));
				if (space >= 0) {
					current = new Line(ByteParser.slice(current.line, new ByteParser.Range(0, commentPos)),
							current.lineNumber);
					return;
				}
			}
			// current = new Line(ByteParser.slice(current.line, new
			// ByteParser.Range(0, commentPos)), current.lineNumber);
		}

		public void read() throws IOException {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int c;
			boolean empty = true;
			while (true) {
				c = in.read();
				if (c == -1) {
					if (empty) {
						current = null;
						return;
					}
					break;
				}
				empty = false;
				if (c == '\n') {
					break;
				}
				buf.write(c);
			}
			buf.close();
			long lineNumber = 1;
			if (current != null) {
				lineNumber += current.lineNumber;
			}
			current = new Line(buf.toByteArray(), lineNumber);
			return;
		}
	}

	/**
	 * <pre>
	 * {@link Line} 中的定义， position:
	 * 上一个阶段:如果是刚开始，应该等于 0
	 *          如果是sequence, 应该在 '-'后面
	 *          如果是mapping, 应该在 ':'后面,
	 * 动作：下一阶段的 {@link YamlReader},
	 * 1. 查找下一个非' '字符。
	 * 2. 如果找到,定位position,生成新的 {@link YamlReader} 并且以新的位置作为 {@link YamlReader} 的indent 
	 * 3. 如果直到结束,都没有找到,读新的 {@link Line} ,重复 1 4. 生成新的 {@link YamlReader}
	 * </pre>
	 * @return null if InputStream is reached EOF
	 * @throws IOException
	 */
	public YamlReader objStart() throws IOException {
		Line line = rl.current;
		int ind = ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
		if(ind < 0)
			rl.bareRead();
		if(rl.current==null) return null;
		return new YamlReader(rl, ind);
	}
	/**
	 * <pre>
	 * 定义与 {@code objStart} 一样
	 * 行动:

	 * 1. 查找第一个非' '字符.这个position 为scalar的开始
	 * </pre>
	 * @return
	 * @throws IOException
	 */
	public YamlReader scalarStart() throws IOException {
		Line line=rl.current;
		int ind= ByteParser.leadSpace(line.line, new ByteParser.Range(line.position, line.line.length));
		if(ind <0){
			// 当前行没有内容。必须读取下一行
		}
		return new YamlReader(rl, 0);
	}
}
