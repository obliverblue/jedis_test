package core;

/**
 *
 * @since 2019/4/29
 */
public interface KeyOperation<K>
{

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(K key);


	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	Boolean del(K key);

	/**
	 * 设置key的过期时间 单位seconds
	 * @param key
	 * @param seconds
	 * @return 是否过期
	 */
	Boolean expire(K key, int seconds);


	/**
	 * 设置key在在什么时候过期
	 *
	 * Of course, it can be used directly to specify that a given key should expire at a given time in the future.
	 *
	 * @param key
	 * @param timestamp  Unix timestamp 单位seconds
	 * @return 是否过期
	 */
	Boolean expireAt(K key, long timestamp);

	/**
	 * 设置key的过期时间 单位milliseconds
	 * @param key
	 * @param milliseconds
	 * @return
	 */
	Boolean pExpire(K key, long milliseconds);

	/**
	 * 设置key在在什么时候过期
	 *
	 * @param key
	 * @param timestamp Unix timestamp 单位milliseconds
	 * @return
	 */
	Boolean pExpireAt(K key, long timestamp);

	/**
	 * 获取key的剩余时间 单位seconds
	 * @param key
	 * @return -1：没有过期时间 -2：key不存在
	 */
	Long ttl(K key);

	/**
	 * 获取key的剩余时间 单位milliseconds
	 * @param key
	 * @return
	 */
	Long pTtl(K key);

	/**
	 * Return a random key from the currently selected database.
	 * @return
	 */
	String randomkey();

	/**
	 * 去除过期时间
	 * @param key
	 * @return
	 */
	Boolean persist(K key);

}
