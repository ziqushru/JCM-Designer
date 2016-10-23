package graphics.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import program.Program;
import program.map.Map;

public class TopMenu extends MenuBar
{
	private static final Menu		file_menu	= new Menu("File");
	private static final MenuItem	new_file	= new MenuItem("New");
	private static final MenuItem	open_file	= new MenuItem("Open");
	private static final MenuItem	save_file	= new MenuItem("Save");
	private static final MenuItem	exit_file	= new MenuItem("Exit");

	private static final Menu		help_menu	= new Menu("Help");
	private static final MenuItem	about_help	= new MenuItem("About");

	public TopMenu()
	{
		super();

		TopMenu.new_file.setOnAction(event -> Map.clear());
		TopMenu.exit_file.setOnAction(event -> Program.closeProgram());
		TopMenu.file_menu.getItems().add(TopMenu.new_file);
		TopMenu.file_menu.getItems().add(TopMenu.open_file);
		TopMenu.file_menu.getItems().add(TopMenu.save_file);
		TopMenu.file_menu.getItems().add(TopMenu.exit_file);
		this.getMenus().add(TopMenu.file_menu);

		TopMenu.help_menu.getItems().add(about_help);
		this.getMenus().add(help_menu);

		Program.layout.setTop(this);
	}
}
