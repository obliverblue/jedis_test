package connection;

import support.RedisDataType;

import java.util.Set;

/**
 * redis  key相关的操作
 * @author obliverblue
 * @since 2019/4/25
 * @see <a href="https://redis.io/commands#generic">key commands</a>
 */
public interface RedisKeyCommands
{

	/**
	 * 返回所有匹配pattern的key
	 *
	 * Warning: consider KEYS as a command that should only be used in production environments
	 * with extreme care. It may ruin performance when it is executed against large databases.
	 *
	 * @param pattern
	 * @return
	 */
	Set<byte[]> keys(byte[] pattern);


	/**
	 * 判断keys 是否存在
	 *
	 * @param keys
	 * @return 返回keys数组中有多少个key存在（重复的key，也会重复计算）
	 */
	Long exists(byte[]... keys);

	/**
	 *  删除keys
	 * @param keys
	 * @return 返回删除的key的数量
	 */
	Long del(byte[]... keys);


	/**
	 * 删除keys
	 *
	 * This command is very similar to DEL: it removes the specified keys. Just like DEL a key is ignored if it does not exist.
	 *
	 * 特点
	 *  1、工作在其他线程
	 *  2、回收内存
	 *  3、异步
	 * @param keys
	 * @return
	 */
	Long unlink(byte[]... keys);

	/**
	 * 获取redis key的数据类型
	 * @param key
	 * @return
	 */
	RedisDataType type(byte[] key);


	/**
	 * 设置key的过期时间 单位seconds
	 * @param key
	 * @param seconds
	 * @return 是否过期
	 */
	Boolean expire(byte[] key, int seconds);


	/**
	 * 设置key在在什么时候过期
	 *
	 * Of course, it can be used directly to specify that a given key should expire at a given time in the future.
	 *
	 * @param key
	 * @param timestamp  Unix timestamp 单位seconds
	 * @return 是否过期
	 */
	Boolean expireAt(byte[] key, long timestamp);

	/**
	 * 设置key的过期时间 单位milliseconds
	 * @param key
	 * @param milliseconds
	 * @return
	 */
	Boolean pExpire(byte[] key, long milliseconds);

	/**
	 * 设置key在在什么时候过期
	 *
	 * @param key
	 * @param timestamp Unix timestamp 单位milliseconds
	 * @return
	 */
	Boolean pExpireAt(byte[] key, long timestamp);

	/**
	 * 获取key的剩余时间 单位seconds
	 * @param key
	 * @return -1：没有过期时间 -2：key不存在
	 */
	Long ttl(byte[] key);

	/**
	 * 获取key的剩余时间 单位milliseconds
	 * @param key
	 * @return
	 */
	Long pTtl(byte[] key);

	/**
	 * Return a random key from the currently selected database.
	 * @return
	 */
	byte[] randomkey();

	/**
	 * 去除过期时间
	 * @param key
	 * @return
	 */
	Boolean persist(byte[] key);
}
