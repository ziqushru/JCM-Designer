package graphics.menu.top.configurations;

import graphics.gui.CustomGridPane;
import graphics.gui.CustomTextField;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.runnners.Parameters;

public class FuzzyMenu implements Configurations
{
	private Stage				configurations_stage;
	private Label[]				fuzzy_values_labels;
	private CustomTextField[]	fuzzy_values_text_fields;
	private Button				update_button;
	private int					fuzzy_values_length	= 0;
	private GridPane			grid_pane;

	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		main_comp.getChildren().add(new Label("Fuzzy Values"));
		
		ObservableList<String> options = FXCollections.observableArrayList("Without Fuzzy Values", "2", "3", "4", "5");
		final ComboBox<String> combo_box = new ComboBox<String>(options);
		combo_box.setPromptText("Select Fuzzy Value");

		combo_box.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) ->
		{
			if (this.grid_pane != null)
			{				
				for (int i = 0; i < this.fuzzy_values_length; i++)
				{
					main_comp.getChildren().remove(this.fuzzy_values_labels[i]);
					main_comp.getChildren().remove(this.fuzzy_values_text_fields[i]);
				}
				main_comp.getChildren().remove(this.grid_pane);
			}
			main_comp.getChildren().remove(this.update_button);

			if (t1.equals("Without Fuzzy Values"))
			{
				this.configurations_stage.setHeight(204);
				this.fuzzy_values_length = 0;
			}
			else
			{
				this.configurations_stage.setHeight(315);
				this.fuzzy_values_length = Integer.parseInt(t1);
				Parameters.fuzzy_string_values = new String[this.fuzzy_values_length];
				Parameters.fuzzy_double_values = new double[this.fuzzy_values_length];
	
				this.grid_pane = new CustomGridPane(fuzzy_values_length, 2);	
				this.fuzzy_values_labels = new Label[this.fuzzy_values_length];
	
				switch (this.fuzzy_values_length)
				{
					case 2:
						this.fuzzy_values_labels[0] = new Label("Low");
						this.fuzzy_values_labels[1] = new Label("High");
						Parameters.fuzzy_string_values[0] = "Low";
						Parameters.fuzzy_string_values[1] = "High";	
						break;
					case 3:
						this.fuzzy_values_labels[0] = new Label("Low");
						this.fuzzy_values_labels[1] = new Label("Mid");
						this.fuzzy_values_labels[2] = new Label("High");
						Parameters.fuzzy_string_values[0] = "Low";
						Parameters.fuzzy_string_values[1] = "Mid";
						Parameters.fuzzy_string_values[2] = "High";
						break;
					case 4:
						this.fuzzy_values_labels[0] = new Label("Very Low");
						this.fuzzy_values_labels[1] = new Label("Low");
						this.fuzzy_values_labels[2] = new Label("High");
						this.fuzzy_values_labels[3] = new Label("Very High");
						Parameters.fuzzy_string_values[0] = "Very Low";
						Parameters.fuzzy_string_values[1] = "Low";
						Parameters.fuzzy_string_values[2] = "High";
						Parameters.fuzzy_string_values[3] = "Very High";
						break;
					case 5:
						this.fuzzy_values_labels[0] = new Label("Very Low");
						this.fuzzy_values_labels[1] = new Label("Low");
						this.fuzzy_values_labels[2] = new Label("Mid");
						this.fuzzy_values_labels[3] = new Label("High");
						this.fuzzy_values_labels[4] = new Label("Very High");
						Parameters.fuzzy_string_values[0] = "Very Low";
						Parameters.fuzzy_string_values[1] = "Low";
						Parameters.fuzzy_string_values[2] = "Mid";
						Parameters.fuzzy_string_values[3] = "High";
						Parameters.fuzzy_string_values[4] = "Very High";
						break;
				}
				this.fuzzy_values_text_fields = new CustomTextField[this.fuzzy_values_length];
				for (int i = 0; i < this.fuzzy_values_length; i++)
				{
					this.grid_pane.add(this.fuzzy_values_labels[i], i, 0);
					this.fuzzy_values_text_fields[i] = new CustomTextField(this);
					this.grid_pane.add(this.fuzzy_values_text_fields[i], i, 1);
				}
				main_comp.getChildren().add(this.grid_pane);
			}
			main_comp.getChildren().add(this.update_button);
			this.configurations_stage.setWidth(296 + 60 * fuzzy_values_length);
		});
		main_comp.getChildren().add(combo_box);
		
		this.update_button = new Button("Update Values");
		this.update_button.setOnAction(event -> this.buttonOnAction());

		main_comp.getChildren().add(update_button);

		Scene scene = new Scene(main_comp, 280, 170);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());
		this.configurations_stage = new Stage();
		this.configurations_stage.getIcons().add(new Image(Program.logo_path + ".png"));
		this.configurations_stage.setScene(scene);
		this.configurations_stage.setTitle("Fuzzy Configurations");
		this.configurations_stage.setResizable(false);
		this.configurations_stage.show();
	}

	@Override
	public void buttonOnAction()
	{
		String[] fuzzy_values = new String[this.fuzzy_values_text_fields.length];
		for (int i = 0; i < fuzzy_values.length; i++)
			fuzzy_values[i] = this.fuzzy_values_text_fields[i].getText().toString();
		for (int i = 0; i < fuzzy_values.length; i++)
			if (fuzzy_values[i] != null && !fuzzy_values[i].isEmpty())
				Parameters.fuzzy_double_values[i] = Double.parseDouble(fuzzy_values[i]);
		this.configurations_stage.close();
	}
}