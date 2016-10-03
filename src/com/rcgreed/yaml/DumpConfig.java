package com.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class DumpConfig {
	public final static DumpConfig defaultConfig = new DumpConfig();
	public int indent;
	public boolean showStart, showEnd;
	public Map<String, String> tagMappinig;
	static {
		defaultConfig.indent = 2;
		defaultConfig.showEnd = false;
		defaultConfig.showStart = false;

	}

	public YamlWriter newWriter(OutputStream out) {
		return new WriterImpl(this, out);
	}

	private static class WriterImpl implements YamlWriter {
		final private OutputStream out;
		final private int level;
		final private DumpConfig config;
		private boolean cr;
		// Has the tag been printed?
		private boolean tagPrinted = false;

		public WriterImpl(DumpConfig cfg, OutputStream out) {
			this.out = out;
			level = 0;
			config = cfg;
			cr = false;
		}

		private WriterImpl(DumpConfig cfg, OutputStream out, int level, boolean cr) {
			if (out == null || level <= 0) {
				throw new RuntimeException("Instance of OutputStream must be given or level must greate than zero");
			}
			this.out = out;
			this.level = level;
			config = cfg;
			this.cr = cr;
		}

		@Override
		public YamlWriter next(boolean cr) {
			return new WriterImpl(config, out, level + 1, cr);
		}

		@Override
		public void write(byte[] data) throws YamlExecption {
			try {
				out.write(data);
				out.write('\n');
			} catch (IOException e) {
				throw new YamlExecption(e);
			}
		}

		@Override
		public void tag(String tagName) throws YamlExecption {
			if (tagPrinted) {
				throw new YamlExecption("tag has been printed twice");
			}
			try {
				out.write(("!!" + tagName).getBytes("UTF-8"));
				cr = true;
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Impossible/Never reach here", e);
			} catch (IOException e) {
				throw new YamlExecption(e);
			}
			tagPrinted = true;
		}

		@Override
		public void write(int c) throws YamlExecption {
			try {
				out.write(c);
			} catch (IOException e) {
				throw new YamlExecption(e);
			}
		}

		@Override
		public Indention indent(int type) {
			final byte[] space = ByteHelper.space(level * config.indent);
			switch (type) {
			case YamlWriter.SCALAR_INDENT:
				return new BaseIndention() {

					@Override
					public void indent() throws YamlExecption {
						if (first) {
							first = false;
							return;
						}
						try {
							out.write(space);
						} catch (IOException e) {
							throw new YamlExecption(e);
						}
					}
				};
			case YamlWriter.SEQUENCE_INDENT:
				return new BaseIndention() {

					@Override
					public void indent() throws YamlExecption {
						byte sp[]=ByteHelper.space(config.indent);
						sp[0]='-';
						try {
							if (first) {
								first = false;
								if (cr) {
									out.write('\n');
								} else {
									out.write(sp);
									return;
								}
							}
							out.write(space);
							out.write(sp);
						} catch (IOException e) {
							throw new YamlExecption(e);
						}

					}
				};
			case YamlWriter.MAPPING_INDENT:
				return new BaseIndention() {

					@Override
					public void indent() throws YamlExecption {
						try {
							if (first) {
								first = false;
								if (cr) {
									out.write('\n');
								} else {
									// reach here if parent type is sequence
									byte[] sp = ByteHelper.space(config.indent - 1);
									out.write(sp);
									return;
								}
							}
							if (level == 0) {
								return;
							}
							out.write(space);
						} catch (IOException e) {
							throw new YamlExecption(e);
						}
					}
				};
			default:
				throw new RuntimeException("don't know how to handle indention type");
			}
		}

		@Override
		public void stringModifier() throws YamlExecption {
			int c = '0';
			c = c + (level == 0 ? 0 : config.indent);
			if (c == '0') {
				return;
			}
			try {
				out.write(c);
			} catch (IOException e) {
				throw new YamlExecption(e);
			}

		}

		@Override
		public void writeKey(String key) throws YamlExecption {
			try {
				out.write(new ByteHelper().printf("%s : ", key));
			} catch (IOException e) {
				throw new YamlExecption(e);
			}
		}
	}

	private static abstract class BaseIndention implements YamlWriter.Indention {
		protected boolean first;

		public BaseIndention() {
			first = true;
		}

	}
}
