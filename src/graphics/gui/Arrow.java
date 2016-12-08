package graphics.gui;

import graphics.Screen;
import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import program.Program;

public class Arrow extends Polygon
{
	public double			rotate;
	public float			t;
	CubicCurve				curve;
	Rotate					rz;
	public static final int	Y_OFFSET	= 50;

	public Arrow(CubicCurve curve, float t)
	{
		super();
		this.setSmooth(true);
		this.curve = curve;
		this.t = t;
		init();
		Program.layout.getChildren().add(this);
	}

	public Arrow(CubicCurve curve, float t, double... polygon)
	{
		super(polygon);
		this.setSmooth(true);
		this.curve = curve;
		this.t = t;
		this.init();
		Program.layout.getChildren().add(this);
	}

	private void init()
	{
		this.setFill(Screen.HEX2ARGB(0x8F8E86));
		rz = new Rotate();
		{	rz.setAxis(Rotate.Z_AXIS);	}
		this.getTransforms().addAll(rz);
		this.tick();
	}

	public void tick()
	{
		double size = Math.max(curve.getBoundsInLocal().getWidth(), curve.getBoundsInLocal().getHeight());
		double scale = size / 4d;

		Point2D ori = eval(curve, t);
		Point2D tan = evalDt(curve, t).normalize().multiply(scale);

		setTranslateX(ori.getX());
		setTranslateY(ori.getY());

		double angle = Math.atan2(tan.getY(), tan.getX());

		angle = Math.toDegrees(angle);

		double offset = 90;

		rz.setAngle(angle + offset);
	}

	private Point2D eval(CubicCurve c, float t)
	{
		double x = Math.pow(1 - t, 3) * c.getStartX() +
                   3 * t * Math.pow(1 - t, 2) * c.getControlX1() +
                   3 * (1 - t) * t * t * c.getControlX2() +
                   Math.pow(t, 3) * c.getEndX();
		
		double y = Math.pow(1 - t, 3) * c.getStartY() +
				   3 * t * Math.pow(1 - t, 2) * c.getControlY1() +
                   3 * (1 - t) * t * t * c.getControlY2() +
                   Math.pow(t, 3) * c.getEndY();
		return new Point2D(x, y);
	}

	private Point2D evalDt(CubicCurve c, float t)
	{
		double x = -3 * Math.pow(1 - t, 2) * c.getStartX() +
                3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlX1()+
                3 * ((1 - t) * 2 * t - t * t) * c.getControlX2() +
                3 * Math.pow(t, 2) * c.getEndX();
		
		double y = -3 * Math.pow(1 - t, 2) * c.getStartY() +
                   3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlY1()+
                   3 * ((1 - t) * 2 * t - t * t) * c.getControlY2() +
                   3 * Math.pow(t, 2) * c.getEndY();
		
		return new Point2D(x, y);
	}
}
