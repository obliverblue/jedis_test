package core;

import connection.RedisConnection;
import connection.RedisStringCommands;
import support.Expiration;
import support.RedisDataType;

import java.util.*;

/**
 * @since  2019/4/29.
 */
public class DefaultStringOpreation<K,V>  extends AbstractOperation<K, V> implements StringOperation<K, V>
{

	protected DefaultStringOpreation(String keyPrefix, RedisTemplate redisTemplate, Class<K> keyClass, Class<V> valueClass)
	{
		super(keyPrefix, redisTemplate, keyClass, valueClass);
	}

	@Override
	public V get(K key)
	{
		byte[] rawKey =  keyToBytes(key);
		return deserializeValue(execute(connection -> connection.stringCommands().get(rawKey)));
	}

	@Override
	public V getSet(K key, V value)
	{
		return null;
	}

	@Override
	public List<V> mGet(Collection<K> keys)
	{
		if (keys.isEmpty()) {
			return Collections.emptyList();
		}

		byte[][] rawKeys = new byte[keys.size()][];
		int counter = 0;
		for (K hashKey : keys) {
			rawKeys[counter++] = keyToBytes(hashKey);
		}
		List<byte[]> rawValues = execute(connection -> connection.stringCommands().mGet(rawKeys));

		List<V> ret = new ArrayList<>(rawValues.size());
		for(byte[] val : rawValues)
		{
			ret.add(deserializeValue(val));
		}
		return ret;
	}

	@Override
	public Boolean set(K key, V value)
	{
		byte[] rawKey =  keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(connection -> connection.stringCommands().set(rawKey, rawValue));
	}

	@Override
	public Boolean set(K key, V value, Expiration expiration, RedisStringCommands.SetOption option)
	{
		return null;
	}

	@Override
	public Boolean setNX(K key, V value)
	{
		return null;
	}

	@Override
	public Boolean setEx(K key, long seconds, V value)
	{
		return null;
	}

	@Override
	public Boolean pSetEx(K key, long millseconds, V value)
	{
		return null;
	}

	@Override
	public Boolean mSet(Map<K, V> tuples)
	{
		return null;
	}

	@Override
	public Boolean mSetNX(Map<K, V> tuples)
	{
		return null;
	}

	@Override
	public Long incr(K key)
	{
		return null;
	}

	@Override
	public Long incrBy(K key, long delta)
	{
		return null;
	}

	@Override
	public Double incrBy(K key, double delta)
	{
		return null;
	}

	@Override
	public Long decr(K key)
	{
		return null;
	}

	@Override
	public Long decrBy(K key, long delta)
	{
		return null;
	}

	@Override
	public Long append(K key, V value)
	{
		return null;
	}

	@Override
	public byte[] getRange(K key, long start, long end)
	{
		return new byte[0];
	}

	@Override
	public void setRange(K key, V value, long offset)
	{

	}

	@Override
	public Long strLen(K key)
	{
		return null;
	}

	@Override
	public Set<K> keys(K pattern)
	{
		return null;
	}

	@Override
	public Long exists(K[] keys)
	{
		return null;
	}

	@Override
	public Long del(K[] keys)
	{
		return null;
	}

	@Override
	public Long unlink(K[] keys)
	{
		return null;
	}

	@Override
	public RedisDataType type(K key)
	{
		return null;
	}

	@Override
	public Boolean expire(K key, int seconds)
	{
		return null;
	}

	@Override
	public Boolean expireAt(K key, long timestamp)
	{
		return null;
	}

	@Override
	public Boolean pExpire(K key, long milliseconds)
	{
		return null;
	}

	@Override
	public Boolean pExpireAt(K key, long timestamp)
	{
		return null;
	}

	@Override
	public Long ttl(K key)
	{
		return null;
	}

	@Override
	public Long pTtl(K key)
	{
		return null;
	}

	@Override
	public K randomkey()
	{
		return null;
	}

	@Override
	public Boolean persist(K key)
	{
		return null;
	}
}
