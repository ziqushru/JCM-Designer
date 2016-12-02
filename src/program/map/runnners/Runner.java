package program.map.runnners;

import java.math.BigDecimal;
import java.math.RoundingMode;

import program.map.Map;
import program.map.Relation;
import program.utils.Log;
import program.utils.transferfunctions.TransferFunction;

public abstract class Runner implements Runnable
{
	protected final double n;
	private TransferFunction transfer_function;
	protected int iteration;
	private final Number counter_terminated;
	private double[] diff;
	
	public Runner(double n)
	{
		this(n, null);
	}
	
	public Runner(double n, Number counter_tirminated)
	{
		this.n = n;
		this.counter_terminated = counter_tirminated;
	}
	
	public void start()
	{
		Thread runner_thread = new Thread(this, "JFCM Runner");
		runner_thread.start();
	}
		
	@Override
	public void run()
	{
		double[] ak = new double[Map.units.size()];
		double[] weights = Runner.W(ak.length);
		if (counter_terminated == null) diff = new double[ak.length];
		iteration = 1;
		do
		{
			Log.addLog("W\n");
			Runner.displayArray(weights, ak.length);
			
			for (int y = 0; y < ak.length; y++)
			{
				double sum = 0;
				for (int x = 0; x < ak.length; x++)
				{
					if (x == y) continue;
					sum += weights[y + x * ak.length] * Map.units.get(x).concept.getInput();
				}
				double A = calculateA(y, sum);
				ak[y] = transfer_function.calculate(A);

				if (diff != null) diff[y] = Math.abs(Map.units.get(y).concept.getInput() - ak[y]);				
			}
			calculateWeights(weights, ak.length, ak);

			for (int x = 0; x < ak.length; x++)
				Map.units.get(x).concept.setInput(ak[x]);
			
			Log.addLog(iteration + ")");
			Runner.displayArray(ak);
			iteration++;
		}
		while (!isTerminated());
	}
	
	protected abstract void calculateWeights(double[] weights, int scansize, double[] ak);

	protected abstract double calculateA(int y, double sum);
	
	private synchronized boolean isTerminated()
	{
		if (counter_terminated != null)
			if (iteration > counter_terminated.intValue()) 	return true;
			else 												return false;
		for (int x = 0; x < diff.length; x++)
			if (diff[x] > 0.001)
				return false;
		return true;
	}
	
	private static synchronized double[] W(int scansize)
	{
		double[] W = new double[scansize* scansize];
		for (int y = 0; y < scansize; y++)
			for (int x = 0; x < scansize; x++)
			{
				W[x + y * scansize] = 0;
				
				if (x != y)
					for ( Relation relation : Map.units.get(y).relations)
						if (relation.getEndUnit().getName() == Map.units.get(x).getName())
						{
							W[x + y * scansize] = relation.getWeight();
							break;
						}
			}
		return W;
	}
	
	private static synchronized void displayArray(double[]array, int scansize)
	{
		for (int y = 0; y < scansize; y++)
		{
			for (int x = 0; x < scansize; x++)
				Log.addLog(BigDecimal.valueOf(array[x + y * scansize]).setScale(3, RoundingMode.HALF_UP).doubleValue() + "\t");
			Log.addLog("\n");
		}
		Log.consoleOut();
	}
	
	private static synchronized void displayArray(double[] array)
	{
		for (int x = 0; x < array.length; x++)
			Log.addLog(array[x] + "\t");
		Log.consoleOut();
	}
	
	public void setTrasferFunction(TransferFunction transfer_function)
	{
		this.transfer_function = transfer_function;
	}
}
