package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public abstract class HebbianLearning
{
	public abstract void calculateWeights(double[] A, double[] A_before, double[] weights, Parameters parameters);
	public abstract void update_parameters(int iteration, Parameters parameters);
}
