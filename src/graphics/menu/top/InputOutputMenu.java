package graphics.menu.top;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;
import program.map.runnners.Parameters;

public class InputOutputMenu extends Menu
{
	private static final MenuItem input_configurations = new MenuItem("Input & Output");
	public static Stage settings_stage;

	public InputOutputMenu(MenuBar top_menu, String text)
	{
		super(text);
		InputOutputMenu.input_configurations.setOnAction(event -> InputOutputMenu.openInputOutputConfigurations());
		this.getItems().add(InputOutputMenu.input_configurations);
		top_menu.getMenus().add(this);
	}

	private static void openInputOutputConfigurations()
	{
		InputOutputMenu.settings_stage = new Stage();

		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.CENTER);

	    final int input_vector_legth = Map.units.size();		
		GridPane[] grid_pane = new GridPane[2];
		ColumnConstraints column = new ColumnConstraints();
	    column.setHalignment(HPos.CENTER);
	    column.setPercentWidth(100 / input_vector_legth);
	    RowConstraints row = new RowConstraints();
	    row.setValignment(VPos.CENTER);
	    	   
	    for (int i = 0; i < grid_pane.length; i++)
	    {
	    	grid_pane[i] = new GridPane();
	    	grid_pane[i].getRowConstraints().addAll(row, row);
		    for (int j = 0; j < input_vector_legth; j++)
		    	grid_pane[i].getColumnConstraints().add(column);
	    }
	    grid_pane[1].getRowConstraints().add(row);
    	grid_pane[1].getColumnConstraints().add(column);
		
		Label input_vector_label = new Label("Input Vector");
		Label estimated_input_vector_label = new Label("Estimated Output Vector");
		grid_pane[1].add(new Label("Upper"), 0, 1);
		grid_pane[1].add(new Label("Lower"), 0, 2);
		
		Label[][] labels = new Label[grid_pane.length][input_vector_legth];
		TextField[][] text_fields = new TextField[grid_pane.length + 1][input_vector_legth];

		for (int i = 0; i < grid_pane.length; i++)
			for (int j = 0; j < input_vector_legth; j++)
			{
				labels[i][j] = new Label(Map.units.get(j).getName());
				grid_pane[i].add(labels[i][j], i + j, 0);
				for (int k = 0; k <= i; k++)
				{
					text_fields[k + i][j] = new TextField();
					text_fields[k + i][j].setId("text_field");
					text_fields[k + i][j].setAlignment(Pos.CENTER);
					text_fields[k + i][j].setFocusTraversable(false);
					if (i == 0) text_fields[k + i][j].setPromptText(Map.units.get(j).concept.getInput() + "");
					else 		text_fields[k + i][j].setPromptText(Parameters.A_estimated[k][j] + "");
					text_fields[k + i][j].setOnKeyPressed(event ->
					{
						if (event.getCode() == KeyCode.ENTER) InputOutputMenu.updateValues(text_fields);
					});
					grid_pane[i].add(text_fields[k + i][j], i + j, 1 + k);
				}
			}
		main_comp.getChildren().add(input_vector_label);
		main_comp.getChildren().add(grid_pane[0]);
		main_comp.getChildren().add(new Label(""));
		main_comp.getChildren().add(estimated_input_vector_label);
		main_comp.getChildren().add(grid_pane[1]);
		main_comp.getChildren().add(new Label(""));
	
		Button update_button = new Button("Update Values");
		update_button.setOnAction(event -> InputOutputMenu.updateValues(text_fields));

		main_comp.getChildren().add(update_button);

		int width = 0;
		for (int i = 0; i < input_vector_legth; i++)
			width += (Map.units.get(i).getName().length() + 25) * 6;
		Scene scene = new Scene(main_comp, width, 370);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());
		InputOutputMenu.settings_stage.setScene(scene);
		InputOutputMenu.settings_stage.setTitle("Input/Output Configurations");
		InputOutputMenu.settings_stage.setResizable(false);
		InputOutputMenu.settings_stage.show();
	}
	
	private static void updateValues(TextField[][] text_fields)
	{
		String[][] texts = new String[text_fields.length][text_fields[0].length];
		
		for (int i = 0; i < texts.length; i++)
			for (int j = 0; j < texts[0].length; j++)
				texts[i][j] = text_fields[i][j].getText().toString();
			
		for (int i = 0; i < texts.length; i++)
			for (int j = 0; j < texts[0].length; j++)
				if (texts[i][j] != null && !texts[i][j].isEmpty())
					if (i == 0) Map.units.get(j).concept.setInput(Double.parseDouble(texts[i][j]));
					else 		Parameters.A_estimated[i - 1][j] = Double.parseDouble(texts[i][j]);

		InputOutputMenu.settings_stage.close();
	}
}
