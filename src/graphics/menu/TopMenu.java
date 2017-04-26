package graphics.menu;

import graphics.menu.top.configurations.ConfigurationsMenu;
import graphics.menu.top.FileMenu;
import graphics.menu.top.HelpMenu;
import graphics.menu.top.run.RunMenu;
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
		this.setMaxWidth(320);
		this.setMinWidth(320);
		this.setPrefWidth(320);
		Program.main_border_pane.setTop(this);
	}
}
