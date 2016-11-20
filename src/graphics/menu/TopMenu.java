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

public class TopMenu extends MenuBar
{
	private static final Menu		file_menu							= new Menu("File");
	private static final MenuItem	new_file							= new MenuItem("New");
	private static final MenuItem	open_file							= new MenuItem("Open");
	private static final MenuItem	save_file							= new MenuItem("Save");
	private static final MenuItem	exit_file							= new MenuItem("Exit");

	private static final Menu		run_menu							= new Menu("Run");
	private static final MenuItem	existing_activators_run				= new MenuItem("Existing Activators");
	private static final MenuItem	signum_activator_run				= new MenuItem("Signum Activator");
	private static final MenuItem	linear_activator_run				= new MenuItem("Linear Activator");
	private static final MenuItem	sigmoid_activator_run				= new MenuItem("Sigmoid Activator");
	private static final MenuItem	hyperbolic_tangent_activator_run	= new MenuItem("Hyperbolic Tangent Activator");
	private static final MenuItem	gaussian_activator_run				= new MenuItem("Gaussian Activator");
	private static final MenuItem	cauchy_activator_run				= new MenuItem("Cauchy Activator");
	private static final MenuItem	interval_activator_run				= new MenuItem("Interval Activator");
	private static final MenuItem	nary_activator_run					= new MenuItem("Nary Activator");

	private static final Menu		help_menu							= new Menu("Help");
	private static final MenuItem	about_help							= new MenuItem("About");

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

		TopMenu.existing_activators_run.setOnAction(event -> Map.cognitive_map.execute());
		TopMenu.signum_activator_run.setOnAction(event ->
		{
			Map.setActivators(new SignumActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.linear_activator_run.setOnAction(event ->
		{
			Map.setActivators(new LinearActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.sigmoid_activator_run.setOnAction(event ->
		{
			Map.setActivators(new SigmoidActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.hyperbolic_tangent_activator_run.setOnAction(event ->
		{
			Map.setActivators(new HyperbolicTangentActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.gaussian_activator_run.setOnAction(event ->
		{
			Map.setActivators(new GaussianActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.cauchy_activator_run.setOnAction(event ->
		{
			Map.setActivators(new CauchyActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.interval_activator_run.setOnAction(event ->
		{
			Map.setActivators(new IntervalActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.nary_activator_run.setOnAction(event ->
		{
			Map.setActivators(new NaryActivator());
			Map.cognitive_map.execute();
		});
		TopMenu.run_menu.getItems().add(TopMenu.existing_activators_run);
		TopMenu.run_menu.getItems().add(TopMenu.signum_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.linear_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.sigmoid_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.hyperbolic_tangent_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.gaussian_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.cauchy_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.interval_activator_run);
		TopMenu.run_menu.getItems().add(TopMenu.nary_activator_run);
		this.getMenus().add(TopMenu.run_menu);

		TopMenu.help_menu.getItems().add(about_help);
		this.getMenus().add(help_menu);

		Program.layout.setTop(this);
	}
}
