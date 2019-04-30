package connection;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

/**
 * jedis
 *
 * @author oliverblue
 * @since 2019/4/25
 */
public class JedisConnection implements RedisConnection<Jedis>
{

	/**
	 * jedis
	 */
	private final Jedis jedis;

	/**
	 *
	 */
	private final int dbIndex;

	/**
	 *
	 */
	private final String clientName;

	public JedisConnection(Jedis jedis)
	{
		this(jedis, 0);
	}

	public JedisConnection(Jedis jedis, int dbIndex)
	{
		this(jedis, dbIndex, null);
	}

	protected JedisConnection(Jedis jedis, int dbIndex, String clientName)
	{

		this.jedis = jedis;
		this.dbIndex = dbIndex;
		this.clientName = clientName;

		// select the db
		// if this fail, do manual clean-up before propagating the exception
		// as we're inside the constructor
		if(dbIndex != jedis.getDB())
		{
			jedis.select(dbIndex);
		}
	}

	@Override
	public Jedis getConnection()
	{
		return jedis;
	}

	@Override
	public void close()
	{
		if(!isClosed())
		{
			jedis.close();
		}
	}

	@Override
	public boolean isClosed()
	{
		return !jedis.isConnected();
	}


	@Override
	public RedisKeyCommands keyCommands()
	{
		return new JedisKeyCommands(this);
	}

	@Override
	public RedisStringCommands stringCommands()
	{
		return new JedisStringCommands(this);
	}

	@Override
	public RedisListCommands listCommands()
	{
		return new JedisListCommands(this);
	}

	@Override
	public RedisHashCommands hashCommands()
	{
		return new JedisHashCommands(this);
	}

	@Override
	public RedisSetCommands setCommands()
	{
		return new JedisSetCommands(this);
	}

	@Override
	public RedisZSetCommands zSetCommands()
	{
		return new JedisZSetCommands(this);
	}

	@Override
	public RedisScriptingCommands scriptingCommands()
	{
		return new JedisScriptingCommands(this);
	}
}
