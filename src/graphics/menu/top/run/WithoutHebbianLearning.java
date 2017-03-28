package graphics.menu.top.run;

import graphics.gui.CustomGridPane;
import graphics.gui.CustomTextField;
import graphics.menu.top.configurations.Configurations;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;
import program.map.inference_rules.Kosko;
import program.map.inference_rules.ModifiedKosko;
import program.map.inference_rules.RescaledKosko;
import program.map.runnners.Parameters;
import program.map.runnners.Runner;
import program.utils.transferfunctions.Bivalent;
import program.utils.transferfunctions.Continuous;
import program.utils.transferfunctions.NoTransferFunction;
import program.utils.transferfunctions.Sigmoid;
import program.utils.transferfunctions.Trivalent;

public class WithoutHebbianLearning implements Configurations
{
	private Stage			configurations_stage;
	private CustomTextField	e_parameter;

	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		main_comp.getChildren().add(new Label("Without Hebbian Learning"));
		
		CustomGridPane grid_pane = new CustomGridPane(2, 7);
		
		final int inference_rules_length = 3;
		grid_pane.add(new Label("Inference Rules"), 0, 0);
		RadioButton[] inference_rules = new RadioButton[inference_rules_length];
		inference_rules[0] = new RadioButton("Kosko's");
		inference_rules[1] = new RadioButton("Modified Kosko's");
		inference_rules[2] = new RadioButton("Rescaled Kosko's");
		inference_rules[0].setOnAction(event -> Runner.inference_rule = new Kosko());
		inference_rules[1].setOnAction(event -> Runner.inference_rule = new ModifiedKosko());
		inference_rules[2].setOnAction(event -> Runner.inference_rule = new RescaledKosko());
		ToggleGroup inference_rules_group = new ToggleGroup();
		for (int i = 0; i < inference_rules_length; i++)
		{
			inference_rules[i].setToggleGroup(inference_rules_group);
			grid_pane.add(inference_rules[i], 0, 1 + i);
		}

		final int transfer_functions_length = 4;
		grid_pane.add(new Label("Transfer Functions"), 1, 0);
		RadioButton[] transfer_functions = new RadioButton[transfer_functions_length];
		transfer_functions[0] = new RadioButton("Bivalent");
		transfer_functions[1] = new RadioButton("Trivalent");
		transfer_functions[2] = new RadioButton("Sigmoid");
		transfer_functions[3] = new RadioButton("Continuous");
		transfer_functions[0].setOnAction(event -> Runner.transfer_function = new Bivalent());
		transfer_functions[1].setOnAction(event -> Runner.transfer_function = new Trivalent());
		transfer_functions[2].setOnAction(event -> Runner.transfer_function = new Sigmoid());
		transfer_functions[3].setOnAction(event -> Runner.transfer_function = new Continuous());
		ToggleGroup transfer_functions_group = new ToggleGroup();
		for (int i = 0; i < transfer_functions_length; i++)
		{
			transfer_functions[i].setToggleGroup(transfer_functions_group);
			grid_pane.add(transfer_functions[i], 1, 1 + i);
		}
		
		grid_pane.add(new Label("Parameters"), 0, 1 + transfer_functions_length);
		CustomGridPane parameters_grid_pane = new CustomGridPane(2, 1);
		parameters_grid_pane.add(new Label("e"), 0, 0);
		this.e_parameter = new CustomTextField(this);
		parameters_grid_pane.add(this.e_parameter, 1, 0);
		grid_pane.add(parameters_grid_pane, 0, 1 + transfer_functions_length + 1);
		
		main_comp.getChildren().add(grid_pane);

		Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);		

		Scene scene = new Scene(main_comp, 480, 575);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());
		this.configurations_stage = new Stage();
		this.configurations_stage.getIcons().add(new Image(Program.logo_path + ".png"));
		this.configurations_stage.setScene(scene);
		this.configurations_stage.setTitle("Run Configurations");
		this.configurations_stage.setResizable(false);
		this.configurations_stage.show();
	}

	@Override
	public void buttonOnAction()
	{
		String e = this.e_parameter.getText().toString();
		if (e != null && !e.isEmpty()) Parameters.e = Double.parseDouble(e);
		if (Runner.transfer_function == null) Runner.transfer_function = new NoTransferFunction();
		Map.runner.start();
		this.configurations_stage.close();
	}
}
