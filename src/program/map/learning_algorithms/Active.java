package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public class Active extends HebbianLearning
{	
	@Override
	public void calculateWeights(double[] A, double[] A_before, double[] weights, Parameters parameters)
	{
		for (int y = 0; y < A.length; y++)
			for (int x= 0; x < A.length; x++)
				if (x != y)
					weights[x + y * A.length] = (1 - parameters.getG()) * weights[x + y * A.length] +
												parameters.getH() * A[y] * (A[x] - weights[x + y * A.length] * A[y]);
	}

	@Override
	public void update_parameters(int iteration, Parameters parameters)
	{
		parameters.setH(parameters.getB1() * Math.exp(-parameters.getL1() * iteration));
		parameters.setG(parameters.getB2() * Math.exp(-parameters.getL2() * iteration));
	}
}
