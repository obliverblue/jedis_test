package support;

/**
 * The optional LIMIT argument can be used to only get a ScoreRange of the matching elements (similar to SELECT LIMIT offset, count in SQL).
 * A negative count returns all elements from the offset.
 *
 * @author obliverblue
 * @since 2019/4/26
 */
public class Limit
{
	public static final Limit UNLIMITED = builder().count(-1);

	private int offset;
	private int count;


	public static Limit builder()
	{
		return new Limit();
	}


	public Limit offset(int offset)
	{
		this.offset = offset;
		return this;
	}

	public Limit count(int count)
	{
		this.count = count;
		return this;
	}

	public int getCount()
	{
		return count;
	}

	public int getOffset()
	{
		return offset;
	}


	public boolean isUnlimited()
	{
		return this.equals(UNLIMITED);
	}

	public static Limit unlimited()
	{
		return UNLIMITED;
	}
}
