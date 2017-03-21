package graphics.menu;

import graphics.menu.top.ConfigurationsMenu;
import graphics.menu.top.FileMenu;
import graphics.menu.top.HelpMenu;
import graphics.menu.top.RunMenu;
import javafx.scene.control.MenuBar;
import program.Program;

public class TopMenu extends MenuBar
{
	public TopMenu()
	{
		super();
		new FileMenu(this, "File");
		new ConfigurationsMenu(this, "Configurations");
		new RunMenu(this, "Run");
		new HelpMenu(this, "Help");
		Program.layout.setTop(this);
	}
}
