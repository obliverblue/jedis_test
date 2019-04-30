package exception;

/**
 * @since 2019/4/29
 */
public class SerializationException extends RuntimeException
{
	public SerializationException(String msg) {
		super(msg);
	}

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
