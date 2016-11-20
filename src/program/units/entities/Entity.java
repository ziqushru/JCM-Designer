package program.units.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import program.Program;
import program.inputs.Mouse;
import program.utils.Log;
import program.utils.Position;

public abstract class Entity extends ImageView implements EventHandler<MouseEvent>
{
	public int size;
	public Position position;
	
	public Entity(int x, int y, String path)
	{
		super();
		this.position = new Position(x, y);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(this.getClass().getResource("/" + path + ".png"));
		}
		catch (IOException e) {	Log.addLog("Error: loading image"); Log.consoleOut(); }
		this.size = image.getWidth();
		this.setImage( new Image(this.getClass().getResourceAsStream("/" + path + ".png")));
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
			this.setX(Mouse.position.x - this.size / 2);
			this.setY(Mouse.position.y - this.size / 2);
		}
	}
}
