package program.map;

import java.util.ArrayList;
import java.util.List;

import graphics.Screen;
import javafx.scene.input.MouseButton;
import program.inputs.Mouse;
import program.units.Unit;
import program.utils.Position;

public final class Map
{
	public static List<Unit> units = new ArrayList<Unit>();
	public static Unit last_selected_unit = null;
	
	public static void addUnit(Unit unit)
	{
		Map.units.add(unit);
	}
	
	public static void removeUnit(Unit unit)
	{
		Map.units.remove(unit);
	}
	
	public static void checkRemoveUnit(Position position)
	{
		for (Unit unit : Map.units)
		{
			if (position.x >= unit.position.x && position.x < unit.position.x + unit.size)
			{
				if (position.y >= unit.position.y && position.y < unit.position.y + unit.size)
				{
					Map.units.remove(unit);
					if (unit.equals(last_selected_unit)) last_selected_unit = null;
					return;
				}
			}
		}
	}
	
	public static void clear()
	{
		Map.last_selected_unit = null;
		Map.units.clear();
	}
	
	private static boolean checkPressedNothing()
	{
		if (Mouse.pressed  && Mouse.button == MouseButton.PRIMARY)
		{
			for (Unit unit : Map.units)
				if (Mouse.position.x >= unit.position.x && Mouse.position.x < unit.position.x + unit.size)
					if (Mouse.position.y >= unit.position.y && Mouse.position.y < unit.position.y + unit.size)
						return false;
			return true;
		}
		return false;
	}
	
	public static void tick()
	{
		if (Map.checkPressedNothing())
			Map.last_selected_unit = null;
		
		if (Mouse.pressed  && Mouse.button == MouseButton.PRIMARY && Screen.unit != null)
		{
			Screen.unit.position.x = Mouse.position.x - Screen.unit.size / 2;
			Screen.unit.position.y = Mouse.position.y - Screen.unit.size / 2;
			Map.addUnit(Screen.unit);
			Screen.unit = null;
			Map.last_selected_unit = null;
			return;
		}
		
		if (Mouse.pressed && Mouse.button == MouseButton.SECONDARY)
			checkRemoveUnit(Mouse.position);
		
		if (last_selected_unit != null)
			last_selected_unit.drawSelected();
		
		for (Unit unit : Map.units)
			unit.tick();
		
		if (Mouse.pressed && Mouse.button == MouseButton.PRIMARY)
		{
			for (Unit unit : Map.units)
			{
				if (Mouse.position.x >= unit.position.x && Mouse.position.x < unit.position.x + unit.size)
				{
					if (Mouse.position.y >= unit.position.y && Mouse.position.y < unit.position.y + unit.size)
					{
						last_selected_unit = unit;
						break;
					}
				}
			}
		}
	}
	
	public static void tickUnitNames()
	{
		for (Unit unit : Map.units)
			unit.tickName();
	}
	
	public static void drawConnections()
	{
		for (Unit unit : units)
			unit.drawConnections();
	}
}
