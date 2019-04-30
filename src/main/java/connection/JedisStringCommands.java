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


import redis.clients.jedis.params.SetParams;
import support.Expiration;
import util.Assert;
import util.ObjectUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JedisStringCommands implements RedisStringCommands
{

	private final JedisConnection connection;

	public JedisStringCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public byte[] get(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().get(key);
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().getSet(key, value);
	}

	@Override
	public List<byte[]> mGet(byte[]... keys)
	{
		Assert.notNull(keys, "Keys must not be null!");
		Assert.noNullElements(keys, "Keys must not contain null elements!");
		return connection.getConnection().mget(keys);
	}

	@Override
	public Boolean set(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		String ret = connection.getConnection().set(key, value);
		return "OK".equals(ret) ? true : false;
	}

	@Override
	public Boolean set(byte[] key, byte[] value, Expiration expiration, SetOption option)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		Assert.notNull(expiration, "Expiration must not be null!");
		Assert.notNull(option, "Option must not be null!");
		String ret = connection.getConnection().set(key, value, getSetParams(expiration, option));
		return "OK".equals(ret) ? true : false;
	}

	private SetParams getSetParams(Expiration expiration, SetOption option)
	{
		SetParams setParams = SetParams.setParams();
		if(expiration.isPersistent() == false)
		{
			Expiration expir = Expiration.of(expiration.getExpirationTime(), expiration.getUnit());
			long expirationTime = expir.getExpirationTime();
			if(ObjectUtils.objectEquals(expir.getUnit(), TimeUnit.MILLISECONDS))
			{
				setParams.px(expirationTime);
			}
			else
			{
				setParams.ex((int)expirationTime);
			}
		}
		if(option == SetOption.SET_IF_ABSENT)
		{
			setParams.nx();
		}

		if(option == SetOption.SET_IF_PRESENT)
		{
			setParams.xx();
		}
		return setParams;
	}

	@Override
	public Boolean setNX(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		Long ret = connection.getConnection().setnx(key, value);
		return ret != null || ret == 1  ? true : false;
	}

	@Override
	public Boolean setEx(byte[] key, long seconds, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		String ret = connection.getConnection().setex(key, (int)seconds, value);
		return "OK".equals(ret) ? true : false;
	}

	@Override
	public Boolean pSetEx(byte[] key, long millseconds, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		String ret = connection.getConnection().psetex(key, millseconds, value);
		return "OK".equals(ret) ? true : false;
	}

	@Override
	public Boolean mSet(Map<byte[], byte[]> tuples)
	{
		Assert.notNull(tuples, "Tuples must not be null!");
		String ret = connection.getConnection().mset(toByteArrays(tuples));
		return "OK".equals(ret) ? true : false;
	}

	@Override
	public Boolean mSetNX(Map<byte[], byte[]> tuples)
	{
		Assert.notNull(tuples, "Tuples must not be null!");
		Long ret =  connection.getConnection().msetnx(toByteArrays(tuples));
		return ret != null || ret == 1  ? true : false;
	}

	private byte[][] toByteArrays(Map<byte[], byte[]> tuples)
	{
		byte[][] result = new byte[tuples.size() * 2][];
		int index = 0;
		for(Map.Entry<byte[], byte[]> entry : tuples.entrySet())
		{
			result[index++] = entry.getKey();
			result[index++] = entry.getValue();
		}
		return result;
	}

	@Override
	public Long incr(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().incr(key);
	}

	@Override
	public Long incrBy(byte[] key, long delta)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().incrBy(key, delta);
	}

	@Override
	public Double incrByFloat(byte[] key, double delta)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().incrByFloat(key, delta);
	}

	@Override
	public Long decr(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().decr(key);
	}

	@Override
	public Long decrBy(byte[] key, long delta)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().decrBy(key, delta);
	}

	@Override
	public Long append(byte[] key, byte[] value)
	{
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(value, "Value must not be null!");
		return connection.getConnection().append(key, value);
	}

	@Override
	public byte[] getRange(byte[] key, long start, long end)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().getrange(key, start, end);
	}

	@Override
	public void setRange(byte[] key, byte[] value, long offset)
	{
		Assert.notNull(key, "Key must not be null!");
		connection.getConnection().setrange(key, offset, value);
	}

	@Override
	public Long strLen(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().strlen(key);
	}
}
