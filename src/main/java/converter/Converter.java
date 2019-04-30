package converter;

/**
 * @since 2019/4/23
 */
@FunctionalInterface
public interface Converter<S, T>
{
	/**
	 * convert the source object of type {@code S} to target type {@code T}.
	 * @param source
	 * @return
	 */
	T convert(S source);
}
