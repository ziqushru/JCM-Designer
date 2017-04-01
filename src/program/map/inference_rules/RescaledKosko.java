package program.map.inference_rules;

import program.utils.transferfunctions.TransferFunction;

public class RescaledKosko implements InferenceRule
{
	public void calculateA(double[] A, double[] weights, TransferFunction transfer_function)
	{
		double sum;
		for (int y = 0; y < A.length; y++)
		{
			sum = 0;
			for (int x = 0; x < A.length; x++)
				sum += (2 * A[x] - 1) * weights[y + x * A.length];
			A[y] = transfer_function.calculate((2 * A[y] - 1) + sum);
		}
	}
}
