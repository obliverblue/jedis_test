package core;

import connection.RedisConnection;
import serializer.GenericJackson2JsonRedisSerializer;
import serializer.Serializer;
import serializer.StringSerializer;
import util.Assert;

import java.util.List;

/**
 * @since 2019/4/29
 */
public abstract class AbstractOperation<K, V>
{
	private Serializer keySerializer = StringSerializer.UTF_8;
	private Serializer valueSerializer = GenericJackson2JsonRedisSerializer.jsonSerializer;

	protected final String keyPrefix;

	protected final RedisTemplate redisTemplate;

	protected final Class<K> keyClass;

	protected final Class<V> valueClass;

	protected AbstractOperation(String keyPrefix, RedisTemplate redisTemplate, Class<K> keyClass, Class<V> valueClass)
	{
		Assert.notNull(keyPrefix, "non null key prefix required");
		this.keyPrefix = keyPrefix;
		this.redisTemplate = redisTemplate;
		this.keyClass = keyClass;
		this.valueClass = valueClass;
	}


	<T> T execute(RedisCallback<T> callback)
	{
		return redisTemplate.execute(callback);
	}

	protected String makeKey(K key)
	{
		Assert.notNull(key, "non null key required");
		return keyPrefix + "_" + key;
	}

	@SuppressWarnings("unchecked")
	protected byte[] keyToBytes(K key)
	{
		Assert.notNull(key, "non null key required");
		return keySerializer.serialize(makeKey(key));
	}

	@SuppressWarnings("unchecked")
	protected byte[] valueToBytes(V value)
	{
		Assert.notNull(value, "non null value required");
		if(value instanceof byte[])
		{
			return (byte[]) value;
		}
		return valueSerializer.serialize(value);
	}


	@SuppressWarnings("unchecked")
	protected V deserializeValue(byte[] value) {
		return (V) valueSerializer.deserialize(value, valueClass);
	}


}
