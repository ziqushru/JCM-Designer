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
		this.init();
	}

	private void init()
	{
		this.setFill(Connection.color);
		rz = new Rotate();
		{
			rz.setAxis(Rotate.Z_AXIS);
		}
		this.getTransforms().addAll(rz);
		this.update();
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

		double offset = -90;
		if (t > 0.5) offset = +90;

		rz.setAngle(angle + offset);
	}

	private Point2D eval(Line c, float t)
	{
//		double x = Math.pow(1 - t, 2) * c.getStartX() + 2 * t * Math.pow(1 - t, 2) * c.getEndX();
//		double y = Math.pow(1 - t, 2) * c.getStartY() + 2 * t * Math.pow(1 - t, 2) * c.getEndY();
		double x = (1 - t) * c.getStartX() + t * c.getEndX();
		double y = (1 - t) * c.getStartY() + t * c.getEndY();
		
		Point2D p = new Point2D(x, y);
		return p;
	}

	private Point2D evalDt(Line c, float t)
	{
//		double x = -2 * Math.pow(1 - t, 2) * c.getStartX() + 2 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getEndX();
//		double y = -2 * Math.pow(1 - t, 2) * c.getStartY() + 2 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getEndY();
		double x = -c.getStartX() + c.getEndX();
		double y = -c.getStartY() + c.getEndY();
		
		Point2D p = new Point2D(x, y);
		return p;
	}
}
