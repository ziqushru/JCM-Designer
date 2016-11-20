package program.units.entities;

public class Mob extends Entity
{
	public static final double speed = 4.0f;
	
	public Mob(int x, int y, String path)
	{
		super(x, y, path);
	}
	
	protected void moveUp()
	{
		this.setY((int) (this.position.y - Mob.speed));
	}
	
	protected void moveLeft()
	{
		this.setX((int) (this.position.x - Mob.speed));
	}
	
	protected void moveDown()
	{
		this.setY((int) (this.position.y + Mob.speed));
	}
	
	protected void moveRight()
	{
		this.setX((int) (this.position.x + Mob.speed));
	}
}
