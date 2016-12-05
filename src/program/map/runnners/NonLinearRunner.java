package program.map.runnners;

import program.map.Map;

public class NonLinearRunner extends Runner
{
	double g;
	
	public NonLinearRunner(double n, double g)
	{
		super(n);
		this.g = g;
	}
	
	public NonLinearRunner(double n, double g, Number counter_tirminated)
	{
		super(n, counter_tirminated);
		this.g = g;
	}

	@Override
	protected void calculateWeights(double[] weights, int scansize, double[] ak)
	{
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
			{
				if (weights[x + y * scansize] != 0)
					weights[x + y * scansize] += 	this.g *
													weights[x + y * scansize] +
													this.n * Map.units.get(y).concept.getInput() *
														(Map.units.get(y).concept.getInput() +
														weights[x + y * scansize] *
														Map.units.get(x).concept.getInput());
			}
	}

	@Override
	protected double calculateA(int y, double sum)
	{
		return  Map.units.get(y).concept.getInput() + sum;
	}

	@Override
	protected void tickParameters()
	{
		
	}

}
