package program.map.runnners;

import program.map.Map;
import program.map.Relation;
import program.map.inference_rules.InferenceRule;
import program.map.learning_algorithms.HebbianLearning;
import program.utils.Log;
import program.utils.transferfunctions.TransferFunction;

public class Runner implements Runnable
{
	public static int				iteration;
	private final Number			counter_terminated;
	private static final int		stability_length = 4;
	private static int				stability_counter;
	public static double[]			A_before;

	public static TransferFunction	transfer_function;
	public static HebbianLearning	hebbian_learning;
	public static InferenceRule		inference_rule;
	public static Parameters		parameters;

	public static Thread runner_thread;

	public Runner() { this(null); }

	public Runner(Number counter_terminated) { this.counter_terminated = counter_terminated; }

	public void start()
	{
		Runner.runner_thread = new Thread(this, "JFCM Runner");
		Runner.runner_thread.start();
	}

	@Override
	public void run()
	{
		double[] A = new double[Map.units.size()];
		Runner.A_before = new double[A.length];
		for (int y = 0; y < A.length; y++)
		{
			A[y] = Map.units.get(y).concept.getInput();
			Runner.A_before[y] = 2 * A[y];
		}
		double[] weights = Runner.W(A.length);
		Runner.iteration = 1;
		Log.addLog((Runner.iteration - 1) + ")");
		Runner.displayArray(A);
		
		Runner.stability_counter = 0;
		do
		{
//			if (iteration != 1)
				for (int y = 0; y < A.length; y++)
					Runner.A_before[y] = A[y];
				
			Runner.inference_rule.calculateA(A, weights);
			Runner.hebbian_learning.calculateWeights(A, weights);

			this.update(A);
			Log.addLog((Runner.iteration - 1) + ")");
			Runner.displayArray(A);
		} while (!isTerminated(A));
	}

	private void update(double[] A)
	{
		Runner.iteration++;
		Runner.hebbian_learning.update_parameters();
	}

	private synchronized boolean isTerminated(double[] A)
	{
		if (counter_terminated != null)
			if (Runner.iteration > counter_terminated.intValue())	return true;
			else													return false;
		for (int x = 0; x < Runner.A_before.length; x++)
			if (Runner.A_before[x] - A[x] > 0.001)
			{
				Runner.stability_counter = 0;
				return false;
			}
		if (++Runner.stability_counter == Runner.stability_length) 	return true;
		return false;
	}

	private static synchronized double[] W(int scansize)
	{
		double[] W = new double[scansize * scansize];
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
			{
				W[x + y * scansize] = 0;
				if (x != y)
					for (Relation relation : Map.units.get(y).relations)
						if (relation.getEndUnit().getName() == Map.units.get(x).getName())
						{
							W[x + y * scansize] = relation.getWeight();
							break;
						}
			}
		return W;
	}

//	private static synchronized void displayArray(double[] array, int scansize)
//	{
//		for (int y = 0; y < scansize; y++)
//		{
//			for (int x = 0; x < scansize; x++)
//				Log.addLog(BigDecimal.valueOf(array[x + y * scansize]).setScale(3, RoundingMode.HALF_UP).doubleValue() + "\t");
//			Log.addLog("\n");
//		}
//		Log.consoleOut();
//	}

	private static synchronized void displayArray(double[] array)
	{
		for (int x = 0; x < array.length; x++)
			Log.addLog(array[x] + "\t");
		Log.consoleOut();
	}
}
