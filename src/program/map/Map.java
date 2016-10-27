package program.map;

import java.util.ArrayList;
import java.util.List;

import program.units.Unit;

public final class Map
{
	public static List<Unit>	units				= new ArrayList<Unit>();
	public static Unit			last_selected_unit	= null;

	public static void clear()
	{
		for (int i = 0; i < Map.units.size(); i++)
			units.get(i--).remove();
		Map.units.clear();
		Map.last_selected_unit = null;
	}

	public static void tick()
	{
		if (last_selected_unit != null) last_selected_unit.drawSelected();
	}
}
