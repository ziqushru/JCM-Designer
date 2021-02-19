package jcmdesigner.graphics.menu.top.configurations;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ConfigurationsMenu extends Menu
{
	private static final MenuItem input_vector_configurations = new MenuItem("Input Vector");
	private static final MenuItem estimated_output_configurations = new MenuItem("Desired Outputs");
	private static final MenuItem fuzzy_configurations = new MenuItem("Fuzzy");
	
	public static Stage settings_stage;

	public ConfigurationsMenu(MenuBar top_menu, String text)
	{
		super(text);
		ConfigurationsMenu.input_vector_configurations.setOnAction(event -> new InputVectorMenu().openConfigurations());
		ConfigurationsMenu.estimated_output_configurations.setOnAction(event -> new DesiredOutputsMenu().openConfigurations());
		ConfigurationsMenu.fuzzy_configurations.setOnAction(event -> new FuzzyMenu().openConfigurations());
		this.getItems().add(ConfigurationsMenu.input_vector_configurations);
		this.getItems().add(ConfigurationsMenu.estimated_output_configurations);
		this.getItems().add(ConfigurationsMenu.fuzzy_configurations);
		top_menu.getMenus().add(this);
	}
}
