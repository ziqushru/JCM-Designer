package program.map.inference_rules;

import program.utils.transferfunctions.TransferFunction;

public interface InferenceRule
{
	public void calculateA(double[] A, double[] weights, TransferFunction transfer_function);
}
