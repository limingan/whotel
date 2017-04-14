package com.whotel.common.serializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.BigDecimalSerializer;
import com.esotericsoftware.kryo.serialize.BigIntegerSerializer;
import com.esotericsoftware.kryo.serialize.DateSerializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.ClassSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.CopyForIterateCollectionSerializer;
import de.javakaffee.kryoserializers.CopyForIterateMapSerializer;
import de.javakaffee.kryoserializers.CurrencySerializer;
import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.EnumSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.StringBufferSerializer;
import de.javakaffee.kryoserializers.StringBuilderSerializer;
import de.javakaffee.kryoserializers.SubListSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import de.javakaffee.web.msm.serializer.kryo.KryoCustomization;
import de.javakaffee.web.msm.serializer.kryo.UnregisteredClassHandler;

public class KryoTranscoder extends AbstractTranscoder implements Transcoder {
	private static final Logger logger = LoggerFactory.getLogger(KryoTranscoder.class);

	public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100 * 1024;
	public static final int DEFAULT_MAX_BUFFER_SIZE = 2000 * 1024;

	private final Kryo _kryo;
	private final UnregisteredClassHandler[] _unregisteredClassHandlers;

	private final int _initialBufferSize;
	private final int _maxBufferSize;

	/**
	 * 
	 */
	public KryoTranscoder() {
		this(null, null, false);
	}

	/**
	 * @param classLoader
	 * @param copyCollectionsForSerialization
	 * @param customConverterClassNames
	 */
	public KryoTranscoder(final ClassLoader classLoader, final String[] customConverterClassNames,
			final boolean copyCollectionsForSerialization) {
		this(classLoader, customConverterClassNames, copyCollectionsForSerialization, DEFAULT_INITIAL_BUFFER_SIZE,
				DEFAULT_MAX_BUFFER_SIZE);
	}

	/**
	 * @param classLoader
	 * @param copyCollectionsForSerialization
	 * @param customConverterClassNames
	 */
	public KryoTranscoder(final ClassLoader classLoader, final String[] customConverterClassNames,
			final boolean copyCollectionsForSerialization, final int initialBufferSize, final int maxBufferSize) {
		final Triple<Kryo, UnregisteredClassHandler[]> triple = createKryo(classLoader, customConverterClassNames,
				copyCollectionsForSerialization);
		_kryo = triple.a;
		_unregisteredClassHandlers = triple.c;
		_initialBufferSize = initialBufferSize;
		_maxBufferSize = maxBufferSize;
	}

	@Override
	public Object doDeserialize(final byte[] data) {
		return new ObjectBuffer(_kryo).readClassAndObject(data);
	}

	@Override
	public byte[] doSerialize(Object object) {
		return new ObjectBuffer(_kryo, _initialBufferSize, _maxBufferSize).writeClassAndObject(object);
	}

	private Triple<Kryo, UnregisteredClassHandler[]> createKryo(final ClassLoader classLoader,
			final String[] customConverterClassNames, final boolean copyCollectionsForSerialization) {

		final Kryo kryo = new KryoReflectionFactorySupport() {

			@Override
			public Serializer newSerializer(@SuppressWarnings("rawtypes") final Class clazz) {
				if (EnumSet.class.isAssignableFrom(clazz)) {
					return new EnumSetSerializer(this);
				}
				if (EnumMap.class.isAssignableFrom(clazz)) {
					return new EnumMapSerializer(this);
				}
				if (SubListSerializer.canSerialize(clazz)) {
					return new SubListSerializer(this);
				}
				if (copyCollectionsForSerialization) {
					final Serializer copyCollectionSerializer = loadCopyCollectionSerializer(clazz, this);
					if (copyCollectionSerializer != null) {
						return copyCollectionSerializer;
					}
				}
				return super.newSerializer(clazz);
			}

			@Override
			protected void handleUnregisteredClass(@SuppressWarnings("rawtypes") final Class clazz) {
				if (_unregisteredClassHandlers != null) {
					for (int i = 0; i < _unregisteredClassHandlers.length; i++) {
						final boolean handled = _unregisteredClassHandlers[i].handleUnregisteredClass(clazz);
						if (handled) {
							if (logger.isDebugEnabled()) {
								logger.debug("UnregisteredClassHandler "
										+ _unregisteredClassHandlers[i].getClass().getName() + " handled class "
										+ clazz);
							}
							return;
						}
					}
				}
				super.handleUnregisteredClass(clazz);
			}

		};

		if (classLoader != null) {
			kryo.setClassLoader(classLoader);
		}

		// com.esotericsoftware.minlog.Log.TRACE = true;

		kryo.setRegistrationOptional(true);
		kryo.register(ArrayList.class);
		kryo.register(LinkedList.class);
		kryo.register(HashSet.class);
		kryo.register(HashMap.class);
		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer(kryo));
		kryo.register(Currency.class, new CurrencySerializer(kryo));
		kryo.register(StringBuffer.class, new StringBufferSerializer(kryo));
		kryo.register(StringBuilder.class, new StringBuilderSerializer(kryo));
		kryo.register(Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer());
		kryo.register(Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer());
		kryo.register(Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer());
		kryo.register(Collections.singletonList("").getClass(), new CollectionsSingletonListSerializer(kryo));
		kryo.register(Collections.singleton("").getClass(), new CollectionsSingletonSetSerializer(kryo));
		kryo.register(Collections.singletonMap("", "").getClass(), new CollectionsSingletonMapSerializer(kryo));
		kryo.register(Class.class, new ClassSerializer(kryo));
		kryo.register(Date.class, new DateSerializer());
		kryo.register(BigDecimal.class, new BigDecimalSerializer());
		kryo.register(BigInteger.class, new BigIntegerSerializer());
		kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
		kryo.register(Timestamp.class, new TimestampSerializer());
		kryo.register(InvocationHandler.class, new JdkProxySerializer(kryo));
		UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		SynchronizedCollectionsSerializer.registerSerializers(kryo);

