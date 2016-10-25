package graphics.gui;

public class TestButton extends Button
{
	public TestButton(String text, int x_position, int y_position, int width, int height, int color)
	{
		super(text, x_position, y_position, width, height, color);
	}
	
	public TestButton(String text, int x_position, int y_position, String path)
	{
		super(text, x_position, y_position, path);
	}
	
	@Override
	protected void pressed()
	{
		if (color == 0) setPixels(clicked_pixels);
	}

	@Override
	protected void clicked()
	{
	}

}
