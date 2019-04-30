package connection;

import redis.clients.jedis.Tuple;
import support.Limit;
import support.ScoreRange;
import support.Weights;
import java.util.Set;

/**
 * zset（有序集合）相关操作
 *
 * @author oliverblue
 * @see <a href="https://redis.io/commands#sorted_set">sorted set commands</a>
 * @since 2019/4/25
 */
public interface RedisZSetCommands
{

	/**
	 * Sort aggregation operations.
	 */
	enum Aggregate
	{
		SUM, MIN, MAX;
	}

	enum ZAddOption
	{
		/**
		 * NX
		 */
		ADD_IF_ABSENT,
		/**
		 * XX
		 */
		ADD_IF_PRESENT,
		/**
		 * 修改zadd的返回值
		 * 将返回值从新添加元素的数量变成变换元素的数量
		 * 变化元素：新添加元素和更新score的元素
		 */
		ADD_IF_ELEMENTS_CHANGED,
		;

		public static ZAddOption changed() {
			return ADD_IF_ELEMENTS_CHANGED;
		}

		public static ZAddOption ifPresent() {
			return ADD_IF_PRESENT;
		}

		public static ZAddOption ifAbsent() {
			return ADD_IF_ABSENT;
		}

	}


	/**
	 * 当key不存在时，添加member，或者当key存在时，更新score
	 *
	 * @param key
	 * @param score
	 * @param member
	 * @return 返回被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
	 */
	Long zAdd(byte[] key, double score, byte[] member);

	/**
	 * 根据option添加元素或者更新score
	 *
	 * @param key
	 * @param score
	 * @param member
	 * @param option ZADD options
	 * @return
	 */
	Long zAdd(byte[] key, double score, byte[] member, ZAddOption option);

	/**
	 * 根据tuples元素，添加member或者更新score，或者更改返回值
	 *
	 * @param key
	 * @param tuples
	 * @return 返回被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
	 */
	Long zAdd(byte[] key, Set<Tuple> tuples);

	/**
	 * 根据tuples元素，添加member或者更新score，或者更改返回值
	 *
	 * @param key
	 * @param tuples
	 * @param option
	 * @return
	 */
	Long zAdd(byte[] key, Set<Tuple> tuples, ZAddOption option);


	/**
	 * 从指定key中删除指定的member
	 * @param key
	 * @param members
	 * @return 返回被删除的元素数量，不包括不存在的member的数量
	 */
	Long zRem(byte[] key, byte[]... members);


	/**
	 * 删除分数在[min, max]的所有元素
	 * @param key
	 * @param min
	 * @param max
	 * @return 返回被删除的元素个数
	 */
	default Long zRemScoreRangeByScore(byte[] key, double min, double max)
	{
		return zRemRangeByScore(key, ScoreRange.builder().gte(min).lte(max));
	}

	/**
	 * 删除指定范围的score的所有元素
	 * @param key
	 * @param ScoreRange
	 * @return 返回被删除的元素个数
	 */
	Long zRemRangeByScore(byte[] key, ScoreRange ScoreRange);

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
	Long zRemRangeByRank(byte[] key, long start, long end);


	/**
	 * 返回并移除分数最高的一个元素
	 * @param key
	 * @return
	 */
	default Tuple zPopMax(byte[] key)
	{
		return zPopMax(key, 1);
	}


	/**
	 * 返回分数最高的一个元素，并移除分数最高的前count个元素
	 * Removes and returns up to count members with the highest scores in the sorted set stored at key.
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	Tuple zPopMax(byte[] key, long count);


	/**
	 * 返回并移除分数最低的元素
	 * @param key
	 * @return
	 */
	default Tuple zPopMin(byte[] key)
	{
		return zPopMin(key, 1);
	}

	/**
	 * Removes and returns up to count members with the lowest scores in the sorted set stored at key.
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	Tuple zPopMin(byte[] key, long count);



	/**
	 * 获取指定member的分数score
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	Double zScore(byte[] key, byte[] member);

	/**
	 * Increments the score of member in the sorted set stored at key by increment.
	 *
	 * @param key
	 * @param increment
	 * @param member
	 * @return 返回新的分数（the new score of member ）
	 */
	Double zIncrBy(byte[] key, double increment, byte[] member);


