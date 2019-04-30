package connection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * hash（哈希表）相关的操作
 * @author obliverblue
 * @since 2019/4/25
 * @see <a href="https://redis.io/commands#hash">hash commands</a>
 */
public interface RedisHashCommands
{
	/**
	 * Set the value of a hash field.
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Boolean hSet(byte[] key, byte[] field, byte[] value);

	/**
	 * Sets field in the hash stored at key to value, only if field does not yet exist.
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Boolean hSetNX(byte[] key, byte[] field, byte[] value);

	/**
	 * Set multiple hash fields to multiple values using data provided in hashes.
	 * @param key
	 * @param hashes
	 */
	void hMSet(byte[] key, Map<byte[], byte[]> hashes);

	/**
	 * Get the value associated with field in the hash stored at key.
	 * @param key
	 * @param field
	 * @return
	 */
	byte[] hGet(byte[] key, byte[] field);

	/**
	 * Get the values associated with the specified fields in the hash stored at key.
	 * @param key
	 * @param fields
	 * @return
	 */
	List<byte[]> hMGet(byte[] key, byte[]... fields);

	/**
	 * 对指定key的field的value做加delta操作
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	Long hIncrBy(byte[] key, byte[] field, long delta);

	/**
	 * 对指定key的field的value做加delta操作
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	Double hIncrBy(byte[] key, byte[] field, double delta);

	/**
	 * 判断key的field是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	Boolean hExists(byte[] key, byte[] field);

	/**
	 * 删除key中指定的field
	 *
	 * @param key
	 * @param fields
	 * @return 返回被删除的field的个数，如果key不存在，视为操作空的hash，返回0
	 */
	Long hDel(byte[] key, byte[]... fields);


	/**
	 * 获取指定key的hash大小
	 * @param key
	 * @return
	 */
	Long hLen(byte[] key);


	/**
	 * 获取指定的key中与field相关的value值的字符串长度
	 * @param key
	 * @param field
	 * @return 与field相关的value值的字符串长度，如果field不存在或者key不存在，则返回0
	 */
	Long hStrLen(byte[] key, byte[] field);

	//////////////////////////////////////////////////////////////////////////
	//
	// Bulk Operations
	//
	/////////////////////////////////////////////////////////////////////////

	/**
	 * 获取指定key的所有field
	 * @param key
	 * @return
	 */
	Set<byte[]> hKeys(byte[] key);


	/**
	 * 获取指定key的所有value
	 * @param key
	 * @return
	 */
	List<byte[]> hValues(byte[] key);


	/**
	 * 获取指定key的所有hash（key-value pairs）
	 * @param key
	 * @return
	 */
	Map<byte[], byte[]> hGetAll(byte[] key);
}
