package graphics.menu;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import program.Program;
import program.units.Unit;

public class LeftMenu extends ToolBar
{
	private static final Button	button_1	= new Button();
	private static final Button	button_2	= new Button();
	private static final Button	button_3	= new Button();

	public LeftMenu()
	{
		super();

		this.setOrientation(Orientation.VERTICAL);

		LeftMenu.button_1.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("2.png"))));
		LeftMenu.button_2.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("2.png"))));
		LeftMenu.button_3.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("2.png"))));

		DropShadow shadow = new DropShadow(10, Color.RED);
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_1.setEffect(shadow);
			}
		});
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_1.setEffect(null);
			}
		});
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Screen.unit = new Unit("1", 0, 0, "2");
			}
		});

		LeftMenu.button_2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_2.setEffect(shadow);
			}
		});
		LeftMenu.button_2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_2.setEffect(null);
			}
		});
		LeftMenu.button_2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Screen.unit = new Unit("2", 0, 0, "2");
			}
		});
		
		LeftMenu.button_3.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_3.setEffect(shadow);
			}
		});
		LeftMenu.button_3.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_3.setEffect(null);
			}
		});
		LeftMenu.button_3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Screen.unit = new Unit("3", 0, 0, "2");
			}
		});

		this.getItems().add(LeftMenu.button_1);
		this.getItems().add(LeftMenu.button_2);
		this.getItems().add(LeftMenu.button_3);

		Program.layout.setLeft(this);
	}
}
