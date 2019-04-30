import connection.DefaultJedisClientConfiguration;
import connection.JedisClientConfiguration;
import core.DefaultStringOpreation;
import core.RedisManager;
import core.RedisTemplate;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/30.
 */
public class RedisManagerTest
{

	public static void main(String[] args)
	{
		JedisClientConfiguration configuration = new DefaultJedisClientConfiguration(new GenericObjectPoolConfig());
		RedisManager.init(configuration);
		List<Integer> keys = new ArrayList<>();
		for(int i = 0; i < 8; i++)
		{
			keys.add(i);
//			System.out.println(RedisManager.getStringOperation("test").set(i, i));
		}


//		for(int i = 0; i < 8; i++)
//		{
//			System.out.println(RedisManager.getStringOperation("test").get(i));
//		}


//		List<Integer> ret = RedisManager.getStringOperation("test").mGet(keys);
//		for(Integer i : ret)
//		{
//			System.out.println(i);
//		}

		RedisTemplate redisTemplate = RedisManager.getRedisTemplate();

		TestStringOperation operation = new TestStringOperation("test1", redisTemplate, String.class, Test.class);
		RedisManager.registerStringOperation("test1", operation);


		Test test = new Test();
		for(int i =0 ; i < 10; i++)
		{
			test.getMap().put(i, i);
		}

		System.out.println(RedisManager.getStringOperation("test1").set("object", test));


		Test ret1 = (Test)RedisManager.getStringOperation("test1").get("object");
		System.out.println(ret1);

		System.out.println(ret1.getI());
		System.out.println(ret1.getStr());
		System.out.println(ret1.getMap().size());
	}


	static class Test
	{
		private int i = 1;
		private String str = "test";
		private Map<Integer, Integer> map = new HashMap<>();

		public int getI()
		{
			return i;
		}

		public String getStr()
		{
			return str;
		}

		public Map<Integer, Integer> getMap()
		{
			return map;
		}
	}


	static class TestStringOperation<K, V> extends DefaultStringOpreation<K, V>
	{
		public TestStringOperation(String keyPrefix, RedisTemplate redisTemplate, Class<K> keyClass, Class<V> valueClass)
		{
			super(keyPrefix, redisTemplate, keyClass, valueClass);
		}
	}
}
