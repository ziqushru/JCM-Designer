package program.map.learning_algorithms;

import program.map.runnners.Parameters;
import program.map.runnners.Runner;

public class Differential implements HebbianLearning
{
	public Differential()
	{
		Parameters.m = 0.1 * (1 - 1 / (1.1 * Parameters.N));
	}
	
	@Override
	public void calculateWeights(double[] A, double[] weights)
	{
		for (int y = 0; y < A.length; y++)
		{
			double DAY = A[y] - Runner.A_before[y];
			for (int x= 0; x < A.length; x++)
			{
				if (DAY == 0) continue;
				double DAX = A[x] - Runner.A_before[x];
				weights[x + y * A.length] = weights[x + y * A.length] + Parameters.m * (DAY * DAX - weights[x + y * A.length]);
			}
		}
	}

	@Override
	public void update_parameters()
	{
		Parameters.m = 0.1 * (1 - Runner.iteration / (1.1 * Parameters.N));
	}	
}
