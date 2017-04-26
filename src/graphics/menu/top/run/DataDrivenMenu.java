package graphics.menu.top.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import graphics.gui.CustomGridPane;
import graphics.gui.CustomStage;
import graphics.gui.CustomTextField;
import graphics.menu.top.configurations.RunConfigurations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import program.Program;
import program.map.learning_algorithms.DataDriven;

public class DataDrivenMenu extends RunConfigurations
{
	List<double[]> data_driven = new ArrayList<double[]>();
	
	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		CustomGridPane grid_pane = new CustomGridPane(2, 5);

		this.name = "Data-Driven Hebbian Learning";
		main_comp.getChildren().add(new Label(this.name));
		
		this.setTransferFunctionsUI(grid_pane);
		
		int parameters_length = 3;
		grid_pane.add(new Label("Parameters"), 1, 0);
		Label[] parameters_labels = new Label[parameters_length];
		parameters_labels[0] = new Label("η");
		parameters_labels[1] = new Label("g");
		parameters_labels[2] = new Label("e");
		this.text_fields = new CustomTextField[1][parameters_length];
		CustomGridPane[] parameters_grid_panes = new CustomGridPane[parameters_length];
		for (int i = 0; i < parameters_length; i++)
		{
			parameters_grid_panes[i] = new CustomGridPane(2, 1);
			parameters_grid_panes[i].add(parameters_labels[i], 0, 0);
			this.text_fields[0][i] = new CustomTextField(this);
			parameters_grid_panes[i].add(this.text_fields[0][i], 1, 0);
			grid_pane.add(parameters_grid_panes[i], 1, 1 + i);
		}
		Button data_driven_button = new Button("Select Data File");
		data_driven_button.setOnAction(event -> 
		{
			FileChooser file_chooser = new FileChooser();
			file_chooser.setTitle("Select Data File");
			file_chooser.getExtensionFilters().add(new ExtensionFilter("txt", "*.txt"));
			File file = file_chooser.showOpenDialog(Program.window);
			if (file  == null) return;
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null)
				{
					String[] data_string = line.split(" ");
					double[] data = new double[data_string.length];
					for (int i = 0; i < data.length; i++)
						data[i] = Double.parseDouble(data_string[i]);
					this.data_driven.add(data);
				}
				br.close();
			}
			catch (Exception e) { e.printStackTrace(); }
			this.configurations_stage.toFront();
		});
		grid_pane.add(data_driven_button, 1, 1 + parameters_length);		
		main_comp.getChildren().add(grid_pane);

		Button run_button = new Button("Run");
		run_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(run_button);		

		this.configurations_stage = new CustomStage("Run Configurations", 430, 455, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		this.setRunner(null, new DataDriven());
		this.runner.parameters.data_driven = this.data_driven; 
		this.runner.start();
		this.configurations_stage.close();
	}
}
