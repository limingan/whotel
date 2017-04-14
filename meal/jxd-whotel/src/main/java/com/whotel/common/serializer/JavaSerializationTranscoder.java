package com.whotel.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaSerializationTranscoder extends AbstractTranscoder implements Transcoder {
	private static final Logger logger = LoggerFactory.getLogger(JavaSerializationTranscoder.class);

	@Override
	public Object doDeserialize(final byte[] bytes) {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (final ClassNotFoundException e) {
			logger.warn("Caught CNFE decoding " + bytes.length + " bytes of data", e);
			throw new RuntimeException("Caught CNFE decoding data", e);
		} catch (final IOException e) {
			logger.warn("Caught IOException decoding " + bytes.length + " bytes of data", e);
			throw new RuntimeException("Caught IOException decoding data", e);
		} finally {
			closeSilently(bis);
			closeSilently(ois);
		}
	}

	@Override
	public byte[] doSerialize(final Object object) {
		if (object == null) {
			throw new NullPointerException("Can't serialize null");
		}
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		try {
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		} finally {
			closeSilently(bos);
			closeSilently(bos);
		}
	}

	private void closeSilently(final OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (final IOException f) {
			}
		}
	}

	private void closeSilently(final InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (final IOException f) {
			}
		}
	}
}
