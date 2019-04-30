package util;

import java.util.function.Supplier;

/**
 *
 * @since 2019/4/25.
 */
public abstract class Assert
{


	/**
	 * 判断对象是否为空
	 * @param obj
	 * @param message
	 * @throws IllegalArgumentException 如果对象为null， 则抛出IllegalArgumentException
	 */
	public static void notNull(Object obj, String message)
	{
		if(obj == null)
		{
			throw new IllegalArgumentException(message);
		}

	}


	/**
	 * 
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
		if (!expression) {
			throw new IllegalArgumentException(nullSafeGet(messageSupplier));
		}
	}

	private static String nullSafeGet(Supplier<String> messageSupplier) {
		return (messageSupplier != null ? messageSupplier.get() : null);
	}


	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}
}
