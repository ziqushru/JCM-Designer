package program.inputs;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import program.utils.Position;

public final class Mouse implements EventHandler<MouseEvent>
{
	public static boolean		pressed		= false;
	public static Position		position	= new Position();
	public static MouseButton	button		= null;

	public static void clicked(MouseEvent event) {}

	public static void pressed(MouseEvent event)
	{
		Mouse.pressed = true;
		Mouse.position.x = (int) event.getSceneX() - Screen.X_OFFSET;
		Mouse.position.y = (int) event.getSceneY() - Screen.Y_OFFSET;

		button = event.getButton();

		event.consume();
	}

	public static void released(MouseEvent event)
	{
		Mouse.pressed = false;
	}

	public static void moved(MouseEvent event)
	{
		Mouse.position.x = (int) event.getSceneX() - Screen.X_OFFSET;
		Mouse.position.y = (int) event.getSceneY() - Screen.Y_OFFSET;
	}

	public static void dragged(MouseEvent event)
	{
		Mouse.position.x = (int) event.getSceneX() - Screen.X_OFFSET;
		Mouse.position.y = (int) event.getSceneY() - Screen.Y_OFFSET;
	}

	public static void dragReleased()
	{
		
	}
	
	@Override
	public void handle(MouseEvent event) {}

}
