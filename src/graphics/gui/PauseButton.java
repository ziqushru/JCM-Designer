package graphics.gui;

import program.Program;

public class PauseButton extends Button
{
	private static boolean pressed;
	private static boolean clicked;
	
	public PauseButton(String text, int x_position, int y_position, int width, int height, int color)
	{
		super(text, x_position, y_position, width, height, color);
		pressed = false;
		clicked = false;
	}

	@Override
	protected void pressed()
	{
		if (!pressed)
		{
			pressed = true;
			Program.paused = true;
		}
	}

	@Override
	protected void clicked()
	{
		if (pressed && !clicked)
		{
			clicked = true;
			return;
		}
		
		if (pressed && clicked)
		{
			pressed = false;
			clicked = false;
			Program.paused = false;
		}
	}
}
