package program.units.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Screen;
import program.utils.Log;
import program.utils.Position;

public abstract class Entity
{
	private int pixels[];
	public int size;
	public Position position;
	private int color;

	public Entity(int x, int y, int size, int color)
	{
		this.position = new Position(x, y);
		this.size = size;
		this.pixels = new int[size * size];
		this.color = color;
		clearPixels();
	}
	
	public Entity(int x, int y, String path)
	{
		this.position = new Position(x, y);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(this.getClass().getResource("/" + path + ".png"));
		}
		catch (IOException e) {	Log.addLog("Error: loading image"); Log.consoleOut(); }
		this.size = image.getWidth();
		this.pixels = new int[size * size];
		image.getRGB(0, 0, size, size, pixels, 0, size);
		this.color = -1;
	}
	
	protected void clearPixels()
	{
		if (color == -1) return;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++)
				pixels[x + y * size] = color;
	}
	
	public void setSize(int size)
	{
		this.size = size;
		this.pixels = new int[size * size];
		clearPixels();
	}
	
	protected void normalizePosition()
	{
		if (position.x < 0) position.x = 0;
		if (position.y < 0) position.y = 0;
		if (position.x > Screen.WIDTH - size) position.x = Screen.WIDTH - size;
		if (position.y > Screen.HEIGHT - size) position.y = Screen.HEIGHT - size;	
	}
	
	public abstract void tick();
	
	protected void tickPixels()
	{
		for (int y = 0; y < size; y++)
		{
			int yy = y + position.y;
			for (int x = 0; x < size; x++)
			{
				int xx = x + position.x;
				int color = pixels[x + y * size];
				if (color != 0xFFFFAEC9)
					Screen.pixels[xx + yy * Screen.WIDTH] = color;
			}
		}
	}
}
