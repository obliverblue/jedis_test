package connection;

import java.util.List;

/**
 * list（列表）相关操作
 * @author obliverblue
 * @since 2019/4/25
 */
public interface RedisListCommands
{

	/**
	 * List insertion position.
	 */
	enum Position {
		BEFORE, AFTER
	}


	/**
	 * 将values添加到指定key的列表尾部
	 *
	 * Example :
	 *  RPUSH mylist a b c
	 *      result = {a, b, c}
	 *
	 * @param key
	 * @param values
	 * @return 返回执行该命令后的list长度
	 */
	Long rPush(byte[] key, byte[]... values);

	/**
	 * 在指定key存在的情况下，将value添加到指定key的列表尾部
	 * @param key
	 * @param value
	 * @return 返回执行该命令后的list长度，如果key不存在，不做任何操作，则返回0
	 */
	Long rPushX(byte[] key, byte[] value);


	/**
	 * Insert all the specified values at the head of the list stored at key.
	 * 将values添加指定key的list头部
	 *
	 * Example :
	 *  LPUSH mylist a b c
	 *      result = {c, b, a}
	 * @param key
	 * @param values
	 * @return
	 */
	Long lPush(byte[] key, byte[]... values);

	/**
	 * 在指定key存在的情况下，将value添加到指定key的列表头部
	 * @param key
	 * @param value
	 * @return
	 */
	Long lPushX(byte[] key, byte[] value);


	/**
	 * 获取并移除指定key的第一个元素
	 * @param key
	 * @return
	 */
	byte[] lPop(byte[] key);


	/**
	 * 获取并移除指定key的最一个元素
	 * @param key
	 * @return
	 */
	byte[] rPop(byte[] key);


	/**
	 * 将srcKey中的最后一个元素添加destKey的头部
	 *
	 * Example :
	 *      srcKey = {a. b. c}
	 *
	 *      RPOPLPUSH srcKey destKey
	 *      srcKey = {a. b}
	 *      destKey = {c, x, y, z}
	 *
	 * @see <a href="https://redis.io/commands/rpoplpush"></a>
	 * 使用场景
	 *      1、Reliable queue
	 *      2、Circular list
	 * @param srcKey
	 * @param destKey
	 * @return the element being popped and pushed.
	 */
	byte[] rPopLPush(byte[] srcKey, byte[] destKey);


	/**
	 * Removes and returns first element from lists stored at {@code keys}. <br>
	 * <b>Blocks connection</b> until element available or {@code timeout} reached.
	 * @param keys
	 * @param timeout 超时时间 单位seconds
	 * @return empty {@link List}  when no element could be popped and the timeout expired.
	 */
	List<byte[]> bLPop(int timeout, byte[]... keys);


	/**
	 *
	 * Removes and returns last element from lists stored at {@code keys}. <br>
	 * <b>Blocks connection</b> until element available or {@code timeout} reached.
	 *
	 * @param timeout
	 * @param keys
	 * @return
	 */
	List<byte[]> bRPop(int timeout, byte[]... keys);


	/**
	 * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value. <br>
	 * <b>Blocks connection</b> until element available or {@code timeout} reached.
	 *
	 * @param timeout
	 * @param srcKey
	 * @param dstKey
	 * @return
	 */
	byte[] bRPopLPush(int timeout, byte[] srcKey, byte[] dstKey);


	/**
	 * 获取list索引为index的value
	 * @param key
	 * @param index zero based index value. Use negative number to designate elements starting at the tail.
	 * @return
	 */
	byte[] lIndex(byte[] key, long index);

	/**
	 * 在现有的pivot的前面（BEFORE）或者后面（AFTER）插入value
	 * When key does not exist, it is considered an empty list and no operation is performed.
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @return the length of the list after the insert operation, or -1 when the value pivot was not found.
	 */
	Long lInsert(byte[] key, Position where, byte[] pivot, byte[] value);


	/**
	 * 获取指定key的list长度
	 * @param key
	 * @return
	 */
	Long lLen(byte[] key);


	/**
	 * 返回在区间[start, end]之间的所有元素的list
	 *
	 * @param key
	 * @param start inclusive
	 * @param end inclusive
	 * @return  If start is larger than the end of the list, an empty list is returned.
	 *           If end is larger than the actual end of the list, Redis will treat it like the last element of the list.
	 */
	List<byte[]> lRange(byte[] key, long start, long end);

	/**
	 * Removes the first count occurrences of elements equal to value from the list stored at key.
	 *
	 * LREM key count value
	 *      count > 0: Remove elements equal to value moving from head to tail.
	 *      count < 0: Remove elements equal to value moving from tail to head.
	 *      count = 0: Remove all elements equal to value.
	 *
	 *
	 * @param key
	 * @param count
	 * @param value
	 * @return 返回被移除的元素个数
	 */
	Long lRem(byte[] key, long count, byte[] value);

	/**
	 * Sets the list element at index to value
	 * @param key
	 * @param index
	 * @param value
	 */
	void lSet(byte[] key, long index, byte[] value);

	/**
	 * Trim an existing list so that it will contain only the specified ScoreRange of elements specified.
	 * if start is larger than the end of the list, or start > end, the result will be an empty list (which causes key to be removed)
	 * @param key
	 * @param start inclusive
	 * @param end inclusive
	 */
	void lTrim(byte[] key, long start, long end);

}
