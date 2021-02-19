package jcmdesigner.program.utils.transferfunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NoTransferFunction extends TransferFunction
{
	@Override
	public double calculate(double A)
	{
		return BigDecimal.valueOf(A).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}
}
