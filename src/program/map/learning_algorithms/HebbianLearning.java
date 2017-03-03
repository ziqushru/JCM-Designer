package program.map.learning_algorithms;

public interface HebbianLearning
{
	public void calculateWeights(double[] A, double[] weights);
	
	public void update_parameters();
}
