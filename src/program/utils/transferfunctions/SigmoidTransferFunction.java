package program.utils.transferfunctions;

public class SigmoidTransferFunction extends TransferFunction
{	
	@Override
	public double calculate(double A)
	{
		return 1 / (1 + Math.exp(-A));
	}
}
