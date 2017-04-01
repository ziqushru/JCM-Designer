package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public class Differential extends HebbianLearning
{
	@Override
	public void calculateWeights(double[] A, double[] A_before, double[] weights, Parameters parameters)
	{
		for (int y = 0; y < A.length; y++)
		{
			double DAY = A[y] - A_before[y];
			if (DAY == 0) continue;
			for (int x= 0; x < A.length; x++)
			{
				double DAX = A[x] - A_before[x];
				if (x != y && weights[x + y * A.length] != 0)
					weights[x + y * A.length] = weights[x + y * A.length] + parameters.getM() * (DAY * DAX - weights[x + y * A.length]);
			}
		}
	}

	@Override
	public void update_parameters(int iteration, Parameters parameters)
	{
		parameters.setM(0.1 * (1 - iteration / (1.1 * parameters.getN())));
	}	
}
