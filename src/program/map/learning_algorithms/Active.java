package program.map.learning_algorithms;

import java.util.List;

import program.map.runnners.Parameters;

public class Active extends HebbianLearning
{	
	@Override
	public void calculateWeights(List<double[]> A_overall, double[] weights, Parameters parameters)
	{
		double[] A = A_overall.get(A_overall.size() - 1);
		
		for (int y = 0; y < A.length; y++)
			for (int x= 0; x < A.length; x++)
				if (x != y)
					weights[x + y * A.length] = (1 - parameters.getG()) * weights[x + y * A.length] +
												parameters.getH() * A[y] * (A[x] - weights[x + y * A.length] * A[y]);
	}

	@Override
	public void update_parameters(Parameters parameters)
	{
		parameters.setH(parameters.getB1() * Math.exp(-parameters.getL1() * parameters.iteration));
		parameters.setG(parameters.getB2() * Math.exp(-parameters.getL2() * parameters.iteration));
	}
}
