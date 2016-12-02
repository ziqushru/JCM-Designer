package program.utils.transferfunctions;

public class SigmoidTrasferFunction extends TransferFunction
{	
	@Override
	public double calculate(double sum)
	{
		if (sum < 0)			return -1;
		else if (sum == 0)		return 0;
		else					return 1;
	}
}
