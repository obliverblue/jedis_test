package support;


import java.util.List;

/**
 * @author obliverblue
 * @since 2019/4/28
 * @see <a href="https://redis.io/commands/eval">Conversion between Lua and Redis data types</a>
 */
public enum ReturnType
{
	/**
	 * Lua boolean false -> Redis Nil bulk reply.
	 * Return as Boolean
	 */
	BOOLEAN,
	/**
	 * Lua number -> Redis integer reply (the number is converted into an integer)
	 * Returned as Long
	 */
	INTEGER,
	/**
	 * Lua table (array) -> Redis multi bulk reply (truncated to the first nil inside the Lua array if any)
	 * Returned as List <Object>
	 */
	MULTI,
	/**
	 * Lua table with a single ok field -> Redis status reply
	 * Return as byte[]
	 */
	STATUS,
	/**
	 * Other type
	 * Return as byte[]
	 */
	VALUE,
	;

	/**
	 * 根据自己定义的java类型映射到定义的ReturnType
	 *
	 * @return 默认返回VALUE
	 */
	public static ReturnType fromJavaType(Class<?> javaType)
	{
		if(javaType == null)
		{
			return ReturnType.STATUS;
		}

		if(javaType.isAssignableFrom(List.class))
		{
			return ReturnType.MULTI;
		}

		if(javaType.isAssignableFrom(Boolean.class))
		{
			return ReturnType.BOOLEAN;
		}

		if(javaType.isAssignableFrom(Long.class))
		{
			return ReturnType.INTEGER;
		}

		return ReturnType.VALUE;
	}
}
