package core;

import connection.RedisConnection;

/**
 * @since 2019/4/29
 */
public interface RedisCallback<T>
{

	T exec(RedisConnection connection);
}
