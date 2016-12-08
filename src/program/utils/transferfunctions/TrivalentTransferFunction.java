package program.utils.transferfunctions;

public class TrivalentTransferFunction extends TransferFunction
{	
	@Override
	public double calculate(double A)
	{
		if (A > 0)		return 1;
		if (A == 0)		return 0;
		 				return -1;
	}
}
