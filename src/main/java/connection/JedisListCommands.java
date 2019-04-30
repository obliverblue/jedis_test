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


import redis.clients.jedis.ListPosition;
import util.Assert;

import java.util.List;

public class JedisListCommands implements RedisListCommands
{
	private final JedisConnection connection;

	public JedisListCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public Long rPush(byte[] key, byte[]... values)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(values, "Values must not be null!");
		Assert.noNullElements(values, "Values must not contain null elements!");
		return connection.getConnection().rpush(key, values);
	}

	@Override
	public Long rPushX(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().rpushx(key, value);
	}

	@Override
	public Long lPush(byte[] key, byte[]... values)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(values, "Values must not be null!");
		Assert.noNullElements(values, "Values must not contain null elements!");
		return connection.getConnection().lpush(key, values);
	}

	@Override
	public Long lPushX(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().lpushx(key, value);
	}

	@Override
	public byte[] lPop(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().lpop(key);
	}

	@Override
	public byte[] rPop(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().rpop(key);
	}

	@Override
	public byte[] rPopLPush(byte[] srcKey, byte[] destKey)
	{
		Assert.notNull(srcKey, "Source key must not be null!");
		Assert.notNull(destKey, "Destination key must not be null!");
		return  connection.getConnection().rpoplpush(srcKey, destKey);
	}

	@Override
	public List<byte[]> bLPop(int timeout, byte[]... keys)
	{
		return null;
	}

	@Override
	public List<byte[]> bRPop(int timeout, byte[]... keys)
	{
		return null;
	}

	@Override
	public byte[] bRPopLPush(int timeout, byte[] srcKey, byte[] dstKey)
	{
		return new byte[0];
	}

	@Override
	public byte[] lIndex(byte[] key, long index)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().lindex(key, index);
	}

	@Override
	public Long lInsert(byte[] key, Position where, byte[] pivot, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(where, "list positions are mandatory");
		Assert.notNull(value, "Value must not be null!");
		ListPosition position = Position.AFTER.equals(where) ? ListPosition.AFTER : ListPosition.BEFORE;
		return connection.getConnection().linsert(key, position, pivot, value);
	}

	@Override
	public Long lLen(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().llen(key);
	}

	@Override
	public List<byte[]> lRange(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().lrange(key, start, end);
	}

	@Override
	public Long lRem(byte[] key, long count, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().lrem(key, count, value);
	}

	@Override
	public void lSet(byte[] key, long index, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		connection.getConnection().lset(key, index, value);
	}

	@Override
	public void lTrim(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		connection.getConnection().ltrim(key, start, end);
	}
}
