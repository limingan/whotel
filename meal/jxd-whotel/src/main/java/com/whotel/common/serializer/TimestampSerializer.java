package com.whotel.common.serializer;

import java.nio.ByteBuffer;
import java.sql.Timestamp;

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.LongSerializer;
import com.esotericsoftware.minlog.Log;

/**
 * @author
 * 
 */
public class TimestampSerializer extends Serializer {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Timestamp readObjectData(ByteBuffer buffer, Class type) {
		Timestamp timestamp = new Timestamp(LongSerializer.get(buffer, true));
		if (Log.TRACE)
			Log.trace("kryo", "Read timestamp: " + timestamp);
		return timestamp;
	}

	@Override
	public void writeObjectData(ByteBuffer buffer, Object object) {
		LongSerializer.put(buffer, ((Timestamp) object).getTime(), true);
		if (Log.TRACE)
			Log.trace("kryo", "Wrote timestamp: " + object);
	}
}
