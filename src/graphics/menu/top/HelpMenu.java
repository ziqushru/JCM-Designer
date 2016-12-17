package graphics.menu.top;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class HelpMenu extends Menu
{
	private static final MenuItem	instructions	= new MenuItem("How To Use");
	private static final MenuItem	about			= new MenuItem("About");

	public HelpMenu(MenuBar top_menu, String text)
	{
		super(text);
		HelpMenu.instructions.setOnAction(event ->
		{
//			TODO: Create Window to display instructions for "How To Use"
		});
		this.getItems().add(instructions);
		
		HelpMenu.about.setOnAction(event ->
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Me");
			alert.setHeaderText("This program was developed by Jason Koutoulias");
			alert.setContentText("More info at https://github.com/ziqushru/JFCM-Design-App");
			alert.setResizable(false);
			alert.show();
		});
		this.getItems().add(about);
		top_menu.getMenus().add(this);
	}
}
