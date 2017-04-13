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

public strictfp class Runner implements Runnable
{
	private final String 		name;
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
		double[] A = new double[Map.units.size()];
		for (int y = 0; y < A.length; y++)
			A[y] = Map.units.get(y).concept.getInput();
		List<double[]> A_overall = new ArrayList<double[]>();
		A_overall.add(A.clone());
		double[] weights = Runner.W(A.length);
		if (this.hebbian_learning == null) 	rounder = 10000;
		else								rounder = 1000;
		
		do
		{
			this.inference_rule.calculateA(A, weights, this.transfer_function);
			
			this.roundArray(A);
			A_overall.add(A.clone());
			
			if (this.hebbian_learning != null)
				this.hebbian_learning.calculateWeights(A_overall, weights, this.parameters);

			this.roundArray(weights);
			this.parameters.iteration++;
			if (this.hebbian_learning != null)
				this.hebbian_learning.update_parameters(this.parameters);
			
		} while (!shouldTerminate(A_overall));

		new GraphScreen("Outputs", this.name, A_overall);
	}
	
	private synchronized void roundArray(double[] array)
	{
		for (int i = 0; i < array.length; i++)
			array[i] = (double) ((int) (array[i] * rounder)) / rounder;
	}

	private synchronized boolean shouldTerminate(List<double[]> A_overall)
	{
		double[] A = A_overall.get(A_overall.size() - 1);
		if (Parameters.A_desired_length > 0)
		{
			int valid_parameters_counter = 0;
			for (int x = 0; x < A.length; x++)
				if (Parameters.A_desired[0][x] != Parameters.A_desired_null && A[x] <= Parameters.A_desired[0][x]
				&& 	Parameters.A_desired[1][x] != Parameters.A_desired_null && A[x] >= Parameters.A_desired[1][x])
					valid_parameters_counter++;
			if (valid_parameters_counter == Parameters.A_desired_length)	return true;
		}

		if (this.parameters.data_driven != null)
		{
			if (this.parameters.iteration - 1 >= this.parameters.data_driven.size()) return true;
			for (int x = 0; x < A.length; x++)
				if (this.parameters.data_driven.get(this.parameters.iteration - 1)[x] != A[x])
					return false;
			return true;
		}
		
		double[] A_before = A_overall.get(A_overall.size() - 2);
		for (int x = 0; x < A.length; x++)
		{
			if (Math.abs(A_before[x] - A[x]) >= this.parameters.getE())
			{
				this.parameters.stability_counter = 0;
				return false;
			}
		}
		if (++this.parameters.stability_counter == Parameters.stability_length) return true;
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