	/**
	 * 获取指定key的set中元素数量（基数）
	 *
	 * @param key
	 * @return
	 */
	Long zCard(byte[] key);


	/**
	 * 返回指定分数区间[min, max]的元素数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	default Long zCount(byte[] key, double min, double max)
	{
		return zCount(key, ScoreRange.builder().gte(min).lte(max));
	}


	/**
	 * 返回指定分数范围ScoreRange的元素数量
	 * @param key
	 * @param ScoreRange
	 * @return
	 */
	Long zCount(byte[] key, ScoreRange ScoreRange);

	/**
	 * 返回指定范围的元素的集合，元素按score从低到高的顺序返回
	 *
	 * @param key
	 * @param start inclusive
	 * @param end   inclusive
	 * @return
	 */
	Set<byte[]> zRange(byte[] key, long start, long end);

	/**
	 * 获取指定范围的tuple（element-score键值对）集合， tuple按score从低到高的顺序返回
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<Tuple> zRangeWithScores(byte[] key, long start, long end);


	/**
	 * 返回指定范围的元素的集合，元素按score从高到底的顺序返回
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<byte[]> zRevRange(byte[] key, long start, long end);

	/**
	 * 获取指定范围的tuple（element-score键值对）集合， tuple按score从高到底的顺序返回
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<Tuple> zRevRangeWithScores(byte[] key, long start, long end);

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
	default Set<byte[]> zRangeByScore(byte[] key, double min, double max) {
		return zRangeByScore(key, ScoreRange.builder().gte(min).lte(max));
	}

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
	default Set<byte[]> zRangeByScore(byte[] key, double min, double max, long offset, long count) {
		return zRangeByScore(key, new ScoreRange().gte(min).lte(max),
				Limit.builder().offset(Long.valueOf(offset).intValue()).count(Long.valueOf(count).intValue()));
	}

	/**
	 *  返回指定分数范围ScoreRange的所有元素，元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score
	 * between min and max (including elements with score equal to min or max)
	 *
	 * @param key
	 * @param ScoreRange
	 * @return
	 */
	default Set<byte[]> zRangeByScore(byte[] key, ScoreRange ScoreRange) {
		return zRangeByScore(key, ScoreRange, Limit.unlimited());
	}

	/**
	 * 元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score
	 * between min and max (including elements with score equal to min or max)
	 *
	 * @param key
	 * @param ScoreRange
	 * @param limit limit argument can be used to only get a ScoreRange of the matching elements
	 *                (similar to SELECT LIMIT offset, count in SQL).
	 * @return
	 */
	Set<byte[]> zRangeByScore(byte[] key, ScoreRange ScoreRange, Limit limit);


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
	default Set<byte[]> zRevRangeByScore(byte[] key, double min, double max) {
		return zRevRangeByScore(key, ScoreRange.builder().gte(min).lte(max));
	}

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
	default Set<byte[]> zRevRangeByScore(byte[] key, double min, double max, long offset, long count) {

		return zRevRangeByScore(key, ScoreRange.builder().gte(min).lte(max),
				Limit.builder().offset(Long.valueOf(offset).intValue()).count(Long.valueOf(count).intValue()));
	}


	/**
	 * 元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score in ScoreRange.
	 * @param key
	 * @param ScoreRange
	 * @return
	 */
	default Set<byte[]> zRevRangeByScore(byte[] key, ScoreRange ScoreRange) {
		return zRevRangeByScore(key, ScoreRange, Limit.unlimited());
	}

	/**
	 * 元素按score从低到高的顺序返回
	 *
	 * Returns all the elements in the sorted set at key with a score
	 * between max and min (including elements with score equal to max or min)
	 *
	 * @param key
	 * @param ScoreRange
	 * @param limit
	 * @return
	 */
	Set<byte[]> zRevRangeByScore(byte[] key, ScoreRange ScoreRange, Limit limit);

