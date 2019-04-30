package core;

import connection.RedisStringCommands.SetOption;
import support.Expiration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @since 2019/4/29
 */
public interface StringOperation<K, V> extends KeyOperation<K>
{

	/**
	 * get value for specific key
	 *
	 * @param key
	 * @return 如果key不存在 则返回null
	 */
	V get(K key);


	/**
	 * 设置指定key的值，返回该ke的旧值（注意：该操作为原子操作）
	 * 使用场景
	 * 和INCR命令使用，实现重置
	 *
	 * @param key
	 * @return 返回old value，如果key不存在 则返回null
	 */
	V getSet(K key, V value);

	/**
	 * 根据keys中的顺序，返回相应的值
	 *
	 * @param keys
	 * @return 如果给定的keys里面， 有某个key不存在, 则返回null
	 */
	List<V> mGet(Collection<K> keys);


	/**
	 * 设置指定key的value， 如果key存在，则覆盖value
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	Boolean set(K key, V value);


	/**
	 * 根据expiration设置key的生命周期（过期时间）
	 * 根据option设置对key的操作行为
	 * @param key
	 * @param value
	 * @param expiration
	 * @param option
	 * @return
	 */
	Boolean set(K key, V value, Expiration expiration, SetOption option);


	/**
	 * setNX : SET if Not eXists
	 * 只有在key不存在时，才设置key对应的value
	 * @param key
	 * @param value
	 * @return
	 */
	Boolean setNX(K key, V value);


	/**
	 * 设置key的过期时间，单位为seconds
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	Boolean setEx(K key, long seconds, V value);


	/**
	 * 设置key的过期时间，单位为millseconds
	 * @param key
	 * @param millseconds
	 * @param value
	 * @return
	 */
	Boolean pSetEx(K key, long millseconds, V value);


	/**
	 * 设置key-value键值对
	 * @param tuples
	 * @return
	 */
	Boolean mSet(Map<K, V> tuples);

	/**
	 * 在对应的key不存在的情况下，设置key-value键值对
	 * @param tuples
	 * @return
	 */
	Boolean mSetNX(Map<K, V> tuples);


	/**
	 * 对于指定key的value进行加1操作，注意value值一定是Integer的字符串
	 *
	 * redis
	 *      1、如果value不能转换为Integer，则会抛出错误
	 *      2、redis会将value翻译为64bit的十进制有符号整数
	 * @see <a href="https://redis.io/commands/incr"></a>
	 * 使用场景
	 *      1、计数器counter
	 *      2、限速器rate limiter
	 * @param key
	 * @return 如果key不存在，则在操作前将value设置为0
	 */
	Long incr(K key);


	/**
	 * 对于指定key的value进行加delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Long incrBy(K key, long delta);

	/**
	 * 对于指定key的value进行加delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Double incrBy(K key, double delta);




	/**
	 * 对于指定key的value进行减1操作
	 * @param key
	 * @return
	 */
	Long decr(K key);

	/**
	 * 对于指定key的value进行减delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Long decrBy(K key, long delta);


	/**
	 * 对指定key追加value
	 * 如果key存在，则将value追加到现有的的字符串末尾
	 * 如果key不存在，则简单地将键 key的值设为value，类似set命令
	 *
	 * @see <a href="https://redis.io/commands/append"></a>
	 * 使用场景
	 *      时间序列time series
	 *          创建一个固定长度的list samples
	 *           APPEND timeseries "fixed-size sample"
	 * @param key
	 * @param value
	 * @return 返回追加后，value字符串的长度
	 */
	Long append(K key, V value);


	/**
	 * 获取指定key的value在区间[start, end]的子串
	 * Negative offsets can be used in order to provide an offset starting from the end of the string
	 * @param key
	 * @param start inclusive
	 * @param end inclusive
	 * @return
	 */
	byte[] getRange(K key, long start, long end);


	/**
	 * 用value值覆盖指定key从offset开始的值
	 *
	 * @param key
	 * @param value
	 * @param offset 如果offset值大于key现有的value值得长度，添加zero-byte
	 */
	void setRange(K key, V value, long offset);


	/**
	 * 获取指定key的value的长度
	 * @param key
	 * @return
	 */
	Long strLen(K key);

}
