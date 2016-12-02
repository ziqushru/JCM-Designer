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
		double ct = 0.1 * (1 - iteration / (1.1 * 20));
		for (int y = 0; y < scansize; y++)
		{
			double cy = ak[y] - Map.units.get(y).concept.getInput();
			for (int x = 0; x < scansize; x++)
			{
				double cx = ak[x] - Map.units.get(x).concept.getInput();
				if (cy != 0) weights[x + y * scansize] += ct * (cy * cx - weights[x + y * scansize]);
			}
		}
	}
	
	@Override
	protected double calculateA(int y, double sum)
	{
		return sum;
	}
}
