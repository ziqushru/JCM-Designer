package program;

import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.JFrame;

import graphics.gui.GraphScreen;
import graphics.menu.LeftMenu;
import graphics.menu.TopMenu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import program.map.Map;
import program.units.Unit;
import program.utils.Log;

public class Program extends Application
{
	public static final String			TITLE									= "JFCM";
	public static final int				WIDTH									= 1024;
	public static final int				HEIGHT									= 720;

	public static Log					log;

	public static Stage					window;
	public static BorderPane			main_border_pane;
	public static final String			logo_path								= "logo";
	public static HostServices 			host_services;

	public static boolean				running									= false;
	public static boolean				paused									= false;
	
	@Override
	public void start(Stage window) throws Exception
	{
		Program.log = new Log();
		Program.main_border_pane = new BorderPane();
		Program.main_border_pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
		Program.host_services = this.getHostServices();
		new TopMenu();
		new LeftMenu();
		Program.window = window;
		Program.setUserAgentStylesheet(Program.STYLESHEET_CASPIAN);
		Program.window.setScene(new Scene(Program.main_border_pane, Program.WIDTH, Program.HEIGHT));
		Program.window.getScene().getStylesheets().add(getClass().getResource("/stylesheets/application.css").toExternalForm());
		Program.window.getIcons().add(new Image(Program.logo_path + ".png"));
		Program.window.setTitle(Program.TITLE);
		Program.main_border_pane.setOnMouseClicked(event ->
		{
			if (Map.last_selected_unit != null)
				for (int i = 0; i < Unit.selected_lines.length; i++)
					Program.main_border_pane.getChildren().remove(Unit.selected_lines[i]);
			Map.last_selected_unit = null;
		});
		Program.window.setOnCloseRequest(event ->
		{
			event.consume();
			Program.closeProgram();
		});
		Program.window.show();
		Program.running = true;
	}

	public static void closeProgram()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(Program.window);
		alert.setTitle("Exit Confirmation");
		alert.setHeaderText("Your program is about to exit, be sure to save your map");
		alert.setContentText("Are you sure you want to exit ?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
		{
			Program.running = false;
			for (JFrame frame : GraphScreen.frames)
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			Program.window.close();
			Platform.exit();
		}
	}

	public static void main(String... args)
	{
		launch(args);
	}
}
