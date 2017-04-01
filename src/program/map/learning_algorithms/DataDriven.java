package program.map.learning_algorithms;

import program.map.runnners.Parameters;

public class DataDriven extends HebbianLearning
{
	@Override
	public void calculateWeights(double[] A, double[] A_before, double[] weights, Parameters parameters)
	{
		for (int y = 0; y < A.length; y++)
		{
			for (int x= 0; x < A.length; x++)
			{
				
			}
		}
	}

	@Override
	public void update_parameters(int iteration, Parameters parameters)
	{
		
	}
}