	/**
	 *
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	default Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max) {
		return zRangeByScoreWithScores(key,  ScoreRange.builder().gte(min).lte(max));
	}

	/**
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	default Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max, long offset, long count) {
		return zRangeByScoreWithScores(key, ScoreRange.builder().gte(min).lte(max),
				Limit.builder().offset(Long.valueOf(offset).intValue()).count(Long.valueOf(count).intValue()));
	}

	default Set<Tuple> zRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange) {
		return zRangeByScoreWithScores(key, ScoreRange, Limit.unlimited());
	}

	/**
	 * Get set of {@link Tuple}s in ScoreRange from {@code Limit#offset} to {@code Limit#offset + Limit#count} where score is
	 * between {@code ScoreRange#min} and {@code ScoreRange#max} from sorted set.
	 *
	 * @param key
	 * @param ScoreRange
	 * @param limit
	 * @return
	 */
	Set<Tuple> zRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange, Limit limit);


	/**
	 *  元素按score从低到高的顺序返回
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	default Set<Tuple> zRevRangeByScoreWithScores(byte[] key, double min, double max) {
		return zRevRangeByScoreWithScores(key, ScoreRange.builder().gte(min).lte(max), Limit.unlimited());
	}



	/**
	 * 元素按score从低到高的顺序返回
	 *
	 * @param key
	 * @param ScoreRange
	 * @return
	 */
	default Set<Tuple> zRevRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange) {
		return zRevRangeByScoreWithScores(key, ScoreRange, Limit.unlimited());
	}

	/**
	 * 元素按score从低到高的顺序返回
	 *
	 * Get elements in ScoreRange from {@code Limit#offset} to {@code Limit#offset + Limit#count} where score is between
	 * @code ScoreRange#min} and {@code ScoreRange#max} from sorted set ordered high -> low.
	 *
	 * @param key
	 * @param ScoreRange
	 * @param limit
	 * @return
	 */
	Set<Tuple> zRevRangeByScoreWithScores(byte[] key, ScoreRange ScoreRange, Limit limit);


	/**
	 * 获取指定member的排名（排行榜按score从低到高）
	 * Returns the rank of member in the sorted set stored at key, with the scores ordered from low to high.
	 * The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
	 * @param key
	 * @param member
	 * @return
	 */
	Long zRank(byte[] key, byte[] member);

	/**
	 * 获取指定member的排名(排行榜按score从高到底)
	 * Returns the rank of member in the sorted set stored at key, with the scores ordered from high to low.
	 * The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
	 * @param key
	 * @param member
	 * @return
	 */
	Long zRevRank(byte[] key, byte[] member);


	/**
	 *
	 * Computes the union of numkeys sorted sets given by the specified keys, and stores the result in destination
	 *
	 * @param destination
	 * @param sets
	 * @return 返回destination中元素个数
	 */
	Long zUnionStore(byte[] destination, byte[]... sets);


	/**
	 *  权重为整数数组
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param sets
	 * @return 返回destination中元素个数
	 */
	default Long zUnionStore(byte[] destKey, Aggregate aggregate, int[] weights, byte[]... sets) {
		return zUnionStore(destKey, aggregate, Weights.of(weights), sets);
	}

	/**
	 * 计算指定集合的并集
	 * @param destination
	 * @param aggregate 聚合函数 [AGGREGATE SUM|MIN|MAX]
	 * @param weights 权重 [WEIGHTS weight [weight ...]]
	 * @param sets
	 * @return 返回destination中元素个数
	 */
	Long zUnionStore(byte[] destination, Aggregate aggregate, Weights weights, byte[]... sets);


	/**
	 * 计算集合的交集
	 * @param destination
	 * @param sets
	 * @return
	 */
	Long zInterStore(byte[] destination, byte[]... sets);

	/**
	 * 计算集合的交集
	 *
	 * @param destination
	 * @param aggregate
	 * @param weights
	 * @param sets
	 * @return
	 */
	default Long zInterStore(byte[] destination, Aggregate aggregate, int[] weights, byte[]... sets)
	{
		return zInterStore(destination, aggregate, Weights.of(weights), sets);
	}

	/**
	 * 计算集合的交集
	 *
	 * @param destination
	 * @param aggregate
	 * @param weights
	 * @param sets
	 * @return
	 */
	Long zInterStore(byte[] destination, Aggregate aggregate, Weights weights, byte[]... sets);
}
