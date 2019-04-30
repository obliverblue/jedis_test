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


import support.ReturnType;

import java.util.List;


public class JedisScriptingCommands implements RedisScriptingCommands
{

	private final JedisConnection connection;

	public JedisScriptingCommands(JedisConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public <T> T eval(byte[] scripts, ReturnType returnType, int numkeys, byte[]... keysAndArgs)
	{
		return null;
	}

	@Override
	public <T> T evalSha(String scriptSha, ReturnType returnType, int numkeys, byte[]... keysAndArgs)
	{
		return null;
	}

	@Override
	public void scriptFlush()
	{

	}

	@Override
	public void scriptKill()
	{

	}

	@Override
	public String scriptLoad(byte[] script)
	{
		return null;
	}

	@Override
	public List<Boolean> scriptExists(String... scriptShas)
	{
		return null;
	}
}
