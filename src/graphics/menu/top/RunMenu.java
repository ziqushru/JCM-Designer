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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;
import program.map.inference_rules.Kosko;
import program.map.inference_rules.ModifiedKosko;
import program.map.inference_rules.RescaledKosko;
import program.map.learning_algorithms.Active;
import program.map.learning_algorithms.DataDriven;
import program.map.learning_algorithms.Differential;
import program.map.learning_algorithms.NonLinear;
import program.map.runnners.Parameters;
import program.map.runnners.Runner;
import program.utils.transferfunctions.Bivalent;
import program.utils.transferfunctions.Continuous;
import program.utils.transferfunctions.NoTransferFunction;
import program.utils.transferfunctions.Sigmoid;
import program.utils.transferfunctions.Trivalent;

public class RunMenu extends Menu
{
	private static final MenuItem run_configurations = new MenuItem("Run Configurations");
	public static boolean is_open = false;
	public static Stage settings_stage;

	public RunMenu(MenuBar top_menu, String text)
	{
		super(text);
		RunMenu.run_configurations.setOnAction(event -> RunMenu.openRunConfigurations());
		this.getItems().add(RunMenu.run_configurations);
		top_menu.getMenus().add(this);
	}

	private static void openRunConfigurations()
	{
		RunMenu.is_open = true;
		RunMenu.settings_stage = new Stage();

		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(main_comp, 480, 470);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());

		GridPane grid_pane = new GridPane();
		ColumnConstraints column = new ColumnConstraints();
	    column.setPercentWidth(50);
	    column.setHalignment(HPos.CENTER);
	    grid_pane.getColumnConstraints().addAll(column, column);
	    RowConstraints row = new RowConstraints();
	    row.setValignment(VPos.CENTER);
	    grid_pane.getRowConstraints().addAll(row, row, row, row, row, row, row, row, row, row);
		
	    final int inference_rules_length = 3;
		Label inference_rules_label = new Label("Inference Rules");
		grid_pane.add(inference_rules_label, 0, 0);
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

		final int hebbian_learning_algorithms_length = 4;
		Label hebbian_learning_label = new Label("Hebbian Learning Algorithms");
		grid_pane.add(hebbian_learning_label, 1, 0);
		RadioButton[] hebbian_learning_algorithms = new RadioButton[hebbian_learning_algorithms_length];
		hebbian_learning_algorithms[0] = new RadioButton("Differential");
		hebbian_learning_algorithms[1] = new RadioButton("Non Linear");
		hebbian_learning_algorithms[2] = new RadioButton("Active");
		hebbian_learning_algorithms[3] = new RadioButton("Data Driven");
		hebbian_learning_algorithms[0].setOnAction(event -> Runner.hebbian_learning = new Differential());
		hebbian_learning_algorithms[1].setOnAction(event -> Runner.hebbian_learning = new NonLinear());
		hebbian_learning_algorithms[2].setOnAction(event -> Runner.hebbian_learning = new Active());
		hebbian_learning_algorithms[3].setOnAction(event -> Runner.hebbian_learning = new DataDriven());
		ToggleGroup hebbian_learning_group = new ToggleGroup();
		for (int i = 0; i < hebbian_learning_algorithms_length; i++)
		{
			hebbian_learning_algorithms[i].setToggleGroup(hebbian_learning_group);
			grid_pane.add(hebbian_learning_algorithms[i], 1, 1 + i);
		}
		
		final int max_length = Math.max(inference_rules_length, hebbian_learning_algorithms_length);
		grid_pane.add(new Label(), 0, 1 + max_length);
		grid_pane.add(new Label(), 0, 1 + max_length);
		
		final int transfer_functions_length = 4;
		Label transfer_functions_label = new Label("Transfer Functions");
		grid_pane.add(transfer_functions_label, 0, 1 + max_length + 1);
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
			grid_pane.add(transfer_functions[i], 0, 1 + max_length + 1 + 1 + i);
		}
		
		int parameters_length = 4;
		Label parameters_label = new Label("Parameters");
		grid_pane.add(parameters_label, 1, 1 + max_length + 1);
		Label[] parameters_labels = new Label[parameters_length];
		parameters_labels[0] = new Label("η");
		parameters_labels[1] = new Label("γ");
		parameters_labels[2] = new Label("N");
		parameters_labels[3] = new Label("e");
		TextField[] parameters_text_fields = new TextField[parameters_length];
		GridPane[] parameters_grid_pane = new GridPane[parameters_length];
		for (int i = 0; i < parameters_length; i++)
		{
			parameters_grid_pane[i] = new GridPane();
			parameters_grid_pane[i].getColumnConstraints().addAll(column, column);	
			parameters_grid_pane[i].add(parameters_labels[i], 0, 0);
			parameters_text_fields[i] = new TextField();
	    	parameters_text_fields[i].setMaxWidth(64);
	    	parameters_text_fields[i].setAlignment(Pos.CENTER);
	    	parameters_text_fields[i].setFocusTraversable(false);
	    	parameters_grid_pane[i].add(parameters_text_fields[i], 1, 0);
	    	grid_pane.add(parameters_grid_pane[i], 1, 1 + max_length + 1 + 1 + i);
		}
	
		main_comp.getChildren().add(grid_pane);
	
		Button run_button = new Button("Run");
		run_button.setOnAction(event ->
		{
			RunMenu.run(parameters_text_fields);
		});

		main_comp.getChildren().add(run_button);

		RunMenu.settings_stage.addEventHandler(KeyEvent.KEY_PRESSED, event ->
		{
			if (event.getCharacter().equals(KeyCode.ENTER)) RunMenu.run(parameters_text_fields);
		});
			
		RunMenu.settings_stage.setScene(scene);
		RunMenu.settings_stage.setTitle("Run Configurations");
		RunMenu.settings_stage.setResizable(false);
		RunMenu.settings_stage.show();
	}
	
	private static void run(TextField[] parameters_text_fields)
	{
		String n = parameters_text_fields[0].getText().toString();
		String g = parameters_text_fields[1].getText().toString();
		String N = parameters_text_fields[2].getText().toString();
		String e = parameters_text_fields[3].getText().toString();
		if (n != null && !n.isEmpty())
			Parameters.n = Double.parseDouble(n);
		if (g != null && !g.isEmpty())
			Parameters.g = Double.parseDouble(g);
		if (N != null && !N.isEmpty())
			Parameters.N = Double.parseDouble(N);
		if (e != null && !e.isEmpty())
			Parameters.e = Double.parseDouble(e);
		if (Runner.transfer_function == null)
			Runner.transfer_function = new NoTransferFunction();
		Map.runner.start();
		
		is_open = false;
		RunMenu.settings_stage.close();
	}
}
