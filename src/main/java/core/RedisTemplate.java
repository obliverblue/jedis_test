package core;


import connection.JedisConnectionFactory;
import connection.RedisConnection;


/**
 * @since 2019/4/29
 */
public class RedisTemplate
{
	private final JedisConnectionFactory connectionFactory;

	public RedisTemplate(JedisConnectionFactory connectionFactory)
	{
		this.connectionFactory = connectionFactory;
	}

	public <T> T execute(RedisCallback<T> action)
	{
		RedisConnection connection = null;
		try
		{
			connection = connectionFactory.getConnection();
			T ret = action.exec(connection);
			return ret;
		}
		finally
		{
			if(connection != null)
			{
				connection.close();
			}
		}

	}


}
