package program.units.entities;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import program.Program;
import program.inputs.Mouse;
import program.utils.Position;

public abstract class Entity extends ImageView implements EventHandler<MouseEvent>
{
	public int size;
	public Position position;
	
	public Entity(int x, int y, String path)
	{
		super();
		this.position = new Position(x, y);
		Image image = new Image(this.getClass().getResourceAsStream("/" + path + ".png"));
		this.setImage(image);
		this.size = (int) image.getWidth();
		this.setX(this.position.x);
		this.setY(this.position.y);
		this.setSmooth(true);
		this.setOnMouseDragged(this);
		this.setOnMouseClicked(this);
		Program.layout.getChildren().add(this);
	}
	
	protected void setX(int x)
	{
		this.position.x = x;
		super.setX(this.position.x);
	}
	
	protected void setY(int y)
	{
		this.position.y = y;
		super.setY(this.position.y);
	}
	
	protected void normalizePosition()
	{
		if (this.position.x < 0) this.position.x = 0;
		if (this.position.y < 0) this.position.y = 0;
		if (this.position.x > Screen.WIDTH) this.position.x = Screen.WIDTH;
		if (this.position.y > Screen.HEIGHT) this.position.y = Screen.HEIGHT;
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				this.setX(Mouse.position.x - this.size / 2);
				this.setY(Mouse.position.y - this.size / 2);
			}
		}
	}
}
