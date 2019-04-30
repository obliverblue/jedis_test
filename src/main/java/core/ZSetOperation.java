package core;



import connection.RedisZSetCommands.ZAddOption;
import support.Limit;
import support.ScoreRange;
import support.TypedTuple;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @since 2019/4/29
 */
public interface ZSetOperation<K, V> extends KeyOperation<K>
{

	/**
	 *  添加新成员
	 *
	 * @param key
	 * @param score
	 * @param member
	 * @return  添加是否成功
	 */
	Boolean zAdd(K key, double score, V member);

	/**
	 *  批量添加
	 * @param key
	 * @param tuples
	 * @return
	 */
	Long zAdd(K key, Map<V, Double> tuples);

	/**
	 * 从指定key中删除指定的member
	 * @param key
	 * @param member
	 * @return 返回被删除的元素数量，不包括不存在的member的数量
	 */
	Boolean remove(K key, V member);

	/**
	 * 批量删除
	 * @param key
	 * @param members
	 * @return
	 */
	Long remove(K key, Collection<V> members);

	/**
	 * 删除分数在[min, max]的所有元素
	 * @param key
	 * @param min
	 * @param max
	 * @return 返回被删除的元素个数
	 */
	default Long zRemRangeByScore(K key, double min, double max)
	{
		return zRemRangeByScore(key, ScoreRange.builder().gte(min).lte(max));
	}

	/**
	 * 删除指定范围的score的所有元素
	 * @param key
	 * @param scoreRange
	 * @return 返回被删除的元素个数
	 */
	Long zRemRangeByScore(K key, ScoreRange scoreRange);

	/**
	 * 删除排名在[start, end]的元素
	 * Both start and stop are 0 -based indexes with 0 being the element with the lowest score.
	 * These indexes can be negative numbers, where they indicate offsets starting at the element with the highest score.
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Long zRemRangeByRank(K key, long start, long end);


	/**
	 * 获取指定member的分数score
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	Double zScore(K key, V member);

	/**
	 * Increments the score of member in the sorted set stored at key by increment.
	 *
	 * @param key
	 * @param increment
	 * @param member
	 * @return 返回新的分数（the new score of member ）
	 */
	Double zIncrBy(K key, double increment, V member);


	/**
	 * 获取指定key的set中元素数量（基数）
	 *
	 * @param key
	 * @return
	 */
	Long zCard(K key);


	/**
	 * 返回指定分数区间[min, max]的元素数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	default Long zCount(K key, double min, double max)
	{
		return zCount(key, ScoreRange.builder().gte(min).lte(max));
	}


	/**
	 * 返回指定分数范围ScoreRange的元素数量
	 * @param key
	 * @param scoreRange
	 * @return
	 */
	Long zCount(K key, ScoreRange scoreRange);

	/**
	 * 返回指定范围的元素的集合，元素按score从低到高的顺序返回
	 *
	 * @param key
	 * @param start inclusive
	 * @param end   inclusive
	 * @return
	 */
	Set<V> zRange(K key, long start, long end);

	/**
	 * 返回指定范围的元素的集合，元素按score从高到底的顺序返回
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<V> zRevRange(K key, long start, long end);


	/**
	 * 返回指定分数区间[min, max]的所有元素，元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score
	 * between min and max (including elements with score equal to min or max).
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<V> zRangeByScore(K key, double min, double max);

	/**
	 * 从指定分数区间[min, max]的所有元素，返回从offset开始的count数量的元素，元素按score从低到高的顺序返回
	 *
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	 Set<V> zRangeByScore(K key, double min, double max, long offset, long count);

	/**
	 * 返回指定分数区间[min, max]的所有元素，元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score
	 * between max and min (including elements with score equal to max or min)
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<V> zRevRangeByScore(K key, double min, double max);

	/**
	 * 从指定分数区间[min, max]的所有元素中，返回从offset开始的count数量的元素，元素按score从低到高的顺序返回
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	Set<V> zRevRangeByScore(K key, double min, double max, long offset, long count);


	/**
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<TypedTuple<V>> zRangeWithScores(K key, long start, long end);

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<TypedTuple<V>> zRangeByScoreWithScores(K key, double min, double max);


	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	Set<TypedTuple<V>> zRangeByScoreWithScores(K key, double min, double max, long offset, long count);

	/**
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<TypedTuple<V>> zRevRangeWithScores(K key, long start, long end);

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<TypedTuple<V>> zRevRangeByScoreWithScores(K key, double min, double max);

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	Set<TypedTuple<V>> zRevRangeByScoreWithScores(K key, double min, double max, long offset, long count);

	/**
	 * 获取指定member的排名（排行榜按score从低到高）
	 * Returns the rank of member in the sorted set stored at key, with the scores ordered from low to high.
	 * The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
	 * @param key
	 * @param member
	 * @return
	 */
	Long zRank(K key, V member);

	/**
	 * 获取指定member的排名(排行榜按score从高到底)
	 * Returns the rank of member in the sorted set stored at key, with the scores ordered from high to low.
	 * The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
	 * @param key
	 * @param member
	 * @return
	 */
	Long zRevRank(K key, V member);

}
