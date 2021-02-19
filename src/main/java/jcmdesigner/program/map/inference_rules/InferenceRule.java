package jcmdesigner.program.map.inference_rules;

import jcmdesigner.program.utils.transferfunctions.TransferFunction;

public interface InferenceRule
{
	public void calculateA(double[] A, double[] weights, TransferFunction transfer_function);
}
