package converter;

import redis.clients.jedis.util.SafeEncoder;
import support.ScoreRange.Boundary;

import java.nio.ByteBuffer;

/**
 * @since 2019/4/28
 */
public abstract class Converters
{

	private static final Converter<String, byte[]> STRING_TO_BYTES;
	// +inf
	public static final byte[] POSITIVE_INFINITY_BYTES;
	// -inf
	public static final byte[] NEGATIVE_INFINITY_BYTES;

	static
	{
		STRING_TO_BYTES = source -> source == null ? null : SafeEncoder.encode(source);
		POSITIVE_INFINITY_BYTES = toBytes("+inf");
		NEGATIVE_INFINITY_BYTES = toBytes("-inf");
	}


	public static byte[] toBytes(Integer source)
	{
		return String.valueOf(source).getBytes();
	}

	public static byte[] toBytes(Long source)
	{
		return String.valueOf(source).getBytes();
	}

	public static byte[] toBytes(Double source)
	{
		return toBytes(String.valueOf(source));
	}

	public static byte[] toBytes(String source)
	{
		return STRING_TO_BYTES.convert(source);
	}


	/**
	 * For example:
	 *  ZScoreRangeBYSCORE zset (1 5
	 *  Will return all elements with 1 < score <= 5 while:
	 *
	 *  ZScoreRangeBYSCORE zset (5 (10
	 *  Will return all the elements with 5 < score < 10 (5 and 10 excluded).
	 *
	 * @param boundary
	 * @param defaultValue
	 * @return
	 */
	public static byte[] boundaryToBytes(Boundary boundary, byte[] defaultValue)
	{
		if(boundary == null || boundary.getValue() == null)
		{
			return defaultValue;
		}

		byte[] prefix = boundary.isInclusive() ? new byte[0] : toBytes("(");
		Object valueObj  = boundary.getValue();
		byte[] valueByteArr = null;
		if(valueObj instanceof byte[])
		{
			valueByteArr = (byte[]) valueObj;
		}
		else if(valueObj instanceof Double)
		{
			valueByteArr = toBytes((Double) valueObj);
		}
		else if(valueObj instanceof Long)
		{
			valueByteArr = toBytes((Long) valueObj);
		}
		else if(valueObj instanceof Integer)
		{
			valueByteArr = toBytes((Integer) valueObj);
		}
		else if(valueObj instanceof String)
		{
			valueByteArr = toBytes((String) valueObj);
		}
		else
		{
			throw new IllegalArgumentException(String.format("Cannot convert %s to binary format", valueObj));
		}

		ByteBuffer buffer = ByteBuffer.allocate(prefix.length + valueByteArr.length);
		buffer.put(prefix);
		buffer.put(valueByteArr);
		return buffer.array();
	}
}
