package program.map.runnners;

import program.map.Map;

public class DifferentialRunner extends Runner
{

	public DifferentialRunner(double n)
	{
		super(n);
	}

	public DifferentialRunner(double n, Number counter_tirminated)
	{
		super(n, counter_tirminated);
	}

	@Override
	protected void calculateWeights(double[] weights, int scansize, double[] ak)
	{
//		for (int y = 0; y < scansize; y++)
//		{
//			double cy = ak[y] - Map.units.get(y).concept.getInput();
//			for (int x = 0; x < scansize; x++)
//			{
//				double cx = ak[x] - Map.units.get(x).concept.getInput();
//				weights[x + y * scansize] = cy * cx - weights[x + y * scansize];
//			}
//		}
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
				weights[x + y * scansize] += Map.units.get(x).concept.getInput() * Map.units.get(y).concept.getInput();
	}

	@Override
	protected double calculateA(int y, double sum)
	{
		return sum;
	}

	@Override
	protected void tickParameters()
	{
		n = 0.002 * Math.exp(-0.2 * iteration);
	}
}
