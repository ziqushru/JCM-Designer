package jcmdesigner.graphics.menu.top.run;

import java.util.ArrayList;

import jcmdesigner.graphics.gui.CustomGridPane;
import jcmdesigner.graphics.gui.CustomStage;
import jcmdesigner.graphics.gui.CustomTextField;
import jcmdesigner.graphics.menu.top.configurations.RunConfigurations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jcmdesigner.program.map.Map;
import jcmdesigner.program.map.learning_algorithms.Active;

public class ActiveMenu extends RunConfigurations
{
	private CustomTextField[] steps_text_fields;
	
	@Override
	public void openConfigurations()
	{
		final VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		final int parameters_length = 5;
		final int units_size = Map.units.size();
		final CustomGridPane grid_pane;
		final int rows;
		if (units_size > parameters_length)		rows = units_size;
		else									rows = parameters_length;
		grid_pane = new CustomGridPane(3, 1 + rows);
		
		this.name = "Active Hebbian Learning";
		main_comp.getChildren().add(new Label(this.name));

		this.setTransferFunctionsUI(grid_pane);

		grid_pane.add(new Label("Parameters"), 1, 0);
		final Label[] parameters_labels = new Label[parameters_length];
		parameters_labels[0] = new Label("b1");
		parameters_labels[1] = new Label("l1");
		parameters_labels[2] = new Label("b2");
		parameters_labels[3] = new Label("l2");
		parameters_labels[4] = new Label("e");
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
		grid_pane.add(new Label("Steps"), 2, 0);
		this.steps_text_fields = new CustomTextField[units_size];
		final CustomGridPane[] steps_grid_panes = new CustomGridPane[units_size];
		for (int i = 0; i < units_size; i++)
		{
			steps_grid_panes[i] = new CustomGridPane(2, 1);
			steps_grid_panes[i].add(new Label(Map.units.get(i).getName()), 0, 0);
			this.steps_text_fields[i] = new CustomTextField(this);
			steps_grid_panes[i].add(this.steps_text_fields[i], 1, 0);
			grid_pane.add(steps_grid_panes[i], 2, 1 + i);
		}		
		main_comp.getChildren().add(grid_pane);

		final Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);

		this.configurations_stage = new CustomStage("Run Configurations", 800, 220 + rows * 60, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		this.setRunner(null, new Active());
		this.runner.parameters.active_steps = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < this.steps_text_fields.length; i++)
			this.runner.parameters.active_steps.add(new ArrayList<Integer>());
		for (int i = 0; i < this.steps_text_fields.length; i++)
			this.runner.parameters.active_steps.get(Integer.parseInt(this.steps_text_fields[i].getText()) - 1).add(i);
		for (ArrayList<Integer> list : this.runner.parameters.active_steps)
			if (list.isEmpty())
				this.runner.parameters.active_steps.remove(list);			
		this.runner.start();
		this.configurations_stage.close();
	}
}
