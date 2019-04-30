package connection;

/**
 * @since 2019/4/29
 */
public interface RedisConnectionFactory<T, C>
{

	/**
	 * 获取一个redis连接
	 * @return
	 */
	RedisConnection<T> getConnection();


	/**
	 * 获取一个redis集群的连接
	 * @return
	 */
	RedisClusterConnection<C> getClusterConnection();
}

