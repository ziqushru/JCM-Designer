package program.map.learning_algorithms;

import java.util.List;

import program.map.runnners.Parameters;

public abstract class HebbianLearning
{
	public abstract void calculateWeights(List<double[]> A_overall, double[] weights, Parameters parameters);
	public abstract void update_parameters(Parameters parameters);
}
