package connection;


import support.RedisDataType;
import util.Assert;

import java.util.Set;

/**
 * @since 2019/4/23
 */
public class JedisKeyCommands implements RedisKeyCommands
{
	private final JedisConnection connection;

	public JedisKeyCommands(JedisConnection connection) {
		if(connection == null) {
			throw new NullPointerException("connection is marked @NonNull but is null");
		} else {
			this.connection = connection;
		}
	}

	@Override
	public Set<byte[]> keys(byte[] pattern)
	{
		Assert.notNull(pattern, "Key must not be null!");
		return connection.getConnection().keys(pattern);
	}

	@Override
	public Long exists(byte[]... keys)
	{
		Assert.notNull(keys, "Keys must not be null!");
		Assert.noNullElements(keys, "Keys must not contain null elements!");
		return connection.getConnection().exists(keys);
	}

	@Override
	public Long del(byte[]... keys)
	{
		Assert.notNull(keys, "Keys must not be null!");
		Assert.noNullElements(keys, "Keys must not contain null elements!");
		return connection.getConnection().del(keys);
	}

	@Override
	public Long unlink(byte[]... keys)
	{
		Assert.notNull(keys, "Keys must not be null!");
		Assert.noNullElements(keys, "Keys must not contain null elements!");
		return connection.getConnection().unlink(keys);
	}

	@Override
	public RedisDataType type(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return RedisDataType.getRedisDataType(connection.getConnection().type(key));
	}

	@Override
	public Boolean expire(byte[] key, int seconds)
	{
		Assert.notNull(key, "Key must not be null!");
		Long ret = connection.getConnection().expire(key, seconds);
		return ret != null && ret == 1 ? true : false;
	}

	@Override
	public Boolean expireAt(byte[] key, long timestamp)
	{
		Assert.notNull(key, "Key must not be null!");
		Long ret = connection.getConnection().expireAt(key, timestamp);
		return ret != null && ret == 1 ? true : false;
	}

	@Override
	public Boolean pExpire(byte[] key, long milliseconds)
	{
		Assert.notNull(key, "Key must not be null!");
		Long ret = connection.getConnection().pexpire(key, milliseconds);
		return ret != null && ret == 1 ? true : false;
	}

	@Override
	public Boolean pExpireAt(byte[] key, long timestamp)
	{
		Assert.notNull(key, "Key must not be null!");
		Long ret = connection.getConnection().pexpireAt(key, timestamp);
		return ret != null && ret == 1 ? true : false;
	}

	@Override
	public Long ttl(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().ttl(key);
	}

	@Override
	public Long pTtl(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		return connection.getConnection().pttl(key);
	}

	@Override
	public byte[] randomkey()
	{
		return connection.getConnection().randomBinaryKey();
	}

	@Override
	public Boolean persist(byte[] key)
	{
		Assert.notNull(key, "Key must not be null!");
		Long ret = connection.getConnection().persist(key);
		return ret != null && ret == 1 ? true : false;
	}
}
