package util;

import java.util.Arrays;

/**
 * 一个与Object相关的工具类
 *
 * @since 2019/4/25.
 */
public abstract class ObjectUtils
{


	/**
	 * 判断两个对象是否相等， 如果两个对象为数组，则使用Arrays.equals判断
	 *
	 * @param obj1
	 * @param obj2
	 * @return 两个对象是否相等
	 */
	public static boolean objectEquals(Object obj1, Object obj2)
	{
		if(obj1 == obj2)
		{
			return true;
		}

		// 只有一个为空
		if(obj1 == null || obj2 == null)
		{
			return false;
		}

		if(obj1.getClass().isArray() && obj2.getClass().isArray())
		{
			return arrayEquals(obj1, obj2);
		}
		return false;
	}


	/**
	 * 判断两个数组是否相等，使用Array.equals方法（检查元素是否相等，而不是引用是否相等）
	 *
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private static boolean arrayEquals(Object obj1, Object obj2)
	{
		if(obj1 instanceof Object[] && obj2 instanceof Object[])
		{
			return Arrays.equals((Object[])obj1, (Object[])obj2);
		}
		if(obj1 instanceof byte[] && obj2 instanceof byte[])
		{
			return Arrays.equals((byte[])obj1, (byte[])obj2);
		}
		if(obj1 instanceof short[] && obj2 instanceof short[])
		{
			return Arrays.equals((short[])obj1, (short[])obj2);
		}
		if(obj1 instanceof int[] && obj2 instanceof int[])
		{
			return Arrays.equals((int[])obj1, (int[])obj2);
		}
		if(obj1 instanceof long[] && obj2 instanceof long[])
		{
			return Arrays.equals((long[])obj1, (long[])obj2);
		}
		if(obj1 instanceof float[] && obj2 instanceof float[])
		{
			return Arrays.equals((float[])obj1, (float[])obj2);
		}
		if(obj1 instanceof double[] && obj2 instanceof double[])
		{
			return Arrays.equals((double[])obj1, (double[])obj2);
		}
		if(obj1 instanceof boolean[] && obj2 instanceof boolean[])
		{
			return Arrays.equals((boolean[])obj1, (boolean[])obj2);
		}
		return false;
	}
}
