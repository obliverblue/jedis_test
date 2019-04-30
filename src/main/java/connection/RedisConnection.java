package connection;

/**
 * 连接redis server， 提供redis的相关命令
 *
 * @author obliverblue
 * @see <a href="https://redis.io/commands#connection">connection commands</a>
 * @since 2019/4/25
 */
public interface RedisConnection<T>
{
	T getConnection();

	RedisKeyCommands keyCommands();

	RedisStringCommands stringCommands();

	RedisListCommands listCommands();

	RedisHashCommands hashCommands();

	RedisSetCommands setCommands();

	RedisZSetCommands zSetCommands();

	RedisScriptingCommands scriptingCommands();

	/**
	 * 关闭连接
	 * closes(or quits) the connection
	 */
	void close();

	/**
	 * 是否关闭与redis server的连接
	 *
	 * @return
	 */
	boolean isClosed();
}
