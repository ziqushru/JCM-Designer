package program.map.runnners;

import java.util.ArrayList;
import java.util.List;

import program.map.Map;

@SuppressWarnings("serial")
public class Parameters extends ArrayList<Double>
{
	public double getN() {	return this.get(0); }
	public double getM() {	return this.get(1); }
	public double getH() {	return this.get(0); }
	public double getG() {	return this.get(1); }
	public double getB1() {	return this.get(0); }
	public double getL1() {	return this.get(1);	}
	public double getB2() {	return this.get(2); }
	public double getL2() {	return this.get(3);	}
	public double getE() {	return this.get(this.size() - 1); }
	public void setN(double N) {	this.remove(0);				this.add(0, N); }
	public void setM(double m) {	this.remove(1);				this.add(1, m); }
	public void setH(double h) {	this.remove(0);				this.add(0, h); }
	public void setG(double g) {	this.remove(1);				this.add(1, g); }
	public void setB1(double b1) {	this.remove(0);				this.add(0, b1); }
	public void setL1(double l1) {	this.remove(1);				this.add(1, l1); }
	public void setB2(double b2) {	this.remove(2);				this.add(2, b2); }
	public void setL2(double l2) {	this.remove(3);				this.add(3, l2); }
	public void setE(double e) { this.remove(this.size() - 1);	this.add(e); }
	
	public List<ArrayList<Integer>>	active_steps;
	public List<double[]>			data_driven;
	
	public int						iteration			= 1;
	public static double			A_desired_length;
	public static double[][]		A_desired;
	public static final double		A_desired_null 		= 1337;
	public static final int			stability_length	= 5;
	public int						stability_counter   = 0;
	public static final int			max_iterations		= 500;
	
	public static String[]			fuzzy_string_values = new String[0];
	public static double[]			fuzzy_double_values = new double[0];
	
	public static double calculateGE(List<double[]> A_overall)
	{
		final int N = Map.units.size();
		final int M = Map.units.size();
		final double[] output = A_overall.get(A_overall.size() - 1);
		double sum = 0;
		for (int x = 0; x < N; x++)
		{
			double desired_output = 0;
			for (int y = 0; y < A_desired.length; y++)
				if (A_desired[y][x] != A_desired_null)
					desired_output += A_desired[y][x];
			desired_output /= 2;
			sum += (output[x] - desired_output) * (output[x] - desired_output);
		}
		return 0;
	}
	
	public static double calculateMSE(List<double[]> A_overall)
	{
		final int N = Map.units.size();
		final double[] output = A_overall.get(A_overall.size() - 1);
		double sum = 0;
		for (int x = 0; x < N; x++)
		{
			if (A_desired[0][x] != A_desired_null)
				if (output[x] < (A_desired[0][x] + A_desired[1][x]) / 2)
					sum += (output[x] - A_desired[0][x]) * (output[x] - A_desired[0][x]);
				else if (output[x] > (A_desired[0][x] + A_desired[1][x]) / 2)
					sum += (output[x] - A_desired[1][x]) * (output[x] - A_desired[1][x]);
		}
		return (double) ((int) ((sum / A_desired_length) * 10000000)) / 10000000;
	}
}
