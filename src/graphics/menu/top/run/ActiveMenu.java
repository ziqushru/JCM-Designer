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
import program.map.inference_rules.ModifiedKosko;
import program.map.runnners.Parameters;
import program.map.runnners.Runner;
import program.utils.transferfunctions.Bivalent;
import program.utils.transferfunctions.Continuous;
import program.utils.transferfunctions.NoTransferFunction;
import program.utils.transferfunctions.Sigmoid;
import program.utils.transferfunctions.Trivalent;

public class ActiveMenu implements Configurations
{
	private Stage				configurations_stage;
	private CustomTextField[]	parameters_text_fields;

	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		Runner.inference_rule = new ModifiedKosko();

		CustomGridPane grid_pane = new CustomGridPane(2, 6);

		main_comp.getChildren().add(new Label("Active Hebbian Learning Algorithm"));
		
		final int transfer_functions_length = 4;
		grid_pane.add(new Label("Transfer Functions"), 0, 0);
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
			grid_pane.add(transfer_functions[i], 0, 1 + i);
		}
	
		int parameters_length = 5;
		grid_pane.add(new Label("Parameters"), 1, 0);
		Label[] parameters_labels = new Label[parameters_length];
		parameters_labels[0] = new Label("η");
		parameters_labels[1] = new Label("g");
		parameters_labels[2] = new Label("l1");
		parameters_labels[3] = new Label("l2");
		parameters_labels[4] = new Label("e");
		this.parameters_text_fields = new CustomTextField[parameters_length];
		CustomGridPane[] parameters_grid_panes = new CustomGridPane[parameters_length];
		for (int i = 0; i < parameters_length; i++)
		{
			parameters_grid_panes[i] = new CustomGridPane(2, 1);
			parameters_grid_panes[i].add(parameters_labels[i], 0, 0);
			this.parameters_text_fields[i] = new CustomTextField(this);
			parameters_grid_panes[i].add(this.parameters_text_fields[i], 1, 0);
			grid_pane.add(parameters_grid_panes[i], 1, 1 + i);
		}
		main_comp.getChildren().add(grid_pane);

		Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);		

		Scene scene = new Scene(main_comp, 400, 520);
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
		String n = this.parameters_text_fields[0].getText().toString();
		String g = this.parameters_text_fields[1].getText().toString();
		String l1 = this.parameters_text_fields[1].getText().toString();
		String l2 = this.parameters_text_fields[1].getText().toString();
		String e = this.parameters_text_fields[2].getText().toString();
		if (n != null && !n.isEmpty()) Parameters.n = Double.parseDouble(n);
		if (g != null && !g.isEmpty()) Parameters.g = Double.parseDouble(g);
		if (l1 != null && !l1.isEmpty()) Parameters.l1 = Double.parseDouble(l1);
		if (l2 != null && !l2.isEmpty()) Parameters.l2 = Double.parseDouble(l2);
		if (e != null && !e.isEmpty()) Parameters.e = Double.parseDouble(e);
		if (Runner.transfer_function == null) Runner.transfer_function = new NoTransferFunction();
		Map.runner.start();
		this.configurations_stage.close();
	}
}
