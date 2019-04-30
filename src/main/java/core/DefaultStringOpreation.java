package core;


import connection.RedisConnection;
import connection.RedisStringCommands.SetOption;
import support.Expiration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @since 2019/4/29.
 */
public class DefaultStringOpreation<K, V> extends AbstractOperation<K, V> implements StringOperation<K, V>
{

	protected DefaultStringOpreation(String keyPrefix, RedisTemplate redisTemplate, Class<K> keyClass, Class<V> valueClass)
	{
		super(keyPrefix, redisTemplate, keyClass, valueClass);
	}

	@Override
	public V get(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return deserializeValue(execute(connection -> connection.stringCommands().get(rawKey), EMPTY_ARRAY));
	}

	@Override
	public V getSet(K key, V value)
	{
		return null;
	}

	@Override
	public List<V> mGet(Collection<K> keys)
	{
		if(keys.isEmpty())
		{
			return Collections.emptyList();
		}

		byte[][] rawKeys = new byte[keys.size()][];
		int counter = 0;
		for(K hashKey : keys)
		{
			rawKeys[counter++] = keyToBytes(hashKey);
		}
		List<byte[]> rawValues = execute(connection -> connection.stringCommands().mGet(rawKeys), EMPTY_LIST);
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
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(connection -> connection.stringCommands().set(rawKey, rawValue), false);
	}

	@Override
	public Boolean set(K key, V value, long timeout, TimeUnit unit)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		Expiration expiration = Expiration.of(timeout, unit);
		return execute(conn -> conn.stringCommands().set(rawKey, rawValue, expiration, SetOption.NONE), false);
	}

	@Override
	public Boolean set(K key, V value, Expiration expiration, SetOption option)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().set(rawKey, rawValue, expiration, option), false);
	}

	@Override
	public Boolean setIfAbsent(K key, V value)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().setNX(rawKey, rawValue), false);
	}

	@Override
	public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		Expiration expiration = Expiration.of(timeout, unit);
		return execute(conn -> conn.stringCommands().set(rawKey, rawValue, expiration, SetOption.SET_IF_ABSENT), false);
	}

	@Override
	public Boolean setIfPresent(K key, V value)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().set(rawKey, rawValue, Expiration.persistent(), SetOption.SET_IF_PRESENT), false);
	}

	@Override
	public Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		Expiration expiration = Expiration.of(timeout, unit);
		return execute(conn -> conn.stringCommands().set(rawKey, rawValue, expiration, SetOption.SET_IF_PRESENT), false);
	}

	@Override
	public Boolean setExpire(K key, long seconds, V value)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().setEx(rawKey, seconds, rawValue), false);
	}

	@Override
	public Boolean pSetExpire(K key, long millseconds, V value)
	{

		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().pSetEx(rawKey, millseconds, rawValue), false);
	}

	@Override
	public Boolean mSet(Map<? extends K, ? extends V> tuples)
	{
		if(tuples == null || tuples.isEmpty())
		{
			return false;
		}
		Map<byte[], byte[]> rawMap = new HashMap<>(tuples.size());
		for(Map.Entry<? extends K, ? extends V> entry : tuples.entrySet())
		{
			byte[] rawKey = keyToBytes(entry.getKey());
			byte[] rawValue = valueToBytes(entry.getValue());
			rawMap.put(rawKey, rawValue);
		}
		return execute(conn -> conn.stringCommands().mSet(rawMap), false);
	}

	@Override
	public Boolean mSetIfAbsent(Map<? extends K, ? extends V> tuples)
	{
		if(tuples == null || tuples.isEmpty())
		{
			return false;
		}
		Map<byte[], byte[]> rawMap = new HashMap<>(tuples.size());
		for(Map.Entry<? extends K, ? extends V> entry : tuples.entrySet())
		{
			byte[] rawKey = keyToBytes(entry.getKey());
			byte[] rawValue = valueToBytes(entry.getValue());
			rawMap.put(rawKey, rawValue);
		}
		return execute(conn -> conn.stringCommands().mSetNX(rawMap), false);
	}

	@Override
	public Long incr(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().incr(rawKey), 0L);
	}

	@Override
	public Long incrBy(K key, long delta)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().incrBy(rawKey, delta), 0L);
	}

	@Override
	public Double incrBy(K key, double delta)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().incrByFloat(rawKey, delta), 0D);
	}

	@Override
	public Long decr(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().decr(rawKey), 0L);
	}

	@Override
	public Long decrBy(K key, long delta)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().decrBy(rawKey, delta), 0L);
	}

	/**
	 * 返回字符串的长度
	 *
	 * @param key
	 * @param value
	 * @return -1：操作失败
	 */
	@Override
	public Long append(K key, V value)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		return execute(conn -> conn.stringCommands().append(rawKey, rawValue), -1L);
	}

	@Override
	public String getRange(K key, long start, long end)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] ret = execute(conn -> conn.stringCommands().getRange(rawKey, start, end), EMPTY_ARRAY);
		return deserializeValueToString(ret);
	}

	@Override
	public void setRange(K key, V value, long offset)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(value);
		execute(new RedisCallback<Object>()
		        {
			        @Override
			        public Object exec(RedisConnection connection)
			        {
				        connection.stringCommands().setRange(rawKey, rawValue, offset);
				        return null;
			        }
		        }
				, null);
	}

	@Override
	public Long strLen(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.stringCommands().strLen(rawKey), -1L);
	}
}
