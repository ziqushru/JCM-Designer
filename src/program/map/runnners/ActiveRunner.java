package program.map.runnners;

import program.map.Map;

public class ActiveRunner extends Runner
{
	private double g;

	public ActiveRunner(double n, double g)
	{
		super(n);
		this.g = g;
	}

	public ActiveRunner(double n, double g, Number counter_tirminated)
	{
		super(n, counter_tirminated);
		this.g = g;
	}

	@Override
	protected void calculateWeights(double[] weights, int scansize, double[] ak)
	{
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
				weights[x + y * scansize] = (1 - g) * weights[x + y * scansize] + n * Map.units.get(x).concept.getInput() * Map.units.get(y).concept.getInput();
	}

	@Override
	protected double calculateA(int y, double sum)
	{
		return Map.units.get(y).concept.getInput() + sum;
	}

	@Override
	protected void tickParameters()
	{
		n = 0.002 * Math.exp(-0.2 * iteration);
		g = 0.008 * Math.exp(-iteration);
	}

}
