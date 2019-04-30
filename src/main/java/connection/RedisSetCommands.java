package connection;

import java.util.List;
import java.util.Set;

/**
 * set（集合）相关操作
 * @author obliverblue
 * @since 2019/4/25
 * @see <a href="https://redis.io/commands#set">set commands</a>
 */
public interface RedisSetCommands
{

	/**
	 * 添加指定的members到指定key的set中
	 * @param key
	 * @param members
	 * @return 返回添加到set中的元素个数，不包括已存在的元素
	 */
	Long sAdd(byte[] key, byte[]... members);


	/**
	 * 查看指定key中集合的所有元素
	 * @param key
	 * @return
	 */
	List<byte[]> sMembers(byte[] key);


	/**
	 * 查看指定key的集合中是否包含member元素
	 * @param key
	 * @param member
	 * @return
	 */
	Boolean sIsMember(byte[] key, byte[] member);

	/**
	 * 返回指定key中set的基数（cardinality）（或者元素个数）
	 * @param key
	 * @return
	 */
	Long sCard(byte[] key);


	/**
	 * 返回集合的并集
	 * @param keys
	 * @return
	 */
	Set<byte[]> sUnion(byte[]... keys);

	/**
	 * 将集合的并集保存到destination集合中
	 *
	 * Example ：
	 *      key1 = {a,b,c,d}
	 *      key2 = {c}
	 *      key3 = {a,c,e}
	 *      SUNION key1 key2 key3 = {a,b,c,d,e}
	 * @param destination
	 * @param keys
	 * @return 返回并集的元素个数
	 */
	Long sUnionStore(byte[] destination, byte[]... keys);


	/**
	 * 返回集合的交集
	 *
	 * Example :
	 *      key1 = {a,b,c,d}
	 *      key2 = {c}
	 *      key3 = {a,c,e}
	 *      SINTER key1 key2 key3 = {c}
	 * @param keys
	 * @return
	 */
	Set<byte[]> sInter(byte[]... keys);


	/**
	 * 将集合的交集保存到destination集合中
	 *
	 * @param destination
	 * @param keys
	 * @return 交集的元素个数
	 */
	Long sInterStore(byte[] destination, byte[]... keys);

	/**
	 * 返回第一个集合和之后所有集合的差集
	 * Returns the members of the set resulting from the difference between the first set and all the successive sets.
	 *
	 * Example :
	 *         key1 = {a,b,c,d}
	 *         key2 = {c}
	 *         key3 = {a,c,e}
	 *         SDIFF key1 key2 key3 = {b,d}
	 *
	 * @param keys
	 * @return 返回差集
	 */
	Set<byte[]> sDiff(byte[]... keys);

	/**
	 * 将第一个集合和之后所有集合的差集保存到destination中
	 * 如果destination存在，则将覆盖destination中的元素
	 * @param destination
	 * @param keys
	 * @return 返回差集中元素的个数
	 */
	Long sDiffStore(byte[] destination, byte[]... keys);


	/**
	 * Move member from the set at source to the set at destination
	 *
	 * @param source
	 * @param destination
	 * @param member
	 * @return
	 */
	Boolean sMove(byte[] source, byte[] destination, byte[] member);

	/**
	 * Removes and returns one or more random elements from the set value store at key.
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	Set<byte[]> sPop(byte[] key, long count);

	/**
	 * 随机获取一个元素
	 * NOTE:该命令不会更改原始集合
	 * @param key
	 * @return
	 */
	byte[] sRandMember(byte[] key);

	/**
	 * When called with just the key argument, return a random element from the set value stored at key
	 * NOTE:该命令不会更改原始集合
	 * SRANDMEMBER key [count]
	 *
	 *  if count == 0;
	 *      then return empty list
	 *  else if count > 0;
	 *     then return an array of count distinct elements
	 *  else // count < 0
	 *      then return the same element multiple times
	 *
	 * @param key
	 * @param count
	 * @return
	 */
	List<byte[]> sRandomMember(byte[] key, long count);

	/**
	 * Remove the specified members from the set stored at key.
	 *
	 * @param key
	 * @param members
	 * @return
	 */
	Long sRem(byte[] key, byte[]... members);
}
