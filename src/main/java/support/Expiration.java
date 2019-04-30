package support;

import util.Assert;
import util.ObjectUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 *  与过期时间相关
 * @since 2019/4/25
 */
public final class Expiration
{

	// 过期时长
	private final long expirationTime;
	// 时间单位 seconds/millseconds
	private final TimeUnit unit;


	/**
	 *  创建过期类
	 * @param expirationTime
	 * @param unit 如果unit为null，则默认为seconds
	 */
	public Expiration(long expirationTime, TimeUnit unit)
	{
		this.expirationTime = expirationTime;
		this.unit = unit != null ? unit : TimeUnit.SECONDS;
	}

	/**
	 * 创建一个seconds为单位的过期类
	 * @param expirationTime
	 * @return
	 */
	public static Expiration seconds(long expirationTime)
	{
		return new Expiration(expirationTime, TimeUnit.SECONDS);
	}

	/**
	 * 创建一个millseconds为单位的过期类
	 * @param expirationTime
	 * @return
	 */
	public static Expiration millseconds(long expirationTime)
	{
		return new Expiration(expirationTime, TimeUnit.MILLISECONDS);
	}

	/**
	 *  统一时间单位为seconds和millseconds
	 *  单位大于seconds，则转换为seconds
	 *  单位小于millseconds，则转换为millseconds
	 * @param expirationTime
	 * @param unit 默认为seconds
	 * @return
	 */
	public static Expiration of(long expirationTime, TimeUnit unit)
	{
		if(unit == null)
		{
			return seconds(expirationTime);
		}

		if(ObjectUtils.objectEquals(unit, TimeUnit.MICROSECONDS)
				|| ObjectUtils.objectEquals(unit, TimeUnit.NANOSECONDS)
				|| ObjectUtils.objectEquals(unit, TimeUnit.MILLISECONDS))
		{
			return millseconds(unit.toMillis(expirationTime));
		}

		return seconds(unit.toSeconds(expirationTime));
	}

	/**
	 * 根据duration转化成expiration类
	 * @param duration
	 * @return
	 */
	public static Expiration of(Duration duration)
	{
		Assert.notNull(duration, "duration must not be null!");
		if (duration.toMillis() % 1000 == 0) {
			return seconds(duration.getSeconds());
		}

		return millseconds(duration.toMillis());
	}

	/**
	 * 创建一个永久的Expiration类
	 * @return
	 */
	public static Expiration persistent()
	{
		return new Expiration(-1, TimeUnit.SECONDS);
	}

	public boolean isPersistent() {
		return expirationTime == -1;
	}

	public long getExpirationTime()
	{
		return expirationTime;
	}

	public TimeUnit getUnit()
	{
		return unit;
	}
}
