package jcmdesigner.graphics.menu;

import jcmdesigner.graphics.menu.top.configurations.ConfigurationsMenu;
import jcmdesigner.graphics.menu.top.FileMenu;
import jcmdesigner.graphics.menu.top.HelpMenu;
import jcmdesigner.graphics.menu.top.run.RunMenu;
import javafx.scene.control.MenuBar;
import jcmdesigner.program.App;

public class TopMenu extends MenuBar
{
	public TopMenu()
	{
		super();
		new FileMenu(this, "File");
		new ConfigurationsMenu(this, "Configurations");
		new RunMenu(this, "Run");
		new HelpMenu(this, "Help");
		this.setMaxWidth(312);
		this.setMinWidth(312);
		this.setPrefWidth(312);
		App.main_border_pane.setTop(this);
	}
}
