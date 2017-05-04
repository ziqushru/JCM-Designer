package program.map.inference_rules;

import java.util.List;

import program.map.runnners.Parameters;
import program.utils.transferfunctions.TransferFunction;

public class ActiveKosko implements InferenceRule
{
	private Parameters parameters;
	
	public ActiveKosko(Parameters parameters)
	{
		this.parameters = parameters;
	}
	
	public void calculateA(double[] A, double[] weights, TransferFunction transfer_function)
	{
		int step = (parameters.active_steps.size() - 1 + parameters.iteration - 1) % (parameters.active_steps.size() - 1);
		List<Integer> activation_concepts;
		if (step == 0)	activation_concepts = parameters.active_steps.get(step);
		else			activation_concepts = parameters.active_steps.get(step - 1);
		List<Integer> 	activated_concepts = parameters.active_steps.get(step);
		for (int y : activated_concepts)
		{
			double sum = 0;
			for (int x : activation_concepts)
				sum += weights[y + x * A.length] * A[x];
			A[y] = transfer_function.calculate(A[y] + sum);
		}
	}
}
