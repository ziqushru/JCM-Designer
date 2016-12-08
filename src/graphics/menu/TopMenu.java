package graphics.menu;

import org.megadix.jfcm.act.HyperbolicTangentActivator;
import org.megadix.jfcm.act.LinearActivator;
import org.megadix.jfcm.act.SigmoidActivator;
import org.megadix.jfcm.act.SignumActivator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import program.Program;
import program.map.Map;
import program.map.runnners.ActiveRunner;
import program.map.runnners.DifferentialRunner;
import program.map.runnners.NonLinearRunner;
import program.utils.transferfunctions.BivalentTransferFunction;
import program.utils.transferfunctions.ContinuousTransferFunction;
import program.utils.transferfunctions.SigmoidTransferFunction;
import program.utils.transferfunctions.TrivalentTransferFunction;

public class TopMenu extends MenuBar
{
	private static final Menu		file_menu				= new Menu("File");
	private static final MenuItem	new_file				= new MenuItem("New");
	private static final MenuItem	open_file				= new MenuItem("Open");
	private static final MenuItem	save_file				= new MenuItem("Save");
	private static final MenuItem	exit_file				= new MenuItem("Exit");

	private static final Menu		inference_rules			= new Menu("Inference Rules");
	private static final MenuItem	differential_menu_item	= new MenuItem("Differential Hebbian Learning");
	private static final MenuItem	non_linear_menu_item	= new MenuItem("Non Linear Hebbian Learning");
	private static final MenuItem	active_menu_item		= new MenuItem("Active Hebbian Learning");

	private static final Menu		trasfer_functions_menu	= new Menu("TransferFunctions");
	private static final MenuItem	bivalent_t_f			= new MenuItem("Bivalent");
	private static final MenuItem	trivalent_t_f			= new MenuItem("Trivalent");
	private static final MenuItem	sigmoid_t_f				= new MenuItem("Sigmoid");
	private static final MenuItem	continuous_t_f			= new MenuItem("Continuous Values");
	
	private static final Menu		run_menu				= new Menu("Run");
	private static final MenuItem	run_menu_item			= new MenuItem("Run");
	
	private static final Menu		help_menu				= new Menu("Help");
	private static final MenuItem	about_help				= new MenuItem("About");

	public TopMenu()
	{
		super();
		TopMenu.new_file.setOnAction(event -> Map.clear());
		TopMenu.open_file.setOnAction(event -> Map.load());
		TopMenu.save_file.setOnAction(event -> Map.save());
		TopMenu.exit_file.setOnAction(event -> Program.closeProgram());
		TopMenu.file_menu.getItems().add(TopMenu.new_file);
		TopMenu.file_menu.getItems().add(TopMenu.open_file);
		TopMenu.file_menu.getItems().add(TopMenu.save_file);
		TopMenu.file_menu.getItems().add(TopMenu.exit_file);
		this.getMenus().add(TopMenu.file_menu);

		TopMenu.differential_menu_item.setOnAction(event ->	Map.runner = new DifferentialRunner(0.04, 15));
		TopMenu.non_linear_menu_item.setOnAction(event -> Map.runner = new NonLinearRunner(0.04, 0.98));
		TopMenu.active_menu_item.setOnAction(event -> Map.runner = new ActiveRunner(0.1, 0.05));
		TopMenu.inference_rules.getItems().add(TopMenu.differential_menu_item);
		TopMenu.inference_rules.getItems().add(TopMenu.non_linear_menu_item);
		TopMenu.inference_rules.getItems().add(TopMenu.active_menu_item);
		this.getMenus().add(TopMenu.inference_rules);		
		
		TopMenu.bivalent_t_f.setOnAction(event ->
		{
			Map.setActivators(new SignumActivator());
			Map.runner.setTrasferFunction(new BivalentTransferFunction());
		});
		TopMenu.trivalent_t_f.setOnAction(event ->
		{
			Map.setActivators(new LinearActivator());
			Map.runner.setTrasferFunction(new TrivalentTransferFunction());
		});
		TopMenu.sigmoid_t_f.setOnAction(event ->
		{
			Map.setActivators(new SigmoidActivator());
			Map.runner.setTrasferFunction(new SigmoidTransferFunction());
		});
		TopMenu.continuous_t_f.setOnAction(event ->
		{
			Map.setActivators(new HyperbolicTangentActivator());
			Map.runner.setTrasferFunction(new ContinuousTransferFunction());
		});
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.bivalent_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.trivalent_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.sigmoid_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.continuous_t_f);
		this.getMenus().add(TopMenu.trasfer_functions_menu);
		
		TopMenu.run_menu_item.setOnAction(event ->	Map.runner.start());
		TopMenu.run_menu.getItems().add(run_menu_item);
		this.getMenus().add(run_menu);

		TopMenu.about_help.setOnAction(event ->
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Me");
			alert.setHeaderText("This program was developed by Jason Koutoulias");
			alert.setContentText("More info at https://github.com/ziqushru/JFCM-Design-App");
			alert.setResizable(false);
			alert.show();
		});
		TopMenu.help_menu.getItems().add(about_help);
		this.getMenus().add(help_menu);

		Program.layout.setTop(this);
	}
}
