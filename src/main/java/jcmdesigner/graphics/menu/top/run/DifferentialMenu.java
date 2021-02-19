package jcmdesigner.graphics.menu.top.run;

import jcmdesigner.graphics.gui.CustomGridPane;
import jcmdesigner.graphics.gui.CustomStage;
import jcmdesigner.graphics.gui.CustomTextField;
import jcmdesigner.graphics.menu.top.configurations.RunConfigurations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jcmdesigner.program.map.learning_algorithms.Differential;

public class DifferentialMenu extends RunConfigurations
{
	@Override
	public void openConfigurations()
	{
		final VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		final CustomGridPane grid_pane = new CustomGridPane(2, 5);

		this.name = "Differential Hebbian Learning";
		main_comp.getChildren().add(new Label(this.name));
		
		this.setTransferFunctionsUI(grid_pane);
		
		final int parameters_length = 2;
		grid_pane.add(new Label("Parameters"), 1, 0);
		final Label[] parameters_labels = new Label[parameters_length];
		parameters_labels[0] = new Label("N");
		parameters_labels[1] = new Label("e");
		this.text_fields = new CustomTextField[1][parameters_length];
		final CustomGridPane[] parameters_grid_panes = new CustomGridPane[parameters_length];
		for (int i = 0; i < parameters_length; i++)
		{
			parameters_grid_panes[i] = new CustomGridPane(2, 1);
			parameters_grid_panes[i].add(parameters_labels[i], 0, 0);
			this.text_fields[0][i] = new CustomTextField(this);
			parameters_grid_panes[i].add(this.text_fields[0][i], 1, 0);
			grid_pane.add(parameters_grid_panes[i], 1, 1 + i);
		}
		main_comp.getChildren().add(grid_pane);

		final Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);		

		this.configurations_stage = new CustomStage("Run Configurations", 430, 455, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		this.setRunner(null, new Differential());
		this.runner.parameters.setM(0.1 * (1 - 1 / (1.1 * this.runner.parameters.getN())));
		this.runner.start();
		this.configurations_stage.close();
	}
}
