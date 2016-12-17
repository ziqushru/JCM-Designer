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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;
import program.map.runnners.ActiveRunner;
import program.map.runnners.DifferentialRunner;
import program.map.runnners.NonLinearRunner;
import program.utils.transferfunctions.BivalentTransferFunction;
import program.utils.transferfunctions.ContinuousTransferFunction;
import program.utils.transferfunctions.SigmoidTransferFunction;
import program.utils.transferfunctions.TrivalentTransferFunction;

public class RunMenu extends Menu
{
	private static final MenuItem run_configurations = new MenuItem("Run Configurations");

	public RunMenu(MenuBar top_menu, String text)
	{
		super(text);
		RunMenu.run_configurations.setOnAction(event -> RunMenu.openRunConfigurations());
		this.getItems().add(RunMenu.run_configurations);
		top_menu.getMenus().add(this);
	}

	private static void openRunConfigurations()
	{
		Stage settings_stage = new Stage();

		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(main_comp, 420, 280);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());

		GridPane grid_pane = new GridPane();
		ColumnConstraints column = new ColumnConstraints();
	    column.setPercentWidth(50);
	    column.setHalignment(HPos.CENTER);
	    grid_pane.getColumnConstraints().addAll(column, column);
	    RowConstraints row = new RowConstraints();
	    row.setPercentHeight(20);
	    row.setValignment(VPos.CENTER);
	    grid_pane.getRowConstraints().addAll(row, row, row, row, row);
		
		Label inference_rules_label = new Label("Inference Rules");
		grid_pane.add(inference_rules_label, 0, 0);

		RadioButton[] inference_rules = new RadioButton[3];
		inference_rules[0] = new RadioButton("Differential (Kosko's)");
		inference_rules[1] = new RadioButton("Active (Stylios's or Update)");
		inference_rules[2] = new RadioButton("Non Linear");
		inference_rules[0].setOnAction(event -> Map.runner = new DifferentialRunner(0.04));
		inference_rules[1].setOnAction(event -> Map.runner = new ActiveRunner(0.1, 0.05));
		inference_rules[2].setOnAction(event -> Map.runner = new NonLinearRunner(0.04, 0.98));
		ToggleGroup inference_rules_group = new ToggleGroup();
		for (int i = 0; i < inference_rules.length; i++)
		{
			inference_rules[i].setToggleGroup(inference_rules_group);
			grid_pane.add(inference_rules[i], 0, i + 1);
		}

		Label transfer_functions_label = new Label("Transfer Functions");
		grid_pane.add(transfer_functions_label, 1, 0);

		RadioButton[] transfer_functions = new RadioButton[4];
		transfer_functions[0] = new RadioButton("Bivalent");
		transfer_functions[1] = new RadioButton("Trivalent");
		transfer_functions[2] = new RadioButton("Sigmoid");
		transfer_functions[3] = new RadioButton("Continuous");
		transfer_functions[0].setOnAction(event -> Map.runner.setTrasferFunction(new BivalentTransferFunction()));
		transfer_functions[1].setOnAction(event -> Map.runner.setTrasferFunction(new TrivalentTransferFunction()));
		transfer_functions[2].setOnAction(event -> Map.runner.setTrasferFunction(new SigmoidTransferFunction()));
		transfer_functions[3].setOnAction(event -> Map.runner.setTrasferFunction(new ContinuousTransferFunction()));
		ToggleGroup transfer_functions_group = new ToggleGroup();
		for (int i = 0; i < transfer_functions.length; i++)
		{
			transfer_functions[i].setToggleGroup(transfer_functions_group);
			grid_pane.add(transfer_functions[i], 1, i + 1);
		}
		main_comp.getChildren().add(grid_pane);
		
		Button run_button = new Button("Run");
		run_button.setOnAction(event -> Map.runner.start());
		main_comp.getChildren().add(run_button);

		settings_stage.setScene(scene);
		settings_stage.setTitle("Unit Settings");
		settings_stage.setResizable(false);
		settings_stage.show();
	}
}
