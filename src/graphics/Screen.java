package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import program.Program;
import program.map.Map;
import program.units.Unit;

public class Screen extends Canvas implements Runnable
{
	private static BufferedImage	buffered_image;
	private static Image			image;
	public static int				WIDTH;
	public static int				HEIGHT;
	public static int				pixels[];
	public static GraphicsContext	graphics_context;

	private static int				color;

	public static final int			X_OFFSET	= 58;
	public static final int			Y_OFFSET	= 24;

	public static Unit				unit;

	public Screen(int width, int height)
	{
		super(width, height);
		Screen.WIDTH = width - X_OFFSET;
		Screen.HEIGHT = height - Y_OFFSET;
		Screen.pixels = new int[Screen.WIDTH * Screen.HEIGHT];
		Screen.buffered_image = new BufferedImage(Screen.WIDTH, Screen.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Screen.pixels = ((DataBufferInt) Screen.buffered_image.getRaster().getDataBuffer()).getData();
		Screen.graphics_context = this.getGraphicsContext2D();
		Screen.graphics_context.setFill(Color.WHITE);
		Screen.graphics_context.setFontSmoothingType(FontSmoothingType.LCD);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (Map.last_selected_unit != null)
				{
					for (Unit unit : Map.units)
					{
						if (event.getX() >= unit.position.x && event.getX() < unit.position.x + unit.size && event.getY() >= unit.position.y && event.getY() < unit.position.y + unit.size)
							return;
					}
					Map.last_selected_unit = null;
				}
			}
		});

		Program.layout.setCenter(this);

		this.requestFocus();
		this.setFocusTraversable(true);

		Screen.color = 0xFF222222;
	}

	public static void clear()
	{
		for (int y = 0; y < Screen.HEIGHT; y++)
			for (int x = 0; x < Screen.WIDTH; x++)
				Screen.pixels[x + y * Screen.WIDTH] = Screen.color;
	}

	public static void tick()
	{
		Map.tick();

		Screen.image = SwingFXUtils.toFXImage(Screen.buffered_image, (WritableImage) Screen.image);
	}

	public static void render()
	{
		Screen.graphics_context.drawImage(Screen.image, 0, 0);
	}

	@Override
	public void run()
	{
		Screen.render();
	}
}
