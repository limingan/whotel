package com.whotel.common.serializer;

public interface Transcoder {

	/**
	 * {@inheritDoc}
	 */
	Object deserialize(final byte[] bytes);

	/**
	 * {@inheritDoc}
	 */
	byte[] serialize(final Object object);

}