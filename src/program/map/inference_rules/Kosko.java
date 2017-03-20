package program.map.inference_rules;

import program.map.runnners.Runner;

public class Kosko implements InferenceRule
{	
	public void calculateA(double[] A, double[] weights)
	{
		double sum;
		for (int y = 0; y < A.length; y++)
		{
			sum = 0;
			for (int x = 0; x < A.length; x++)
			{
				if (x == y) continue;
				sum += weights[y + x * A.length] * A[x];
			}
			A[y] = Runner.transfer_function.calculate(sum);
		}
	}
}
