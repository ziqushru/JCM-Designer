package graphics.menu.top.run;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class RunMenu extends Menu
{
	private static final MenuItem without_hebbian_leaning = new MenuItem("Without Hebbian Learning");
	
	public RunMenu(MenuBar parent, String text)
	{
		super(text);
		RunMenu.without_hebbian_leaning.setOnAction(event -> new WithoutHebbianLearning().openConfigurations());
		this.getItems().add(RunMenu.without_hebbian_leaning);
		this.getItems().add(new WithHebbianLearning(this, "With Hebbian Learning"));
		parent.getMenus().add(this);
	}
}
