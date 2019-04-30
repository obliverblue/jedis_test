package connection;

import support.Expiration;

import java.util.List;
import java.util.Map;

/**
 * String（字符串）相关操作 字节数组版
 * 提供一些常用的命令
 *
 * @author obliverblue
 * @see <a href="https://redis.io/commands#string">string commands</a>
 * @since 2019/4/25
 */
public interface RedisStringCommands
{

	/**
	 * get value for specific key
	 *
	 * @param key
	 * @return 如果key不存在 则返回null
	 */
	byte[] get(byte[] key);


	/**
	 * 设置指定key的值，返回该ke的旧值（注意：该操作为原子操作）
	 * 使用场景
	 * 和INCR命令使用，实现重置
	 *
	 * @param key
	 * @return 返回old value，如果key不存在 则返回null
	 */
	byte[] getSet(byte[] key, byte[] value);

	/**
	 * 根据keys中的顺序，返回相应的值
	 *
	 * @param keys
	 * @return 如果给定的keys里面， 有某个key不存在, 则返回null
	 */
	List<byte[]> mGet(byte[]... keys);


	/**
	 * 设置指定key的value， 如果key存在，则覆盖value
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	Boolean set(byte[] key, byte[] value);


	/**
	 * 根据expiration设置key的生命周期（过期时间）
	 * 根据option设置对key的操作行为
	 * @param key
	 * @param value
	 * @param expiration
	 * @param option
	 * @return
	 */
	Boolean set(byte[] key, byte[] value, Expiration expiration, SetOption option);


	/**
	 * setNX : SET if Not eXists
	 * 只有在key不存在时，才设置key对应的value
	 * @param key
	 * @param value
	 * @return
	 */
	Boolean setNX(byte[] key, byte[] value);


	/**
	 * 设置key的过期时间，单位为seconds
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	Boolean setEx(byte[] key, long seconds, byte[] value);


	/**
	 * 设置key的过期时间，单位为millseconds
	 * @param key
	 * @param millseconds
	 * @param value
	 * @return
	 */
	Boolean pSetEx(byte[] key, long millseconds, byte[] value);


	/**
	 * 设置key-value键值对
	 * @param tuples
	 * @return
	 */
	Boolean mSet(Map<byte[], byte[]> tuples);

	/**
	 * 在对应的key不存在的情况下，设置key-value键值对
	 * @param tuples
	 * @return
	 */
	Boolean mSetNX(Map<byte[], byte[]> tuples);


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
	Long incr(byte[] key);


	/**
	 * 对于指定key的value进行加delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Long incrBy(byte[] key, long delta);

	/**
	 * 对于指定key的value进行加delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Double incrByFloat(byte[] key, double delta);



	/**
	 * 对于指定key的value进行减1操作
	 * @param key
	 * @return
	 */
	Long decr(byte[] key);

	/**
	 * 对于指定key的value进行减delta操作
	 * @param key
	 * @param delta
	 * @return
	 */
	Long decrBy(byte[] key, long delta);


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
	Long append(byte[] key, byte[] value);


	/**
	 * 获取指定key的value在区间[start, end]的子串
	 * Negative offsets can be used in order to provide an offset starting from the end of the string
	 * @param key
	 * @param start inclusive
	 * @param end inclusive
	 * @return
	 */
	byte[] getRange(byte[] key, long start, long end);


	/**
	 * 用value值覆盖指定key从offset开始的值
	 *
	 * @param key
	 * @param value
	 * @param offset 如果offset值大于key现有的value值得长度，添加zero-byte
	 */
	void setRange(byte[] key, byte[] value, long offset);


	/**
	 * 获取指定key的value的长度
	 * @param key
	 * @return
	 */
	Long strLen(byte[] key);


	/**
	 * set命令中参数NX，XX
	 */
	enum SetOption
	{
		/**
		 * 不设置任何额外的参数
		 */
		NONE,
		/**
		 * NX
		 */
		SET_IF_ABSENT,
		/**
		 * XX
		 */

		SET_IF_PRESENT,
		;

	}
}
