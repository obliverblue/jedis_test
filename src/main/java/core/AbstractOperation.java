package core;

import serializer.GenericJackson2JsonRedisSerializer;
import serializer.Serializer;
import serializer.StringSerializer;
import util.Assert;

import java.util.*;


/**
 * @since 2019/4/29
 */
public abstract class AbstractOperation<K, V> implements KeyOperation<K>
{
	private Serializer keySerializer = StringSerializer.UTF_8;
	private Serializer valueSerializer = GenericJackson2JsonRedisSerializer.jsonSerializer;
	private Serializer stringValueSerializer = StringSerializer.UTF_8;

	protected byte[] EMPTY_ARRAY = new byte[0];
	protected List<byte[]> EMPTY_LIST = new ArrayList<>(0);
	protected Map<byte[], byte[]> EMPTY_MAP = new HashMap<>(0);
	protected Set<byte[]> EMPRT_SET = new HashSet<>(0);


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


	/**
	 * 返回callback的执行结果
	 * @param callback
	 * @param <T>
	 * @return
	 */
	<T> T execute(RedisCallback<T> callback, T defaultValue)
	{
		return redisTemplate.execute(callback, defaultValue);
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


	protected String deserializeValueToString(byte[] value)
	{
		return (String) stringValueSerializer.deserialize(value);
	}


	@Override
	public Boolean exists(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().exists(rawKey) == 1, false);
	}

	@Override
	public Boolean del(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().del(rawKey) == 1, false);
	}

	@Override
	public Boolean expire(K key, int seconds)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().expire(rawKey, seconds), false);
	}

	@Override
	public Boolean expireAt(K key, long timestamp)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().expireAt(rawKey, timestamp), false);
	}

	@Override
	public Boolean pExpire(K key, long milliseconds)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().pExpire(rawKey, milliseconds), false);
	}

	@Override
	public Boolean pExpireAt(K key, long timestamp)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().pExpireAt(rawKey, timestamp), false);
	}

	@Override
	public Long ttl(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().ttl(rawKey), -1L);
	}

	@Override
	public Long pTtl(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().pTtl(rawKey), -1L);
	}

	@Override
	public String randomkey()
	{
		byte[] ret = execute(conn -> conn.keyCommands().randomkey(), EMPTY_ARRAY);
		if(ret != EMPTY_ARRAY)
		{
			return deserializeValueToString(ret).substring(keyPrefix.length());
		}
		return null;
	}

	/**
	 * 移除给定 key 的过期时间，使得 key 永不过期
	 * @param key
	 * @return
	 */
	@Override
	public Boolean persist(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.keyCommands().persist(rawKey), false);
	}


}
