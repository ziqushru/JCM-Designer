package jcmdesigner.program.map.learning_algorithms;

import java.util.List;

import jcmdesigner.program.map.runnners.Parameters;

public class Active extends HebbianLearning
{	
	@Override
	public void calculateWeights(List<double[]> A_overall, double[] weights, Parameters parameters)
	{
		double[] A = A_overall.get(A_overall.size() - 1);
		
		int step = (parameters.active_steps.size() - 1 + parameters.iteration - 1) % (parameters.active_steps.size() - 1);
		List<Integer> activation_concepts = parameters.active_steps.get(step);
		
		for (int y : activation_concepts)
			for (int x = 0; x < A.length; x++)
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
