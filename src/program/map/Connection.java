package program.map;

import graphics.Screen;
import graphics.gui.Arrow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import program.Program;
import program.units.Unit;

public class Connection
{
	private final double		weight;
	private final Unit			start_unit;
	private final Unit			end_unit;
	private final Line			line;
	private final Arrow			arrow;
	public static final Color	color	= Color.web("#AAAA55");

	public Connection(int weight, Unit start_unit, Unit end_unit)
	{
		this.weight = weight;
		this.start_unit = start_unit;
		this.end_unit = end_unit;
		this.line = new Line(start_unit.position.x + Screen.X_OFFSET + start_unit.size / 2, start_unit.position.y + Screen.Y_OFFSET + start_unit.size / 2, end_unit.position.x + Screen.X_OFFSET + start_unit.size / 2, end_unit.position.y + Screen.Y_OFFSET + start_unit.size / 2);
		this.line.setSmooth(true);
		this.line.setStroke(Connection.color);
		this.line.setStrokeWidth(2);
		this.line.setStrokeLineCap(StrokeLineCap.ROUND);
		this.arrow = new Arrow(line, 0.5f, new double[] { 0, 0, 5, 10, -5, 10 });
	}

	private void tickPosition()
	{
		this.line.setStartX(start_unit.position.x + Screen.X_OFFSET + start_unit.size / 2);
		this.line.setStartY(start_unit.position.y + Screen.Y_OFFSET + start_unit.size / 2);
		this.line.setEndX(end_unit.position.x + Screen.X_OFFSET + start_unit.size / 2);
		this.line.setEndY(end_unit.position.y + Screen.Y_OFFSET + start_unit.size / 2);
		this.arrow.update();
	}

	private void drawWeight()
	{
		int x_middle = (start_unit.position.x + end_unit.position.x) / 2;
		int y_middle = (start_unit.position.y + end_unit.position.y) / 2 - 5;
		Screen.graphics_context.fillText(weight + "", x_middle, y_middle);
	}

	public void drawLine()
	{
		Program.layout.getChildren().add(line);
		Program.layout.getChildren().add(arrow);
	}

	public void render()
	{
		tickPosition();
		drawWeight();
	}
}
