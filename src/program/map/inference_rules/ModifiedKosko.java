package program.map.inference_rules;

import program.utils.transferfunctions.TransferFunction;

public class ModifiedKosko implements InferenceRule
{
	public void calculateA(double[] A, double[] weights, TransferFunction transfer_function)
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
			A[y] = transfer_function.calculate(A[y] + sum);
		}
	}
}
