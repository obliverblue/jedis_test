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
import java.util.Set;


public class JedisSetCommands implements RedisSetCommands
{

	private final JedisConnection connection;

	public JedisSetCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public Long sAdd(byte[] key, byte[]... members)
	{
		return null;
	}

	@Override
	public List<byte[]> sMembers(byte[] key)
	{
		return null;
	}

	@Override
	public Boolean sIsMember(byte[] key, byte[] member)
	{
		return null;
	}

	@Override
	public Long sCard(byte[] key)
	{
		return null;
	}

	@Override
	public Set<byte[]> sUnion(byte[]... keys)
	{
		return null;
	}

	@Override
	public Long sUnionStore(byte[] destination, byte[]... keys)
	{
		return null;
	}

	@Override
	public Set<byte[]> sInter(byte[]... keys)
	{
		return null;
	}

	@Override
	public Long sInterStore(byte[] destination, byte[]... keys)
	{
		return null;
	}

	@Override
	public Set<byte[]> sDiff(byte[]... keys)
	{
		return null;
	}

	@Override
	public Long sDiffStore(byte[] destination, byte[]... keys)
	{
		return null;
	}

	@Override
	public Boolean sMove(byte[] source, byte[] destination, byte[] member)
	{
		return null;
	}

	@Override
	public Set<byte[]> sPop(byte[] key, long count)
	{
		return null;
	}

	@Override
	public byte[] sRandMember(byte[] key)
	{
		return new byte[0];
	}

	@Override
	public List<byte[]> sRandomMember(byte[] key, long count)
	{
		return null;
	}

	@Override
	public Long sRem(byte[] key, byte[]... members)
	{
		return null;
	}
}
