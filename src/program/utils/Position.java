package program.utils;

import java.awt.Point;

@SuppressWarnings("serial")
public class Position extends Point
{
	public Position()
	{}
	
	public Position(int x, int y)
	{
		super(x, y);
	}
	
	public Position(double x, double y)
	{
		super((int) x, (int) y);
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
