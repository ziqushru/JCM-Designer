package program.map.runnners;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Parameters extends ArrayList<Double>
{
	public double getN() { return this.get(0); }
	public double getM() { return this.get(1); }
	public double getH() { return this.get(0); }
	public double getG() { return this.get(1); }
	public double getB1() {	return this.get(2); }
	public double getB2() { return this.get(3); }
	public double getL1() {	return this.get(4);	}
	public double getL2() { return this.get(5);	}
	public double getE() { return this.get(this.size() - 1); }
	public void setN(double N) { this.remove(0); this.add(0, N); }
	public void setM(double m) { this.remove(1); this.add(1, m); }
	public void setH(double h) { this.remove(0); this.add(0, h); }
	public void setG(double g) { this.remove(1); this.add(1, g); }
	public void setB1(double b1) { this.remove(2); this.add(2, b1); }
	public void setB2(double b2) { this.remove(3); this.add(3, b2); }
	public void setL1(double l1) { this.remove(4); this.add(4, l1); }
	public void setL2(double l2) { this.remove(5); this.add(5, l2); }
	public void setE(double e) { this.remove(this.size() - 1); this.add(e); }
	
	public static double		A_estimated[][];
	public static final double	A_not_estimated	= 1337;
	public static String[]		fuzzy_string_values = new String[0];
	public static double[]		fuzzy_double_values = new double[0];
}
