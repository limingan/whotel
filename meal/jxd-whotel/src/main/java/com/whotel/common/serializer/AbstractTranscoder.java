package com.whotel.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author
 * 
 */
public abstract class AbstractTranscoder implements Transcoder {
	private boolean compressed = false;

	@Override
	public Object deserialize(final byte[] bytes) {
		byte[] data = isCompressed() ? unGZip(bytes) : bytes;
		return doDeserialize(data);
	}

	protected abstract Object doDeserialize(byte[] data);

	@Override
	public byte[] serialize(Object object) {
		byte[] bytes = doSerialize(object);
		return isCompressed() ? gZip(bytes) : bytes;
	}

	protected abstract byte[] doSerialize(Object object);

	/***
	 * 压缩GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] gZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***
	 * 解压GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * @return the compressed
	 */
	public boolean isCompressed() {
		return compressed;
	}

	/**
	 * @param compressed
	 *            the compressed to set
	 */
	public void setCompressed(boolean compressed) {
		this.compressed = compressed;
	}

}
