package core;

import redis.clients.jedis.Tuple;
import support.DefaultTypedTuple;
import support.ScoreRange;
import support.TypedTuple;

import java.util.*;

/**
 * @since 2019/4/30
 */
public class DefaultZSetOperation<K, V> extends AbstractOperation<K, V> implements ZSetOperation<K, V>
{

	protected Set<Tuple> EMPTY_TUPLE_SET = new HashSet<>(0);

	public DefaultZSetOperation(String keyPrefix, RedisTemplate redisTemplate, Class<K> keyClass, Class<V> valueClass)
	{
		super(keyPrefix, redisTemplate, keyClass, valueClass);
	}

	@Override
	public Boolean zAdd(K key, double score, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zAdd(rawKey, score, rawValue) == 1, false);
	}

	@Override
	public Long zAdd(K key, Map<V, Double> tuples)
	{
		if(tuples == null || tuples.isEmpty())
		{
			return -1L;
		}

		byte[] rawKey = keyToBytes(key);
		Set<Tuple> rawTuples = new HashSet<>(tuples.size());
		for(Map.Entry<V, Double> entry : tuples.entrySet())
		{
			byte[] rawValue = valueToBytes(entry.getKey());
			Tuple tuple = new Tuple(rawValue, entry.getValue());
			rawTuples.add(tuple);
		}
		return execute(conn -> conn.zSetCommands().zAdd(rawKey, rawTuples), -1L);
	}

	@Override
	public Boolean remove(K key, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zRem(rawKey, rawValue) == 1, false);
	}

	@Override
	public Long remove(K key, Collection<V> members)
	{
		if(members == null || members.isEmpty())
		{
			return -1L;
		}
		byte[] rawKey = keyToBytes(key);
		byte[][] rawMembers = new byte[members.size()][];
		int index = 0;
		for(V member : members)
		{
			rawMembers[index++] = valueToBytes(member);
		}

		return execute(conn -> conn.zSetCommands().zRem(rawKey, rawMembers), -1L);
	}

	@Override
	public Long zRemRangeByScore(K key, ScoreRange scoreRange)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.zSetCommands().zRemRangeByScore(rawKey, scoreRange), -1L);
	}

	@Override
	public Long zRemRangeByRank(K key, long start, long end)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.zSetCommands().zRemRangeByRank(rawKey, start, end), -1L);
	}

	@Override
	public Double zScore(K key, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zScore(rawKey, rawValue), -1d);
	}

	@Override
	public Double zIncrBy(K key, double increment, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zIncrBy(rawKey, increment, rawValue), Double.MAX_VALUE);
	}

	@Override
	public Long zCard(K key)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.zSetCommands().zCard(rawKey), -1L);
	}

	@Override
	public Long zCount(K key, ScoreRange scoreRange)
	{
		byte[] rawKey = keyToBytes(key);
		return execute(conn -> conn.zSetCommands().zCount(rawKey, scoreRange), -1L);
	}

	@Override
	public Set<V> zRange(K key, long start, long end)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRange(rawKey, start, end), EMPRT_SET);
		return convert(ret0);
	}

	@Override
	public Set<V> zRevRange(K key, long start, long end)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRevRange(rawKey, start, end), EMPRT_SET);
		return convert(ret0);
	}

	@Override
	public Set<V> zRangeByScore(K key, double min, double max)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRangeByScore(rawKey, min, max), EMPRT_SET);
		return convert(ret0);
	}

	@Override
	public Set<V> zRangeByScore(K key, double min, double max, long offset, long count)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRangeByScore(rawKey, min, max, offset, count), EMPRT_SET);
		return convert(ret0);
	}

	@Override
	public Set<V> zRevRangeByScore(K key, double min, double max)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRevRangeByScore(rawKey, min, max), EMPRT_SET);
		return convert(ret0);
	}

	@Override
	public Set<V> zRevRangeByScore(K key, double min, double max, long offset, long count)
	{
		byte[] rawKey = keyToBytes(key);
		Set<byte[]> ret0 = execute(conn -> conn.zSetCommands().zRevRangeByScore(rawKey, min, max, offset, count), EMPRT_SET);
		return convert(ret0);
	}


	private Set<V> convert(Set<byte[]> source)
	{
		Set<V> ret = new LinkedHashSet<>(source.size());
		for(byte[] s : source)
		{
			ret.add(deserializeValue(s));
		}
		return ret;
	}

	@Override
	public Set<TypedTuple<V>> zRangeWithScores(K key, long start, long end)
	{
		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRangeWithScores(rawKey, start, end), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}

	@Override
	public Set<TypedTuple<V>> zRangeByScoreWithScores(K key, double min, double max)
	{
		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRangeByScoreWithScores(rawKey, min, max), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}

	@Override
	public Set<TypedTuple<V>> zRangeByScoreWithScores(K key, double min, double max, long offset, long count)
	{
		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRangeByScoreWithScores(rawKey, min, max, offset, count), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}

	@Override
	public Set<TypedTuple<V>> zRevRangeWithScores(K key, long start, long end)
	{

		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRevRangeWithScores(rawKey, start, end), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}

	@Override
	public Set<TypedTuple<V>> zRevRangeByScoreWithScores(K key, double min, double max)
	{
		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRevRangeByScoreWithScores(rawKey, min, max), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}

	@Override
	public Set<TypedTuple<V>> zRevRangeByScoreWithScores(K key, double min, double max, long offset, long count)
	{
		byte[] rawKey = keyToBytes(key);
		Set<Tuple> ret0 = execute(conn -> conn.zSetCommands().zRevRangeByScoreWithScores(rawKey, min, max, offset, count), EMPTY_TUPLE_SET);
		return convertTuple(ret0);
	}


	private Set<TypedTuple<V>> convertTuple(Set<Tuple> source)
	{
		Set<TypedTuple<V>> ret = new LinkedHashSet<>(source.size());
		for(Tuple tuple : source)
		{
			V value = deserializeValue(tuple.getBinaryElement());
			TypedTuple<V> typedTuple = new DefaultTypedTuple<>(value, tuple.getScore());
			ret.add(typedTuple);
		}
		return ret;
	}

	/**
	 * base-1
	 * @param key
	 * @param member
	 * @return
	 */
	@Override
	public Long zRank(K key, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zRank(rawKey, rawValue),  -2L) + 1;
	}

	@Override
	public Long zRevRank(K key, V member)
	{
		byte[] rawKey = keyToBytes(key);
		byte[] rawValue = valueToBytes(member);
		return execute(conn -> conn.zSetCommands().zRevRank(rawKey, rawValue),  -2L) + 1;
	}
}
