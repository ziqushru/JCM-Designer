package program.units.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Screen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import program.Program;
import program.utils.Log;
import program.utils.Position;

public abstract class Entity extends ImageView
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
}
