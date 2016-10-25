package graphics.gui;

import java.util.ArrayList;
import java.util.List;

public class GUI
{
	public static List<Component> components = new ArrayList<Component>();
	
	public static void addComponent(Component component)
	{
		components.add(component);
	}
	
	public static void removeComponent(Component component)
	{
		components.remove(component);
	}
	
	public static void tick()
	{
		for (Component component : components)
			component.tick();
	}
}
