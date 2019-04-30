package connection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.Pool;

/**
 * @since 2019/4/29
 */
public class JedisConnectionFactory implements RedisConnectionFactory<Jedis, JedisCluster>
{

	private Pool<Jedis> pool;

	private final JedisClientConfiguration clientConfiguration;


	public JedisConnectionFactory()
	{
		this(new DefaultJedisClientConfiguration(new GenericObjectPoolConfig()));
	}

	public JedisConnectionFactory(JedisClientConfiguration clientConfiguration)
	{
		this.clientConfiguration = clientConfiguration;
	}


	public void init()
	{
		if(clientConfiguration.isUsingPool())
		{
			createPool();
		}
	}

	private void createPool()
	{
		this.pool = new JedisPool(
				clientConfiguration.getPoolConfig(),
				clientConfiguration.getHost(),
				clientConfiguration.getPort(),
				clientConfiguration.getConnectionTimeout(),
				clientConfiguration.getSoTimeout(),
				clientConfiguration.getPassword(),
				clientConfiguration.getDatabase(),
				clientConfiguration.getClientName(),
				clientConfiguration.isUsingSsl(),
				clientConfiguration.getSslSocketFactory().orElse(null),
				clientConfiguration.getSslParameters().orElse(null),
				clientConfiguration.getHostnameVerifier().orElse(null)
		);
	}


	private Jedis fetchJedis()
	{
		if(clientConfiguration.isUsingPool())
		{
			return pool.getResource();
		}

		return createJedis();
	}


	private Jedis createJedis()
	{
		return new Jedis(
				clientConfiguration.getHost(),
				clientConfiguration.getPort(),
				clientConfiguration.getConnectionTimeout(),
				clientConfiguration.getSoTimeout(),
				clientConfiguration.isUsingSsl(),
				clientConfiguration.getSslSocketFactory().orElse(null),
				clientConfiguration.getSslParameters().orElse(null),
				clientConfiguration.getHostnameVerifier().orElse(null)
		);
	}

	@Override
	public RedisConnection<Jedis> getConnection()
	{
		Jedis jedis = fetchJedis();
		JedisConnection connection = new JedisConnection(jedis);
		return connection;
	}


	@Override
	public RedisClusterConnection<JedisCluster> getClusterConnection()
	{

		return null;
	}
}
