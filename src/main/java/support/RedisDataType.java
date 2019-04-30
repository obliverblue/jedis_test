package support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 2019/4/29
 */
public enum  RedisDataType
{
	/**
	 * none when key does not exist
	 */
	NONE("none"),
	STRING("string"),
	HASH("hash"),
	LIST("list"),
	SET("set"),
	ZSET("zset"),
	/**
	 * redis 5 之后添加的类型
	 */
	// STREAM("stream"),
	;

	private static final Map<String, RedisDataType> typeLookup = new ConcurrentHashMap<>(6);

	static
	{
		for(RedisDataType type : RedisDataType.values())
		{
			typeLookup.put(type.getType(), type);
		}
	}

	private final String type;
	RedisDataType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public static RedisDataType getRedisDataType(String typeStr)
	{
		RedisDataType type = typeLookup.get(typeStr);
		if(type == null)
		{
			throw  new IllegalArgumentException("unknown redis data type!");
		}
		return type;
	}
}
