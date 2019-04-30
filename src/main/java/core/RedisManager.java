package core;


import connection.JedisClientConfiguration;
import connection.JedisConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @since  2019/4/29
 */
public class RedisManager
{

	private static RedisTemplate redisTemplate;

	private static Map<String, StringOperation>  stringOperationMap = new HashMap<>();

	public static void init(JedisClientConfiguration clientConfiguration)
	{
		JedisConnectionFactory factory = new JedisConnectionFactory(clientConfiguration);
		factory.init();
		redisTemplate = new RedisTemplate(factory);
		register();
	}


	private static void register()
	{
		DefaultStringOpreation<Integer, Integer> stringOpreation = new DefaultStringOpreation("test", redisTemplate, Integer.class, Integer.class);
		stringOperationMap.put("test", stringOpreation);
	}


	public static void registerStringOperation(String prefix, StringOperation stringOperation)
	{
		if(!stringOperationMap.containsKey(prefix))
		{
			stringOperationMap.put(prefix, stringOperation);
		}
	}


	public static StringOperation getStringOperation(String prefix)
	{
		return stringOperationMap.get(prefix);
	}


	public static RedisTemplate getRedisTemplate()
	{
		return redisTemplate;
	}
}
