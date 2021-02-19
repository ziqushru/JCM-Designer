package jcmdesigner.graphics.menu.top.run;

import jcmdesigner.graphics.gui.CustomGridPane;
import jcmdesigner.graphics.gui.CustomRadioButton;
import jcmdesigner.graphics.gui.CustomStage;
import jcmdesigner.graphics.gui.CustomTextField;
import jcmdesigner.graphics.menu.top.configurations.RunConfigurations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WithoutHebbianLearning extends RunConfigurations
{
	private CustomRadioButton inference_rules;
	
	@Override
	public void openConfigurations()
	{
		final VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		this.name = "Without Hebbian Learning";
		main_comp.getChildren().add(new Label(this.name));
		
		final CustomGridPane grid_pane = new CustomGridPane(3, 5);
		
		this.setTransferFunctionsUI(grid_pane);

		final int inference_rules_length = 3;
		grid_pane.add(new Label("Inference Rules"), 1, 0);
		this.inference_rules = new CustomRadioButton(this, "Kosko's", "Modified Kosko's", "Rescaled Kosko's");
		for (int i = 0; i < inference_rules_length; i++)
			grid_pane.add(inference_rules.get(i), 1, 1 + i);
		
		grid_pane.add(new Label("Parameters"), 2, 0);
		final CustomGridPane parameters_grid_pane = new CustomGridPane(2, 1);
		parameters_grid_pane.add(new Label("e"), 0, 0);
		this.text_fields = new CustomTextField[][] {{new CustomTextField(this)}};
		parameters_grid_pane.add(this.text_fields[0][0], 1, 0);
		grid_pane.add(parameters_grid_pane, 2, 1);
		
		main_comp.getChildren().add(grid_pane);

		final Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);		

		this.configurations_stage = new CustomStage("Run Configurations", 675, 445, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		this.setRunner(inference_rules, null);
		this.runner.start();
		this.configurations_stage.close();
	}
}
