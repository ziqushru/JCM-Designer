package program.map.runnners;

import java.util.ArrayList;
import java.util.List;

import graphics.gui.GraphScreen;
import program.map.Map;
import program.map.Relation;
import program.map.inference_rules.InferenceRule;
import program.map.learning_algorithms.HebbianLearning;
import program.map.learning_algorithms.NonLinear;
import program.utils.Log;
import program.utils.transferfunctions.TransferFunction;

public class Runner implements Runnable
{
	public static int				iteration;
	private static Number			counter_terminated;
	private static final int		stability_length	= 4;
	private static int				stability_counter;
	public static double[]			A_before;
	private static double			rounder;

	public static TransferFunction	transfer_function;
	public static HebbianLearning	hebbian_learning;
	public static InferenceRule		inference_rule;
	public static Parameters		parameters;

	public static Thread			runner_thread;

	public Runner()
	{
		this(null);
	}

	public Runner(Number counter_terminated)
	{
		Runner.counter_terminated = counter_terminated;
	}

	public void start()
	{
		if (Runner.hebbian_learning instanceof NonLinear && Parameters.g == 0) Parameters.g = 1;
		Runner.runner_thread = new Thread(this, "JFCM Runner");
		Runner.runner_thread.start();
	}

	@Override
	public void run()
	{
		List<double[]> A_overall = new ArrayList<double[]>();
		double[] A = new double[Map.units.size()];
		Runner.A_before = new double[A.length];
		for (int y = 0; y < A.length; y++)
			A[y] = Map.units.get(y).concept.getInput();
		A_overall.add(A.clone());
		double[] weights = Runner.W(A.length);
		Runner.iteration = 1;
		Runner.stability_counter = 0;
		if (Runner.hebbian_learning == null) 	rounder = 10000;
		else									rounder = 1000;
		
		do
		{
			for (int y = 0; y < A.length; y++)
				Runner.A_before[y] = A[y];

			Runner.inference_rule.calculateA(A, weights);
			if (Runner.hebbian_learning != null) Runner.hebbian_learning.calculateWeights(A, weights);

			Runner.update(A, weights);
			A_overall.add(A.clone());
		} while (!isTerminated(A));

		new GraphScreen("Outputs", "Outputs", A_overall);
	}

	private static synchronized void update(double[] A, double[] weights)
	{
		for (int i = 0; i < A.length; i++)
			A[i] = (double) ((int) (A[i] * rounder)) / rounder;
		for (int i = 0; i < weights.length; i++)
			weights[i] = (double) ((int) (weights[i] * rounder)) / rounder;
		if (Runner.hebbian_learning != null) Runner.hebbian_learning.update_parameters();
		Runner.iteration++;
	}

	private static synchronized boolean isTerminated(double[] A)
	{
		if (Runner.counter_terminated != null) if (Runner.iteration > Runner.counter_terminated.intValue())
			return true;
		else
			return false;
		
		int parameters_counter = 0;
		int valid_parameters_counter = 0;
		for (int x = 0; x < A.length; x++)
			if (Parameters.A_estimated[0][x] != Parameters.A_not_estimated && Parameters.A_estimated[1][x] != Parameters.A_not_estimated)
			{
				parameters_counter++;
				if (A[x] <= Parameters.A_estimated[0][x] && A[x] >= Parameters.A_estimated[1][x])
					valid_parameters_counter++;
			}
		if (parameters_counter > 0)
			if (valid_parameters_counter == parameters_counter) return true;
		
		for (int x = 0; x < A.length; x++)
		{
			if (Math.abs(Runner.A_before[x] - A[x]) >= Parameters.e)
			{
				Runner.stability_counter = 0;
				return false;
			}
		}
		if (++Runner.stability_counter == Runner.stability_length) return true;
		return false;
	}

	private static synchronized double[] W(int scansize)
	{
		double[] W = new double[scansize * scansize];
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
			{
				W[x + y * scansize] = 0;
				if (x != y) for (Relation relation : Map.units.get(y).relations)
					if (relation.getEndUnit().getName() == Map.units.get(x).getName())
					{
						W[x + y * scansize] = relation.getWeight();
						break;
					}
			}
		return W;
	}

	@SuppressWarnings("unused")
	private static synchronized void displayArray(double[] array, int scansize)
	{
		for (int y = 0; y < scansize; y++)
		{
			for (int x = 0; x < scansize; x++)
				Log.addLog(array[x + y * scansize] + "\t");
			Log.addLog("\n");
		}
		Log.consoleOut();
	}

	@SuppressWarnings("unused")
	private static synchronized void displayArray(double[] array)
	{
		for (int x = 0; x < array.length; x++)
			Log.addLog(array[x] + "\t");
		Log.consoleOut();
	}
}