		final Triple<KryoCustomization[], UnregisteredClassHandler[]> pair = loadCustomConverter(
				customConverterClassNames, classLoader, kryo);

		final KryoCustomization[] customizations = pair.a;
		if (customizations != null) {
			for (final KryoCustomization customization : customizations) {
				try {
					logger.info("Executing KryoCustomization " + customization.getClass().getName());
					customization.customize(kryo);
				} catch (final Exception e) {
					logger.error("Could not execute customization " + customization, e);
				}
			}
		}

		return Triple.create(kryo, pair.c);
	}

	private Serializer loadCopyCollectionSerializer(final Class<?> clazz, final Kryo kryo) {
		if (Collection.class.isAssignableFrom(clazz)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Loading CopyForIterateCollectionSerializer for class " + clazz);
			}
			return new CopyForIterateCollectionSerializer(kryo);
		}
		if (Map.class.isAssignableFrom(clazz)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Loading CopyForIterateMapSerializer for class " + clazz);
			}
			return new CopyForIterateMapSerializer(kryo);
		}
		return null;
	}

	private Triple<KryoCustomization[], UnregisteredClassHandler[]> loadCustomConverter(
			final String[] customConverterClassNames, final ClassLoader classLoader, final Kryo kryo) {
		if (customConverterClassNames == null || customConverterClassNames.length == 0) {
			return Triple.empty();
		}
		final List<KryoCustomization> customizations = new ArrayList<KryoCustomization>();
		final List<UnregisteredClassHandler> unregisteredClassHandlers = new ArrayList<UnregisteredClassHandler>();
		final ClassLoader loader = classLoader != null ? classLoader : Thread.currentThread().getContextClassLoader();
		for (int i = 0; i < customConverterClassNames.length; i++) {
			final String element = customConverterClassNames[i];
			try {
				processElement(element, customizations, unregisteredClassHandlers, kryo, loader);
			} catch (final Exception e) {
				logger.error("Could not instantiate " + element
						+ ", omitting this KryoCustomization/SerializerFactory.", e);
				throw new RuntimeException("Could not load serializer " + element, e);
			}
		}
		final KryoCustomization[] customizationsArray = customizations.toArray(new KryoCustomization[customizations
				.size()]);
		final UnregisteredClassHandler[] unregisteredClassHandlersArray = unregisteredClassHandlers
				.toArray(new UnregisteredClassHandler[unregisteredClassHandlers.size()]);
		return Triple.create(customizationsArray, unregisteredClassHandlersArray);
	}

	private void processElement(final String element, final List<KryoCustomization> customizations,
			final List<UnregisteredClassHandler> unregisteredClassHandlers, final Kryo kryo, final ClassLoader loader)
			throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
			InvocationTargetException {
		final Class<?> clazz = Class.forName(element, true, loader);
		if (KryoCustomization.class.isAssignableFrom(clazz)) {
			logger.info("Loading KryoCustomization " + element);
			final KryoCustomization customization = createInstance(clazz.asSubclass(KryoCustomization.class), kryo);
			customizations.add(customization);

		}
		if (UnregisteredClassHandler.class.isAssignableFrom(clazz)) {
			logger.info("Loading UnregisteredClassHandler " + element);
			final UnregisteredClassHandler handler = createInstance(clazz.asSubclass(UnregisteredClassHandler.class),
					kryo);
			unregisteredClassHandlers.add(handler);
		}
	}

	private static <T> T createInstance(final Class<? extends T> clazz, final Kryo kryo) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException {
		try {
			final Constructor<? extends T> constructor = clazz.getConstructor(Kryo.class);
			return constructor.newInstance(kryo);
		} catch (final NoSuchMethodException nsme) {
			final Constructor<? extends T> constructor = clazz.getConstructor();
			return constructor.newInstance();
		}
	}

	private static class Triple<A, C> {
		private static final Triple<?, ?> EMPTY = Triple.create(null, null);
		private final A a;
		private final C c;

		public Triple(final A a, final C c) {
			this.a = a;
			this.c = c;
		}

		public static <A, C> Triple<A, C> create(final A a, final C c) {
			return new Triple<A, C>(a, c);
		}

		@SuppressWarnings("unchecked")
		public static <A, C> Triple<A, C> empty() {
			return (Triple<A, C>) EMPTY;
		}
	}

}
