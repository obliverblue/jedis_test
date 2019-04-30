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

import java.util.List;
import java.util.Map;
import java.util.Set;


public class JedisHashCommands implements RedisHashCommands
{

	private final JedisConnection connection;

	public JedisHashCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public Boolean hSet(byte[] key, byte[] field, byte[] value)
	{
		return null;
	}

	@Override
	public Boolean hSetNX(byte[] key, byte[] field, byte[] value)
	{
		return null;
	}

	@Override
	public void hMSet(byte[] key, Map<byte[], byte[]> hashes)
	{

	}

	@Override
	public byte[] hGet(byte[] key, byte[] field)
	{
		return new byte[0];
	}

	@Override
	public List<byte[]> hMGet(byte[] key, byte[]... fields)
	{
		return null;
	}

	@Override
	public Long hIncrBy(byte[] key, byte[] field, long delta)
	{
		return null;
	}

	@Override
	public Double hIncrBy(byte[] key, byte[] field, double delta)
	{
		return null;
	}

	@Override
	public Boolean hExists(byte[] key, byte[] field)
	{
		return null;
	}

	@Override
	public Long hDel(byte[] key, byte[]... fields)
	{
		return null;
	}

	@Override
	public Long hLen(byte[] key)
	{
		return null;
	}

	@Override
	public Long hStrLen(byte[] key, byte[] field)
	{
		return null;
	}

	@Override
	public Set<byte[]> hKeys(byte[] key)
	{
		return null;
	}

	@Override
	public List<byte[]> hValues(byte[] key)
	{
		return null;
	}

	@Override
	public Map<byte[], byte[]> hGetAll(byte[] key)
	{
		return null;
	}
}
