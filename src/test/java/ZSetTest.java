import connection.DefaultJedisClientConfiguration;
import connection.JedisClientConfiguration;
import core.RedisManager;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import support.TypedTuple;

import java.util.Set;

/**
 * Created by Administrator on 2019/4/30.
 */
public class ZSetTest
{

	public static void main(String[] args)
	{
		JedisClientConfiguration configuration = new DefaultJedisClientConfiguration(new GenericObjectPoolConfig());
		RedisManager.init(configuration);
//		for(int i = 0; i < 100000; i++)
//		{
//			RedisManager.getZSetOperation("zset_1").zAdd(0, i, i);
//		}
//
		long start = System.currentTimeMillis();

		Set<Integer> ret1 = RedisManager.getZSetOperation("zset_1").zRange(0, 30, 100000);
//		for(Integer i : ret1)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret1.size() + "--------------------------------"  + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		Set<Integer> ret2 = RedisManager.getZSetOperation("zset_1").zRangeByScore(0, 100, 100000);
//		for(Integer i : ret2)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret2.size() +"--------------------------------"  + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();

		Set<Integer> ret3 = RedisManager.getZSetOperation("zset_1").zRevRangeByScore(0, 200, 2000);
//		for(Integer i : ret3)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret3.size() +"--------------------------------"  + (System.currentTimeMillis() - start));


		start = System.currentTimeMillis();

		Set<TypedTuple<Integer>> ret4 = RedisManager.getZSetOperation("zset_1").zRangeWithScores(0, 30, 100000);
//		for(Integer i : ret1)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret4.size() + "--------------------------------"  + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		Set<TypedTuple<Integer>> ret5 = RedisManager.getZSetOperation("zset_1").zRangeByScoreWithScores(0, 100, 100000);
//		for(Integer i : ret2)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret5.size() +"--------------------------------"  + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();

		Set<TypedTuple<Integer>> ret6 = RedisManager.getZSetOperation("zset_1").zRevRangeByScoreWithScores(0, 200, 2000);
//		for(Integer i : ret3)
//		{
//			System.out.println(i);
//		}

		System.out.println(ret6.size() +"--------------------------------"  + (System.currentTimeMillis() - start));

	}


}
