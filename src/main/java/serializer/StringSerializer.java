package serializer;

import java.nio.charset.StandardCharsets;

/**
 * 使用String
 *
 * @since 2019/4/29
 */
public class StringSerializer implements Serializer<String>
{

	public static final StringSerializer UTF_8 = new StringSerializer();

	@Override
	public byte[] serialize(String s)
	{
		return s == null ? null : s.getBytes();
	}

	@Override
	public String deserialize(byte[] bytes)
	{
		return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
	}

	@Override
	public <V> String deserialize(byte[] bytes, Class<V> type)
	{
		return deserialize(bytes);
	}
}
