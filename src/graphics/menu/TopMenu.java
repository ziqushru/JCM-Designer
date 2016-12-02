package graphics.menu;

import org.megadix.jfcm.act.CauchyActivator;
import org.megadix.jfcm.act.GaussianActivator;
import org.megadix.jfcm.act.HyperbolicTangentActivator;
import org.megadix.jfcm.act.IntervalActivator;
import org.megadix.jfcm.act.LinearActivator;
import org.megadix.jfcm.act.NaryActivator;
import org.megadix.jfcm.act.SigmoidActivator;
import org.megadix.jfcm.act.SignumActivator;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import program.Program;
import program.map.Map;
import program.map.runnners.ActiveRunner;
import program.map.runnners.DifferentialRunner;
import program.map.runnners.NonLinearRunner;
import program.utils.transferfunctions.SigmoidTrasferFunction;

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
	private static final MenuItem	stylios_menu_item		= new MenuItem("Active Hebbian Learning");

	private static final Menu		trasfer_functions_menu	= new Menu("TransferFunctions");
	private static final MenuItem	signum_t_f				= new MenuItem("Signum");
	private static final MenuItem	linear_t_f				= new MenuItem("Linear");
	private static final MenuItem	sigmoid_t_f				= new MenuItem("Sigmoid");
	private static final MenuItem	hyperbolic_t_f			= new MenuItem("Hyperbolic Tangent");
	private static final MenuItem	gaussian_t_f			= new MenuItem("Gaussian");
	private static final MenuItem	cauchy_t_f				= new MenuItem("Cauchy");
	private static final MenuItem	interval_t_f			= new MenuItem("Interval");
	private static final MenuItem	nary_t_f				= new MenuItem("Nary");
	
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

		TopMenu.differential_menu_item.setOnAction(event ->	Map.runner = new DifferentialRunner(0.05, 15));
		TopMenu.non_linear_menu_item.setOnAction(event -> Map.runner = new NonLinearRunner(0.04, 0.98));
		TopMenu.stylios_menu_item.setOnAction(event -> Map.runner = new ActiveRunner(0.04, 0.98));
		TopMenu.inference_rules.getItems().add(TopMenu.differential_menu_item);
		TopMenu.inference_rules.getItems().add(TopMenu.non_linear_menu_item);
		TopMenu.inference_rules.getItems().add(TopMenu.stylios_menu_item);
		this.getMenus().add(TopMenu.inference_rules);		
		
		TopMenu.signum_t_f.setOnAction(event ->
		{
			Map.setActivators(new SignumActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.linear_t_f.setOnAction(event ->
		{
			Map.setActivators(new LinearActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.sigmoid_t_f.setOnAction(event ->
		{
			Map.setActivators(new SigmoidActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.hyperbolic_t_f.setOnAction(event ->
		{
			Map.setActivators(new HyperbolicTangentActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.gaussian_t_f.setOnAction(event ->
		{
			Map.setActivators(new GaussianActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.cauchy_t_f.setOnAction(event ->
		{
			Map.setActivators(new CauchyActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.interval_t_f.setOnAction(event ->
		{
			Map.setActivators(new IntervalActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.nary_t_f.setOnAction(event ->
		{
			Map.setActivators(new NaryActivator());
			Map.runner.setTrasferFunction(new SigmoidTrasferFunction());
		});
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.signum_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.linear_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.sigmoid_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.hyperbolic_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.gaussian_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.cauchy_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.interval_t_f);
		TopMenu.trasfer_functions_menu.getItems().add(TopMenu.nary_t_f);
		this.getMenus().add(TopMenu.trasfer_functions_menu);
		
		TopMenu.run_menu_item.setOnAction(event ->	Map.runner.start());
		TopMenu.run_menu.getItems().add(run_menu_item);
		this.getMenus().add(run_menu);

		TopMenu.help_menu.getItems().add(about_help);
		this.getMenus().add(help_menu);

		Program.layout.setTop(this);
	}
}
