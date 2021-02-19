package jcmdesigner.program.units.entities;

import java.awt.MouseInfo;
import java.awt.Point;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jcmdesigner.program.App;
import jcmdesigner.program.utils.Position;

public abstract class Entity extends ImageView implements EventHandler<MouseEvent>
{
	public int size;
	public Position position;
	
	public Entity(int x, int y, String path)
	{
		super();
		this.position = new Position(x, y);
		Image image = new Image(this.getClass().getResourceAsStream(path));
		this.setImage(image);
		this.size = (int) image.getWidth();
		this.setX(x);
		this.setY(y);
		this.setSmooth(true);
		this.setOnMouseDragged(this);
		this.setOnMouseClicked(this);
		App.main_border_pane.getChildren().add(this);
	}
	
	protected void updateX(double x)
	{
		this.position.x = (int) x;
		super.setX(this.position.x);
	}
	
	protected void updateY(double y)
	{
		this.position.y = (int) y;
		super.setY(this.position.y);
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				Point point = MouseInfo.getPointerInfo().getLocation();
				Point2D point2D = App.main_border_pane.screenToLocal(point.x, point.y);
				this.updateX(point2D.getX() - this.size / 2);
				this.updateY(point2D.getY() - this.size / 2);
			}
		}
	}
}
