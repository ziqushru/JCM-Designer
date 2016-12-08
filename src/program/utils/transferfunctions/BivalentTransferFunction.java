package program.utils.transferfunctions;

public class BivalentTransferFunction extends TransferFunction
{	
	@Override
	public double calculate(double A)
	{
		if (A > 0)	return 1;
		else			return 0;
	}
}
