package program;

import java.util.Optional;
import java.util.function.Consumer;

import graphics.Screen;
import graphics.menu.LeftMenu;
import graphics.menu.TopMenu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import program.inputs.Keyboard;
import program.utils.Log;

public class Program extends Application
{
	public static final String			TITLE									= "JFCM";
	public static final int				WIDTH									= 1024;
	public static final int				HEIGHT									= 720;

	public static Log					log;

	public static Stage					window;
	public static BorderPane			layout;
	public static Screen				screen;

	public static boolean				running;
	public static boolean				paused;

	private static Consumer<Float>		updater;
	private static Consumer<Integer>	fps_reporter;

	private static long					previous_time							= 0;
	private static float				seconds_elapsed_since_last_fps_update	= 0f;
	private static int					frames_since_last_fps_update			= 0;
	
	@Override
	public void start(Stage window) throws Exception
	{
		Program.running = false;
		Program.paused = false;

		Program.log = new Log();
		Program.window = window;
		Program.layout = new BorderPane();
		Program.screen = new Screen(Program.WIDTH, Program.HEIGHT);
		new TopMenu();
		new LeftMenu();

		Program.window.setOnCloseRequest(event ->
		{
			event.consume();
			Program.closeProgram();
		});

		Program.window.setTitle(Program.TITLE);
		Program.window.setScene(new Scene(Program.layout, Program.WIDTH, Program.HEIGHT));
		Program.window.setResizable(false);
		
		Program.updater = new Consumer<Float>()
		{
			@Override
			public void accept(Float t)
			{
				Program.tick();
			}
		};

		Program.fps_reporter = new Consumer<Integer>()
		{
			@Override
			public void accept(Integer fps)
			{
				Program.window.setTitle(Program.TITLE + " | fps: " + fps);
			}
		};

		AnimationTimer animation_timer = new AnimationTimer()
		{
			@Override
			public void handle(long current_time)
			{
				if (Program.previous_time == 0)
				{
					Program.previous_time = current_time;
					return;
				}

				float seconds_elapsed = (current_time - Program.previous_time) / 1e9f;
				float seconds_elapsed_capped = Math.min(seconds_elapsed, 60);
				Program.previous_time = current_time;

				Program.updater.accept(seconds_elapsed_capped);
				screen.run();

				Program.seconds_elapsed_since_last_fps_update += seconds_elapsed;
				Program.frames_since_last_fps_update++;
				if (Program.seconds_elapsed_since_last_fps_update >= 0.5f)
				{
					int fps = Math.round(Program.frames_since_last_fps_update / Program.seconds_elapsed_since_last_fps_update);
					Program.fps_reporter.accept(fps);
					Program.seconds_elapsed_since_last_fps_update = 0;
					Program.frames_since_last_fps_update = 0;
				}
			}
		};
		
		Program.window.show();
		Program.running = true;
		animation_timer.start();
	}

	public static void tick()
	{
		if (Keyboard.keys[0] == true) System.exit(0);

		if (!Program.paused)
		{
			Screen.clear();
			Screen.tick();
		}
	}

	public static void closeProgram()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit Confirmation");
		alert.setHeaderText("Your program is about to exit, be sure you have saved your progress");
		alert.setContentText("Are you sure you want to exit ?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
		{
			Program.running = false;
			Program.window.close();
		}
	}

	public static void main(String... args)
	{
		launch(args);
	}
}
