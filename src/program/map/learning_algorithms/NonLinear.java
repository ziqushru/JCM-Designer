package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public class NonLinear implements HebbianLearning
{
	@Override
	public void calculateWeights(double[] A, double[] weights)
	{
		for (int y = 0; y < A.length; y++)
			for (int x= 0; x < A.length; x++)
				weights[x + y * A.length] = Parameters.g * weights[x + y * A.length] + 
											Parameters.n * A[x] * (A[y] - signum(weights[x + y * A.length]) * A[x] * weights[x + y * A.length]);
	}
	
	private double signum(double number)
	{
		if (number  > 0)	return 1;
		if (number == 0)	return 0;
		return -1;
	}

	@Override
	public void update_parameters() {}
}
