package graphics.gui;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;

import program.inputs.Mouse;

public abstract class Button extends Component
{
	private boolean hovered;

	public Button(String text, int x_position, int y_position, int width, int height, int color)
	{
		super(text, x_position, y_position, width, height, color);
	}

	public Button(String text, int x_position, int y_position, String path)
	{
		super(text, x_position, y_position, path);
	}
	
	public void tick()
	{
		if (isHovered() == true)
		{
			if (hovered == false)
			{
				if (color != 0)
					setPixels(new Color(color).brighter().getRGB());
				else
					setPixels(hovered_pixels);
			}
			hovered = true;
		}
		if (isHovered() == false)
		{
			if (hovered == true)
			{
				if (color != 0)
					setPixels(new Color(color).darker().getRGB());
				else
					setPixels(default_pixels);
			}
			hovered = false;
		}
		
		if (isPressed())
			pressed();
		
		if (isClicked())
			clicked();
		
		tickPixels();
	}
	
	protected abstract void pressed();
	protected abstract void clicked();
	
	private boolean isClicked()
	{
//		if (Mouse.clicked)
//		{
//			Mouse.clicked = false;
//			if (Mouse.position.x >= position.x && Mouse.position.y >= position.y)
//			{
//				if (Mouse.position.x < position.x + width && Mouse.position.y < position.y + height)
//				{
//					return true;
//				}
//			}
//		}
		return false;		
	}
	
	private boolean isPressed()
	{
		if (Mouse.pressed)
		{
			if (Mouse.position.x >= position.x && Mouse.position.y >= position.y)
			{
				if (Mouse.position.x < position.x + width && Mouse.position.y < position.y + height)
				{
					return true;
				}
			}
		}
		return false;		
	}
	
	private boolean isHovered()
	{
		Point mouse_position = MouseInfo.getPointerInfo().getLocation();
//		TODO: SwingUtilities.convertPointFromScreen(mouse_position, Program.window);
		mouse_position.y -= height;
		
		if (mouse_position.x >= position.x && mouse_position.y >= position.y)
		{
			if (mouse_position.x < position.x + width && mouse_position.y < position.y + height)
			{
				return true;
			}
		}
		return false;
	}	
}
