package program.inputs;

import java.awt.MouseInfo;
import java.awt.Point;

import graphics.Screen;
import javafx.geometry.Point2D;
import program.Program;
import program.utils.Position;

public final class Mouse
{
	public static Position position = new Position();

	public static void tick()
	{
		Point point = MouseInfo.getPointerInfo().getLocation();
		Point2D point2D = Program.screen.screenToLocal(point.x, point.y);
		Mouse.position.x = (int) point2D.getX() + Screen.X_OFFSET;
		Mouse.position.y = (int) point2D.getY() + Screen.Y_OFFSET;
	}
}
