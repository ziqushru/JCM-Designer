package program.map;

import java.util.ArrayList;
import java.util.List;

import org.megadix.jfcm.conn.WeightedConnection;

import graphics.gui.Arrow;
import graphics.gui.BezierCurve;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import program.Program;
import program.units.Unit;
import program.utils.Position;

public class Relation extends WeightedConnection
{
	public Text					weight_text;
	private Unit				start_unit;
	private Unit				end_unit;
	public final BezierCurve	curve;
	public final BezierCurve	interaction_curve;
	public final List<Arrow>	arrows;

	public Relation(double weight, Unit start_unit, Unit end_unit)
	{
		super(start_unit.concept.getName() + " -> " + end_unit.concept.getName(), "test_desc", weight);
		this.setFrom(start_unit.concept);
		this.setTo(end_unit.concept);
		this.start_unit = start_unit;
		this.end_unit = end_unit;
		Position middle_position = Relation.getMiddlePoint(start_unit.position, start_unit.size, end_unit.position, end_unit.size);
		this.weight_text = new Text(weight + "");
		this.weight_text.setStyle("-fx-font: 16px Alcubierre;");
		double cos = Relation.getCos(start_unit.position.x, end_unit.position.x, start_unit.position.y, end_unit.position.y);
		double sin = Relation.getSin(start_unit.position.x, end_unit.position.x, start_unit.position.y, end_unit.position.y);
		this.weight_text.setX(middle_position.x + cos * 15);
		this.weight_text.setY(middle_position.y + sin * 15);
		this.weight_text.setSmooth(true);
		this.weight_text.setFontSmoothingType(FontSmoothingType.LCD);
		this.weight_text.setTranslateZ(0);
		Program.main_border_pane.getChildren().add(weight_text);
		this.curve = new BezierCurve(this, start_unit.position.x + start_unit.size / 2, start_unit.position.y + start_unit.size / 2, middle_position.x, middle_position.y, middle_position.x, middle_position.y, end_unit.position.x + start_unit.size / 2, end_unit.position.y + start_unit.size / 2, false);
		this.arrows = new ArrayList<Arrow>();
		this.arrows.add(new Arrow(this.curve, 0.2f, new double[] { 0, 0, 5, 10, -5, 10 }));
		this.arrows.add(new Arrow(this.curve, 0.8f, new double[] { 0, 0, 5, 10, -5, 10 }));
		for (Arrow arrow : this.arrows)
			arrow.setTranslateZ(0);
		this.interaction_curve = new BezierCurve(this, start_unit.position.x + start_unit.size / 2, start_unit.position.y + start_unit.size / 2, middle_position.x, middle_position.y, middle_position.x, middle_position.y, end_unit.position.x + start_unit.size / 2, end_unit.position.y + start_unit.size / 2, true);
	}

	public void remove()
	{
		Map.cognitive_map.removeConnection(Relation.this.getName());
		this.start_unit.relations.remove(this);
		Program.main_border_pane.getChildren().remove(this.curve);
		Program.main_border_pane.getChildren().remove(this.interaction_curve);
		Program.main_border_pane.getChildren().remove(this.weight_text);
		for (Arrow arrow : this.arrows)
			Program.main_border_pane.getChildren().remove(arrow);
	}

	public void tick()
	{
		this.curve.setStartX(start_unit.position.x + start_unit.size / 2);
		this.interaction_curve.setStartX(start_unit.position.x + start_unit.size / 2);
		this.curve.setStartY(start_unit.position.y + start_unit.size / 2);
		this.interaction_curve.setStartY(start_unit.position.y + start_unit.size / 2);
		Position middle_position = Relation.getMiddlePoint(start_unit.position, start_unit.size, end_unit.position, end_unit.size);
		this.curve.setControlX1(middle_position.x);
		this.interaction_curve.setControlX1(middle_position.x);
		this.curve.setControlY1(middle_position.y);
		this.interaction_curve.setControlY1(middle_position.y);
		this.curve.setControlX2(middle_position.x);
		this.interaction_curve.setControlX2(middle_position.x);
		this.curve.setControlY2(middle_position.y);
		this.interaction_curve.setControlY2(middle_position.y);
		this.curve.setEndX(end_unit.position.x + start_unit.size / 2);
		this.interaction_curve.setEndX(end_unit.position.x + start_unit.size / 2);
		this.curve.setEndY(end_unit.position.y + start_unit.size / 2);
		this.interaction_curve.setEndY(end_unit.position.y + start_unit.size / 2);
		double cos = Relation.getCos(start_unit.position.x, end_unit.position.x, start_unit.position.y, end_unit.position.y);
		double sin = Relation.getSin(start_unit.position.x, end_unit.position.x, start_unit.position.y, end_unit.position.y);
		this.weight_text.setX(middle_position.x + cos * 15);
		this.weight_text.setY(middle_position.y + sin * 15);
		for (Arrow arrow : this.arrows)
			arrow.tick();
	}

	public Unit getStartUnit() { return this.start_unit; }

	public Unit getEndUnit() { return this.end_unit; }

	public static Position getMiddlePoint(Position start_position, Position end_position)
	{		
		return Relation.getMiddlePoint(start_position, 0, end_position, 0);
	}

	private static double getSin(int x_start, int x_end, int y_start, int y_end)
	{
		double theta = Math.atan2(y_end - y_start, x_end - x_start);
	    theta += Math.PI/2.0;
	    double angle = Math.toDegrees(theta);
	    if (angle < 0) {
	        angle += 360;
	    }
		return Math.sin(theta);
	}
	
	private static double getCos(int x_start, int x_end, int y_start, int y_end)
	{		
		double theta = Math.atan2(y_end - y_start, x_end - x_start);
	    theta += Math.PI/2.0;
	    double angle = Math.toDegrees(theta);
	    if (angle < 0) {
	        angle += 360;
	    }
		return Math.cos(theta);
	}
	
	public static Position getMiddlePoint(Position start_position, int start_size, Position end_position, int end_size)
	{
		int x_start = start_position.x + start_size / 2;
		int y_start = start_position.y + start_size / 2;
		int x_end = end_position.x + end_size / 2;
		int y_end = end_position.y + end_size / 2;
		double cx = (x_start + x_end) / 2;
		double cy = (y_start + y_end) / 2;
		int horizontal = x_end - x_start;
		int vertical = y_start - y_end;
		double vertical_sin_angle = Math.sin(vertical / Math.sqrt(vertical * vertical + horizontal * horizontal));
		double horizontal_sin_angle = Math.sin(horizontal / Math.sqrt(vertical * vertical + horizontal * horizontal));
		cx -= Arrow.OFFSET * vertical_sin_angle;
		cy -= Arrow.OFFSET * horizontal_sin_angle;
		return new Position(cx, cy);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Relation)) return false;
		Relation other = (Relation) obj;
		if (!end_unit.equals(other.end_unit)) return false;
		if (start_unit == null)
			if (other.start_unit != null) return false;
		else if (!start_unit.equals(other.start_unit)) return false;
		return true;
	}	
}
