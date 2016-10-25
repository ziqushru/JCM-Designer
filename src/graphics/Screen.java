package graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import program.Program;
import program.inputs.Keyboard;
import program.inputs.Mouse;
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
	
	public static final int 		X_OFFSET = 58;
	public static final int 		Y_OFFSET = 24;
	
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
		Program.layout.setCenter(this);	        

		this.setOnKeyPressed(event -> Keyboard.pressed(event));
		this.setOnKeyReleased(event -> Keyboard.released(event));
		this.setOnKeyTyped(event -> Keyboard.typed(event));
		
		this.setOnMouseMoved(event -> Mouse.moved(event));
		this.setOnMousePressed(event -> Mouse.pressed(event));
		this.setOnMouseReleased(event -> Mouse.released(event));
		this.setOnMouseClicked(event -> Mouse.clicked(event));
		this.setOnMouseDragged(event -> Mouse.dragged(event));
		this.setOnMouseDragReleased(event -> Mouse.dragReleased());

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

		Map.tickUnitNames();
		Map.drawConnections();
	}

	@Override
	public void run()
	{
		Screen.render();
	}
}
