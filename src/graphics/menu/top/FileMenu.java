package graphics.menu.top;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import program.Program;
import program.map.Map;

public class FileMenu extends Menu
{
	private static final MenuItem	new_file	= new MenuItem("New");
	private static final MenuItem	open_file	= new MenuItem("Open");
	private static final MenuItem	save_file	= new MenuItem("Save");
	private static final MenuItem	exit_file	= new MenuItem("Exit");

	public FileMenu(MenuBar top_menu, String text)
	{
		super(text);
		FileMenu.new_file.setOnAction(event -> Map.clear());
		FileMenu.open_file.setOnAction(event -> Map.load());
		FileMenu.save_file.setOnAction(event -> Map.save());
		FileMenu.exit_file.setOnAction(event -> Program.closeProgram());
		this.getItems().add(FileMenu.new_file);
		this.getItems().add(FileMenu.open_file);
		this.getItems().add(FileMenu.save_file);
		this.getItems().add(FileMenu.exit_file);
		top_menu.getMenus().add(this);
	}
}
