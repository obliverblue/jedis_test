package support;

import util.Assert;

/**
 *
 * @author obliverblue
 * @since 2019/4/26
 */
public class ScoreRange
{
	private Boundary min;
	private Boundary max;

	/**
	 * 创建新的Range
	 * @return
	 */
	public static ScoreRange builder()
	{
		return new ScoreRange();
	}

	public static ScoreRange unbounded()
	{
		ScoreRange range = new ScoreRange();
		range.max = Boundary.INFINITY;
		range.min = Boundary.INFINITY;
		return range;
	}

	/**
	 *  Greater Than Equals
	 * @param min
	 * @return
	 */
	public ScoreRange gte(Object min) {
		Assert.notNull(min, "Min already set for range.");
		this.min = new Boundary(min, true);
		return this;
	}

	/**
	 *   Greater Than
	 * @param min
	 * @return
	 */
	public ScoreRange gt(Object min) {
		Assert.notNull(min, "Min already set for range.");
		this.min = new Boundary(min, false);
		return this;
	}

	/**
	 *  Less Then Equals
	 * @param max
	 * @return
	 */
	public ScoreRange lte(Object max) {
		Assert.notNull(max, "Max already set for range.");
		this.max = new Boundary(max, true);
		return this;
	}

	/**
	 * Less Than
	 * @param max
	 * @return
	 */
	public ScoreRange lt(Object max) {
		Assert.notNull(max, "Max already set for range.");
		this.max = new Boundary(max, false);
		return this;
	}

	public Boundary getMin()
	{
		return min;
	}

	public Boundary getMax()
	{
		return max;
	}

	public static class Boundary
	{
		public static final Boundary INFINITY = new Boundary(null, true);

		/**
		 * value 的类型包含
		 *   byte[]
		 *   Double
		 *   Long
		 *   Integer
		 *   String
		 */
		Object value;
		boolean isInclusive;

		public Boundary(Object value, boolean isInclusive)
		{
			this.value = value;
			this.isInclusive = isInclusive;
		}

		public Object getValue()
		{
			return value;
		}

		public boolean isInclusive()
		{
			return isInclusive;
		}
	}
}
