package jcmdesigner.graphics.menu.top.run;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class WithHebbianLearning extends Menu
{
	private static final MenuItem	differential_hebbian_leaning = new MenuItem("Differential Hebbian Learning");
	private static final MenuItem	non_linear_hebbian_leaning = new MenuItem("Non Linear Hebbian Learning");
	private static final MenuItem	active_hebbian_leaning = new MenuItem("Active Hebbian Learning");
	private static final MenuItem	data_driven_hebbian_leaning = new MenuItem("Data-Driven Hebbian Learning");
	
	public WithHebbianLearning(RunMenu parent, String text)
	{
		super(text);
		WithHebbianLearning.differential_hebbian_leaning.setOnAction(event -> new DifferentialMenu().openConfigurations());
		WithHebbianLearning.non_linear_hebbian_leaning.setOnAction(event -> new NonLinearMenu().openConfigurations());
		WithHebbianLearning.active_hebbian_leaning.setOnAction(event -> new ActiveMenu().openConfigurations());
		WithHebbianLearning.data_driven_hebbian_leaning.setOnAction(event -> new DataDrivenMenu().openConfigurations());
		this.getItems().add(WithHebbianLearning.differential_hebbian_leaning);
		this.getItems().add(WithHebbianLearning.non_linear_hebbian_leaning);
		this.getItems().add(WithHebbianLearning.active_hebbian_leaning);
		this.getItems().add(WithHebbianLearning.data_driven_hebbian_leaning);
	}	
}
