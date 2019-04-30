package support;

/**
 * @since 2019/4/30
 */
public interface TypedTuple<V> extends Comparable<TypedTuple<V>>
{
	V getValue();
	Double getScore();
}
