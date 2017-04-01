package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public class NonLinear extends HebbianLearning
{
	@Override
	public void calculateWeights(double[] A, double[] A_before, double[] weights, Parameters parameters)
	{
		for (int y = 0; y < A.length; y++)
			for (int x= 0; x < A.length; x++)
				if (x != y && weights[x + y * A.length] != 0)
					weights[x + y * A.length] = parameters.getG() * weights[x + y * A.length] + 
												parameters.getH() * A[x] * (A[y] - NonLinear.signum(weights[x + y * A.length]) * A[x] * weights[x + y * A.length]);
	}
	
	private static double signum(double number)
	{
		if (number  > 0)	return 1;
		if (number == 0)	return 0;
							return -1;
	}

	@Override
	public void update_parameters(int iteration, Parameters parameters) {}
}
