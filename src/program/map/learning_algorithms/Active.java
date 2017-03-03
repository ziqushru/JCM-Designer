package program.map.learning_algorithms;

import program.map.runnners.Parameters;
import program.map.runnners.Runner;

public class Active implements HebbianLearning
{
	@Override
	public void calculateWeights(double[] A, double[] weights)
	{
		for (int y = 0; y < A.length; y++)
			for (int x= 0; x < A.length; x++)
				weights[x + y * A.length] = (1 - Parameters.g) * weights[x + y * A.length] +
											Parameters.n * A[y] * (A[x] - weights[x + y * A.length] * A[y]);
	}

	@Override
	public void update_parameters()
	{
		Parameters.n = 0.002 * Math.exp(-0.2 * Runner.iteration);
		Parameters.g = 0.008 * Math.exp(-Runner.iteration);
	}
}
