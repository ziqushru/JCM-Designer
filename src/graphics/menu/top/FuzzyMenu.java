package graphics.menu.top;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.runnners.Parameters;

public class FuzzyMenu implements Configurations
{
	private static Stage		settings_stage;
	private static Label[]		fillers;
	private static Label[]		fuzzy_values_labels;
	private static TextField[]	fuzzy_values_text_fields;
	private static Button		update_button;
	private static int			fuzzy_values_length	= 0;
	private static GridPane		grid_pane;

	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.CENTER);
		
		FuzzyMenu.fillers = new Label[3];
		for (int i = 0; i < FuzzyMenu.fillers.length; i++)
			FuzzyMenu.fillers[i] = new Label();

		ObservableList<String> options = FXCollections.observableArrayList("Without Fuzzy Values", "2", "3", "4", "5");
		final ComboBox<String> combo_box = new ComboBox<String>(options);
		combo_box.setPromptText("Fuzzy Value");

		combo_box.valueProperty().addListener(((ChangeListener<String>) (ov, t, t1) ->
		{
			if (FuzzyMenu.grid_pane != null)
			{				
				for (int i = 0; i < FuzzyMenu.fuzzy_values_length; i++)
				{
					main_comp.getChildren().remove(FuzzyMenu.fuzzy_values_labels[i]);
					main_comp.getChildren().remove(FuzzyMenu.fuzzy_values_text_fields[i]);
				}
				main_comp.getChildren().remove(FuzzyMenu.grid_pane);
			}
			for (int i = 0; i < FuzzyMenu.fillers.length; i++)
				main_comp.getChildren().remove(FuzzyMenu.fillers[i]);
			main_comp.getChildren().remove(FuzzyMenu.update_button);

			if (t1.equals("Without Fuzzy Values"))
			{
				FuzzyMenu.fuzzy_values_length = 0;
				Parameters.fuzzy_string_values = new String[0];
				Parameters.fuzzy_double_values = new double[0];
				FuzzyMenu.fuzzy_values_text_fields = new TextField[0];
			}
			else
			{
				FuzzyMenu.fuzzy_values_length = Integer.parseInt(t1);
				Parameters.fuzzy_string_values = new String[FuzzyMenu.fuzzy_values_length];
				Parameters.fuzzy_double_values = new double[FuzzyMenu.fuzzy_values_length];
				
	
				FuzzyMenu.grid_pane = new GridPane();
				ColumnConstraints column = new ColumnConstraints();
				column.setPercentWidth(100 / fuzzy_values_length);
				column.setHalignment(HPos.CENTER);
				for (int i = 0; i < FuzzyMenu.fuzzy_values_length; i++)
					grid_pane.getColumnConstraints().add(column);
				RowConstraints row = new RowConstraints();
				row.setValignment(VPos.CENTER);
				grid_pane.getRowConstraints().addAll(row, row);
	
				FuzzyMenu.fuzzy_values_labels = new Label[FuzzyMenu.fuzzy_values_length];
	
				if (FuzzyMenu.fuzzy_values_length == 2)
				{
					FuzzyMenu.fuzzy_values_labels[0] = new Label("Low");
					FuzzyMenu.fuzzy_values_labels[1] = new Label("High");
					Parameters.fuzzy_string_values[0] = "Low";
					Parameters.fuzzy_string_values[1] = "High";	
				}
				else if (FuzzyMenu.fuzzy_values_length == 3)
				{
					FuzzyMenu.fuzzy_values_labels[0] = new Label("Low");
					FuzzyMenu.fuzzy_values_labels[1] = new Label("Mid");
					FuzzyMenu.fuzzy_values_labels[2] = new Label("High");
					Parameters.fuzzy_string_values[0] = "Low";
					Parameters.fuzzy_string_values[1] = "Mid";
					Parameters.fuzzy_string_values[2] = "High";
				}
				else if (FuzzyMenu.fuzzy_values_length == 4)
				{
					FuzzyMenu.fuzzy_values_labels[0] = new Label("Very Low");
					FuzzyMenu.fuzzy_values_labels[1] = new Label("Low");
					FuzzyMenu.fuzzy_values_labels[2] = new Label("High");
					FuzzyMenu.fuzzy_values_labels[3] = new Label("Very High");
					Parameters.fuzzy_string_values[0] = "Very Low";
					Parameters.fuzzy_string_values[1] = "Low";
					Parameters.fuzzy_string_values[2] = "High";
					Parameters.fuzzy_string_values[3] = "Very High";
				}
				else if (FuzzyMenu.fuzzy_values_length == 5)
				{
					FuzzyMenu.fuzzy_values_labels[0] = new Label("Very Low");
					FuzzyMenu.fuzzy_values_labels[1] = new Label("Low");
					FuzzyMenu.fuzzy_values_labels[2] = new Label("Mid");
					FuzzyMenu.fuzzy_values_labels[3] = new Label("High");
					FuzzyMenu.fuzzy_values_labels[4] = new Label("Very High");
					Parameters.fuzzy_string_values[0] = "Very Low";
					Parameters.fuzzy_string_values[1] = "Low";
					Parameters.fuzzy_string_values[2] = "Mid";
					Parameters.fuzzy_string_values[3] = "High";
					Parameters.fuzzy_string_values[4] = "Very High";
				
				}
				FuzzyMenu.fuzzy_values_text_fields = new TextField[FuzzyMenu.fuzzy_values_length];
				for (int i = 0; i < FuzzyMenu.fuzzy_values_length; i++)
				{
					FuzzyMenu.grid_pane.add(FuzzyMenu.fuzzy_values_labels[i], i, 0);
					FuzzyMenu.fuzzy_values_text_fields[i] = new TextField();
					FuzzyMenu.fuzzy_values_text_fields[i].setMaxWidth(64);
					FuzzyMenu.fuzzy_values_text_fields[i].setAlignment(Pos.CENTER);
					FuzzyMenu.fuzzy_values_text_fields[i].setFocusTraversable(false);
					FuzzyMenu.fuzzy_values_text_fields[i].setOnKeyPressed(event ->
					{
						if (event.getCode() == KeyCode.ENTER) this.buttonOnAction();
					});
					FuzzyMenu.grid_pane.add(FuzzyMenu.fuzzy_values_text_fields[i], i, 1);
				}
				main_comp.getChildren().add(FuzzyMenu.fillers[0]);
				main_comp.getChildren().add(FuzzyMenu.grid_pane);
				main_comp.getChildren().add(FuzzyMenu.fillers[1]);
			}
			main_comp.getChildren().add(FuzzyMenu.fillers[2]);
			main_comp.getChildren().add(FuzzyMenu.update_button);
			settings_stage.setWidth(280 + 30 * fuzzy_values_length);
		}));
		main_comp.getChildren().add(combo_box);
		
		FuzzyMenu.update_button = new Button("Update Values");
		FuzzyMenu.update_button.setOnAction(event -> this.buttonOnAction());


		main_comp.getChildren().add(fillers[2]);
		main_comp.getChildren().add(update_button);

		FuzzyMenu.settings_stage = new Stage();
		Scene scene = new Scene(main_comp, 300, 240);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());
		FuzzyMenu.settings_stage.setScene(scene);
		FuzzyMenu.settings_stage.setTitle("Fuzzy Configurations");
		FuzzyMenu.settings_stage.setResizable(false);
		FuzzyMenu.settings_stage.show();
	}

	@Override
	public void buttonOnAction()
	{
		String[] fuzzy_values = new String[FuzzyMenu.fuzzy_values_text_fields.length];
		for (int i = 0; i < fuzzy_values.length; i++)
			fuzzy_values[i] = FuzzyMenu.fuzzy_values_text_fields[i].getText().toString();

		for (int i = 0; i < fuzzy_values.length; i++)
			if (fuzzy_values[i] != null && !fuzzy_values[i].isEmpty()) Parameters.fuzzy_double_values[i] = Double.parseDouble(fuzzy_values[i]);

		FuzzyMenu.settings_stage.close();
	}
}
