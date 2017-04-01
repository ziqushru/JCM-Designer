package program.map.runnners;

import java.util.ArrayList;
import java.util.List;

import graphics.gui.GraphScreen;
import program.map.Map;
import program.map.Relation;
import program.map.inference_rules.InferenceRule;
import program.map.learning_algorithms.HebbianLearning;
import program.utils.Log;
import program.utils.transferfunctions.TransferFunction;

public class Runner implements Runnable
{
	private final String 		name;
	private int					iteration;
	private static final int	stability_length	= 4;
	private int					stability_counter;
	private double				rounder;
	private Thread				runner_thread;

	private TransferFunction	transfer_function;
	private InferenceRule		inference_rule;
	public HebbianLearning		hebbian_learning;
	public Parameters			parameters			= new Parameters();
	
	public Runner(String name, HebbianLearning hebbian_learning)
	{
		this.name = name;
		this.hebbian_learning = hebbian_learning;
	}

	public void start()
	{
		this.runner_thread = new Thread(this, "JFCM Runner(" + this.name + ")");
		this.runner_thread.setPriority(Thread.MAX_PRIORITY);
		this.runner_thread.start();
	}

	@Override
	public void run()
	{
		List<double[]> A_overall = new ArrayList<double[]>();
		double[] A = new double[Map.units.size()];
		double[] A_before = new double[A.length];
		for (int y = 0; y < A.length; y++)	A[y] = Map.units.get(y).concept.getInput();
		A_overall.add(A.clone());
		double[] weights = Runner.W(A.length);
		this.iteration = 1;
		this.stability_counter = 0;
		if (this.hebbian_learning == null) 	rounder = 10000;
		else								rounder = 1000;
		
		do
		{
			for (int y = 0; y < A.length; y++)
				A_before[y] = A[y];

			this.inference_rule.calculateA(A, weights, this.transfer_function);
			
			if (this.hebbian_learning != null)
				this.hebbian_learning.calculateWeights(A, A_before, weights, this.parameters);

			this.update(A, weights);
			A_overall.add(A.clone());
		} while (!isTerminated(A, A_before));

		new GraphScreen("Outputs", this.name, A_overall);
	}

	private synchronized void update(double[] A, double[] weights)
	{
		this.iteration++;
		for (int i = 0; i < A.length; i++)
			A[i] = (double) ((int) (A[i] * this.rounder)) / this.rounder;
		for (int i = 0; i < weights.length; i++)
			weights[i] = (double) ((int) (weights[i] * rounder)) / rounder;
		if (this.hebbian_learning != null)
			this.hebbian_learning.update_parameters(this.iteration, this.parameters);
	}

	private synchronized boolean isTerminated(double[] A, double[] A_before)
	{
		int parameters_counter = 0;
		int valid_parameters_counter = 0;
		for (int x = 0; x < A.length; x++)
			if (Parameters.A_estimated[0][x] != Parameters.A_not_estimated)
			{
				parameters_counter++;
				if (A[x] <= Parameters.A_estimated[0][x]) valid_parameters_counter++;
			}
			else if (Parameters.A_estimated[1][x] != Parameters.A_not_estimated)
			{
				parameters_counter++;
				if (A[x] >= Parameters.A_estimated[1][x]) valid_parameters_counter++;
			}
		if (parameters_counter > 0)
			if (valid_parameters_counter == parameters_counter) return true;
		
		for (int x = 0; x < A.length; x++)
		{
			if (Math.abs(A_before[x] - A[x]) >= this.parameters.getE())
			{
				this.stability_counter = 0;
				return false;
			}
		}
		if (++this.stability_counter == Runner.stability_length) return true;
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

	public void setTransferFunction(TransferFunction transfer_function)
	{
		this.transfer_function = transfer_function;
	}

	public void setInferenceRule(InferenceRule inference_rule)
	{
		this.inference_rule = inference_rule;
	}
}
