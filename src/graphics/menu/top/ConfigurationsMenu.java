package graphics.menu.top;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ConfigurationsMenu extends Menu
{
	private static final MenuItem input_configurations = new MenuItem("Input & Output");
	private static final MenuItem fuzzy_configurations = new MenuItem("Fuzzy");
	
	public static Stage settings_stage;

	public ConfigurationsMenu(MenuBar top_menu, String text)
	{
		super(text);
		ConfigurationsMenu.input_configurations.setOnAction(event -> new InputOutputMenu().openConfigurations());
		ConfigurationsMenu.fuzzy_configurations.setOnAction(event -> new FuzzyMenu().openConfigurations());
		this.getItems().add(ConfigurationsMenu.input_configurations);
		this.getItems().add(ConfigurationsMenu.fuzzy_configurations);
		top_menu.getMenus().add(this);
	}
}
