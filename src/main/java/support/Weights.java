package support;

import util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Redis : ZUNIONSTORE/ZINTERSTORE 中使用的权重类
 * @author oliverblue
 * @since 2019/4/28
 */
public class Weights
{

	private final List<Double> weights;

	public Weights(List<Double> weights)
	{
		this.weights = weights;
	}


	public static Weights of(int... weights)
	{
		Assert.notNull(weights, "Weights must not be null!");
		return new Weights(Arrays.stream(weights).mapToDouble(val -> val).boxed().collect(Collectors.toList()));
	}

	public static Weights of(double... weights)
	{
		Assert.notNull(weights, "Weights must not be null!");
		return new Weights(Arrays.stream(weights).boxed().collect(Collectors.toList()));
	}

	public static Weights fromSetCount(int count)
	{
		Assert.isTrue(count >= 0, "Count of input sorted sets must be greater or equal to zero!");
		return new Weights(IntStream.range(0, count).mapToDouble(val -> 1).boxed().collect(Collectors.toList()));
	}


	public Weights multiply(int multiplier)
	{
		return apply(it -> it * multiplier);
	}

	public Weights multiply(double multiplier)
	{

		return apply(it -> it * multiplier);
	}

	public Weights apply(Function<Double, Double> operator)
	{
		return new Weights(weights.stream().map(operator).collect(Collectors.toList()));
	}

	public double getWeight(int index)
	{
		return weights.get(index);
	}

	public int size()
	{
		return weights.size();
	}

	public double[] toArray()
	{
		return weights.stream().mapToDouble(Double::doubleValue).toArray();
	}

	public List<Double> toList()
	{
		return Collections.unmodifiableList(weights);
	}
}
