package program.utils.transferfunctions;

public class Continuous extends TransferFunction
{	
	@Override
	public double calculate(double A)
	{
		return (Math.exp(A) - Math.exp(-A)) / (Math.exp(A) + Math.exp(-A));
	}
}
