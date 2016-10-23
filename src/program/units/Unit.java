package program.units;

import java.util.ArrayList;
import java.util.List;

import graphics.Screen;
import javafx.scene.input.MouseButton;
import program.inputs.Mouse;
import program.map.Map;
import program.units.entities.Entity;

public class Unit extends Entity
{
	private String				name;
	private static final int	pressed_color	= 0xFF2222FF;
	private static final int	selected_color	= 0xFFFF2222;
	private List<Unit>			connections;
	
	public Unit(String name, int x, int y, int size, int color)
	{
		super(x, y, size, color);
		this.name = name;
		this.connections = new ArrayList<Unit>();
	}

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
		this.name = name;
		this.connections = new ArrayList<Unit>();
	}

	@Override
	public void tick()
	{
		if (checkPressed())
		{
			if (check()) return;
			drawPressed();
			drag();
		}
			
		normalizePosition();
		
		tickPixels();
	}
	
	public void drawConnections()
	{
		for (Unit unit : connections)
			Screen.graphics_context.strokeLine(this.position.x + this.size / 2, this.position.y + this.size / 2, unit.position.x + unit.size / 2, unit.position.y + unit.size / 2);
	}
	
	private boolean check()
	{
		if (Map.last_selected_unit != null)
			if (this.equals(Map.last_selected_unit) == false)
			{
				this.connections.add(Map.last_selected_unit);
				Map.last_selected_unit = null;
				return true;
			}
		return false;
	}
	
	private boolean checkPressed()
	{
		if (Mouse.pressed && Mouse.button == MouseButton.PRIMARY)
			if (Mouse.position.x >= this.position.x && Mouse.position.x < this.position.x + this.size)
				if (Mouse.position.y >= this.position.y && Mouse.position.y < this.position.y + this.size)
					return true;
		return false;
	}
	
	private void drag()
	{
		this.position.x = Mouse.position.x - this.size / 2;
		this.position.y = Mouse.position.y - this.size / 2;
	}
	
	private void drawOutline(int offset, int color)
	{
		for (int y = 0; y < this.size; y++)
		{
			int yy = y + this.position.y;
			int xx = 0 + this.position.x - offset;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int y = 0; y < this.size; y++)
		{
			int yy = y 			+ this.position.y;
			int xx = this.size 	+ this.position.x + offset - 1;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int x = 0; x < this.size; x++)
		{
			int yy = 0 + this.position.y - offset;
			int xx = x + this.position.x;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int x = 0; x < this.size; x++)
		{
			int yy = this.size + 	this.position.y + offset - 1;
			int xx = x + 			this.position.x;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}
	}

	private void drawPressed()
	{
		drawOutline(1, Unit.pressed_color);
	}
	
	public void drawSelected()
	{
		if (!checkPressed())
			drawOutline(1, Unit.selected_color);
	}
	
	public void tickName()
	{
		Screen.graphics_context.fillText(this.name, this.position.x + this.size / 2 - this.name.length() / 2 * 7.5, this.position.y - 5);
	}
}
