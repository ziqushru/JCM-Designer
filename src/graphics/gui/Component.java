package graphics.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Screen;
import program.Program;
import program.utils.Log;
import program.utils.Position;

public abstract class Component
{
	protected String text;
	protected Position position;
	protected int width;
	protected int height;
	protected int color;
	protected int[] pixels;
	protected int[] default_pixels;
	protected int[] hovered_pixels;
	protected int[] clicked_pixels;
	
	public Component(String text, int x_position, int y_position, int width, int height, int color)
	{
		this.text = text;
		this.position = new Position(x_position, y_position);
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		setPixels(new Color(color).brighter().darker().getRGB());
	}

	public Component(String text, int x_position, int y_position, String path)
	{
		BufferedImage button_default = null;
		BufferedImage button_hovered = null;
		BufferedImage button_clicked = null;
		
		try
		{
			button_default = ImageIO.read(this.getClass().getResource("/" + path + "/" + path + "_default.png"));
			button_hovered = ImageIO.read(this.getClass().getResource("/" + path + "/" + path + "_hovered.png"));
			button_clicked = ImageIO.read(this.getClass().getResource("/" + path + "/" + path + "_clicked.png"));
		}
		catch (IOException e)
		{
			Log.addLog("Error: loading images");
			Log.consoleOut();
			e.printStackTrace();
		}
		if (button_default == null || button_hovered == null || button_clicked == null)
		{
			Log.addLog("Error: image = null");
			Log.consoleOut();
			System.exit(-1);
		}
		
		this.text = text;
		this.position = new Position(x_position, y_position);
		this.width = button_default.getWidth();
		this.height = button_default.getHeight();
		this.color = 0;
		this.pixels = new int[width * height];
		this.default_pixels = new int[width * height];
		this.hovered_pixels = new int[width * height];
		this.clicked_pixels = new int[width * height];
		button_default.getRGB(0, 0, this.width, this.height, this.default_pixels, 0, this.width);
		button_hovered.getRGB(0, 0, this.width, this.height, this.hovered_pixels, 0, this.width);
		button_clicked.getRGB(0, 0, this.width, this.height, this.clicked_pixels, 0, this.width);
		setPixels(default_pixels);
	}
	
	protected void setPixels(int[] pixels)
	{
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				this.pixels[x + y * width] = pixels[x + y * width];
	}
	
	protected void setPixels(int color)
	{
		this.color = color;
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				this.pixels[x + y * width] = color;
	}
	
	public abstract void tick();
	
	protected void tickPixels()
	{
		for (int y = 0; y < height; y++)
		{
			int yy = y + position.y;
			for (int x = 0; x < width; x++)
			{
				int xx = x + position.x;
				int color = pixels[x + y * width];
				if (color != 0xFFAEC9)
					Screen.pixels[xx + yy * Program.WIDTH] = pixels[x + y * width];
			}
		}
	}
}
