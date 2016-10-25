package graphics.gui;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import program.map.Connection;

public class Arrow extends Polygon
{
	public double	rotate;
	public float	t;
	Line			line;
	Rotate			rz;

	public Arrow(Line line, float t)
	{
		super();
		this.setSmooth(true);
		this.line = line;
		this.t = t;
		init();
	}

	public Arrow(Line line, float t, double... arg0)
	{
		super(arg0);
		this.setSmooth(true);
		this.line = line;
		this.t = t;
		init();
	}

	private void init()
	{
		setFill(Connection.color);

		rz = new Rotate();
		{
			rz.setAxis(Rotate.Z_AXIS);
		}
		getTransforms().addAll(rz);

		update();
	}

	public void update()
	{
		double size = Math.max(line.getBoundsInLocal().getWidth(), line.getBoundsInLocal().getHeight());
		double scale = size / 4d;

		Point2D ori = eval(line, t);
		Point2D tan = evalDt(line, t).normalize().multiply(scale);

		setTranslateX(ori.getX());
		setTranslateY(ori.getY());

		double angle = Math.atan2(tan.getY(), tan.getX());

		angle = Math.toDegrees(angle);

		// arrow origin is top => apply offset
		double offset = -90;
		if (t > 0.5) offset = +90;

		rz.setAngle(angle + offset);

	}

	private Point2D eval(Line c, float t)
	{
		Point2D p = new Point2D(Math.pow(1 - t, 3) * c.getStartX() + 3 * t * Math.pow(1 - t, 2) + 3 * (1 - t) * t * t + Math.pow(t, 3) * c.getEndX(), Math.pow(1 - t, 3) * c.getStartY() + 3 * t * Math.pow(1 - t, 2) + 3 * (1 - t) * t * t + Math.pow(t, 3) * c.getEndY());
		return p;
	}

	private Point2D evalDt(Line c, float t)
	{
		Point2D p = new Point2D(-3 * Math.pow(1 - t, 2) * c.getStartX() + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) + 3 * ((1 - t) * 2 * t - t * t) + 3 * Math.pow(t, 2) * c.getEndX(), -3 * Math.pow(1 - t, 2) * c.getStartY() + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) + 3 * ((1 - t) * 2 * t - t * t) + 3 * Math.pow(t, 2) * c.getEndY());
		return p;
	}
}
