package serializer;

/**
 * @since 2019/4/29
 */
public interface Serializer<T>
{

	byte[] serialize(T t);

	T deserialize(byte[] bytes);

	<V> T deserialize(byte[] bytes, Class<V> type);
}
