/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package connection;


import converter.Converters;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.ZAddParams;
import support.Limit;
import support.ScoreRange;
import support.Weights;
import util.Assert;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *  sorted set 基本操作
 */
public class JedisZSetCommands implements RedisZSetCommands
{

	private final JedisConnection connection;

	public JedisZSetCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public Long zAdd(byte[] key, double score, byte[] member)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(member, "Value must not be null!");
		return connection.getConnection().zadd(key, score, member);
	}


	@Override
	public Long zAdd(byte[] key, double score, byte[] member, ZAddOption option)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(member, "Value must not be null!");
		return connection.getConnection().zadd(key, score, member, getZAddParams(option));
	}



	@Override
	public Long zAdd(byte[] key, Set<Tuple> tuples)
	{

		// TODO 需要测试一下 tuples size = 0的情况
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(tuples, "Value must not be null!");
		return connection.getConnection().zadd(key, toTupleMap(tuples));
	}


	@Override
	public Long zAdd(byte[] key, Set<Tuple> tuples, ZAddOption option)
	{
		// TODO 需要测试一下 tuples size = 0的情况
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(tuples, "Value must not be null!");
		return connection.getConnection().zadd(key, toTupleMap(tuples), getZAddParams(option));
	}

	private ZAddParams getZAddParams(ZAddOption option)
	{
		ZAddParams zAddParams = ZAddParams.zAddParams();
		if(option == ZAddOption.ADD_IF_ABSENT)
		{
			zAddParams.nx();
		}

		if(option == ZAddOption.ADD_IF_PRESENT)
		{
			zAddParams.xx();
		}

		if(option == ZAddOption.ADD_IF_ELEMENTS_CHANGED)
		{
			zAddParams.ch();
		}
		return zAddParams;
	}


	private Map<byte[], Double> toTupleMap(Set<Tuple> tuples)
	{
		Map<byte[], Double> args = new LinkedHashMap<>(tuples.size(), 1);
		for(Tuple tuple : tuples)
		{
			args.put(tuple.getBinaryElement(), tuple.getScore());
		}
		return args;
	}



	@Override
	public Double zIncrBy(byte[] key, double increment, byte[] value) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().zincrby(key, increment, value);
	}

	@Override
	public Long zRem(byte[] key, byte[]... members) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(members, "Values must not be null!");
		Assert.noNullElements(members, "Values must not contain null elements!");
		return connection.getConnection().zrem(key, members);
	}


	@Override
	public Long zRemRangeByScore(byte[] key, ScoreRange ScoreRange)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		return connection.getConnection().zremrangeByScore(key, min, max);
	}

	@Override
	public Long zRemRangeByRank(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zremrangeByRank(key, start, end);
	}

	// TODO 未实现  Available since 5.0.0.
	@Override
	public Tuple zPopMax(byte[] key, long count)
	{
		return null;
	}

	// TODO 未实现 Available since 5.0.0.
	@Override
	public Tuple zPopMin(byte[] key, long count)
	{
		return null;
	}

	@Override
	public Double zScore(byte[] key, byte[] member)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(member, "Values must not be null!");
		return connection.getConnection().zscore(key, member);
	}

	@Override
	public Long zCard(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zcard(key);
	}

	@Override
	public Long zCount(byte[] key, ScoreRange ScoreRange)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		return connection.getConnection().zcount(key, min, max);
	}

	@Override
	public Set<byte[]> zRange(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zrange(key, start, end);
	}

	@Override
	public Set<Tuple> zRangeWithScores(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zrangeWithScores(key, start, end);
	}

	@Override
	public Set<byte[]> zRevRange(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zrevrange(key, start, end);
	}

	@Override
	public Set<Tuple> zRevRangeWithScores(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().zrevrangeWithScores(key, start, end);
	}

	@Override
	public Set<byte[]> zRangeByScore(byte[] key, ScoreRange ScoreRange, Limit limit)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		if(limit != null)
		{
			return connection.getConnection().zrangeByScore(key, min, max, limit.getOffset(), limit.getCount());
		}
		return connection.getConnection().zrangeByScore(key, min, max);
	}

	@Override
	public Set<byte[]> zRevRangeByScore(byte[] key, ScoreRange ScoreRange, Limit limit)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		if(limit != null)
		{
			return connection.getConnection().zrevrangeByScore(key, max, min, limit.getOffset(), limit.getCount());
		}
		return connection.getConnection().zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<Tuple> zRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange, Limit limit)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		if(limit != null)
		{
			return connection.getConnection().zrangeByScoreWithScores(key, min, max, limit.getOffset(), limit.getCount());
		}
		return connection.getConnection().zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zRevRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange, Limit limit)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(ScoreRange, "ScoreRange for ZREMScoreRangeBYSCORE must not be null!");
		byte[] min = Converters.boundaryToBytes(ScoreRange.getMin(), Converters.NEGATIVE_INFINITY_BYTES);
		byte[] max = Converters.boundaryToBytes(ScoreRange.getMax(), Converters.POSITIVE_INFINITY_BYTES);
		if(limit != null)
		{
			return connection.getConnection().zrevrangeByScoreWithScores(key, max, min, limit.getOffset(), limit.getCount());
		}
		return connection.getConnection().zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Long zRank(byte[] key, byte[] member)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(member, "Values must not be null!");
		return connection.getConnection().zrank(key, member);
	}

	@Override
	public Long zRevRank(byte[] key, byte[] member)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(member, "Values must not be null!");
		return connection.getConnection().zrevrank(key, member);
	}

	@Override
	public Long zUnionStore(byte[] destination, byte[]... sets)
	{
		Assert.notNull(destination, "Key must not be null!");
		Assert.notNull(sets, "Values must not be null!");
		Assert.noNullElements(sets, "Values must not contain null elements!");
		return connection.getConnection().zunionstore(destination, sets);
	}

	@Override
	public Long zUnionStore(byte[] destination, Aggregate aggregate, Weights weights, byte[]... sets)
	{
		Assert.notNull(destination, "Key must not be null!");
		Assert.notNull(weights, "Weights must not be null!");
		Assert.isTrue(weights.size() == sets.length, () -> String
				.format("The number of weights (%d) must match the number of source sets (%d)!", weights.size(), sets.length));
		Assert.notNull(sets, "Values must not be null!");
		Assert.noNullElements(sets, "Values must not contain null elements!");
		ZParams zparams = new ZParams().weights(weights.toArray())
				.aggregate(ZParams.Aggregate.valueOf(aggregate.name()));
		return connection.getConnection().zunionstore(destination, zparams, sets);
	}

	@Override
	public Long zInterStore(byte[] destination, byte[]... sets)
	{
		Assert.notNull(destination, "Key must not be null!");
		Assert.notNull(sets, "Values must not be null!");
		Assert.noNullElements(sets, "Values must not contain null elements!");
		return connection.getConnection().zinterstore(destination, sets);
	}

	@Override
	public Long zInterStore(byte[] destination, Aggregate aggregate, Weights weights, byte[]... sets)
	{
		Assert.notNull(destination, "Key must not be null!");
		Assert.notNull(weights, "Weights must not be null!");
		Assert.isTrue(weights.size() == sets.length, () -> String
				.format("The number of weights (%d) must match the number of source sets (%d)!", weights.size(), sets.length));
		Assert.notNull(sets, "Values must not be null!");
		Assert.noNullElements(sets, "Values must not contain null elements!");
		ZParams zparams = new ZParams().weights(weights.toArray())
				.aggregate(ZParams.Aggregate.valueOf(aggregate.name()));
		return connection.getConnection().zinterstore(destination, zparams, sets);
	}
}
