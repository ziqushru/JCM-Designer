package graphics.menu;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import program.Program;
import program.map.Map;
import program.units.Unit;

public class LeftMenu extends ToolBar
{
	private static final Button button_1 = new Button();

	public LeftMenu()
	{
		super();

		this.setOrientation(Orientation.VERTICAL);

		LeftMenu.button_1.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("factor.png"))));
		LeftMenu.button_1.setTooltip(new Tooltip("Creates a factor with a fixed name that can be renamed later (by clicking the right button over the factor and next changing the field name)"));

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
				Unit unit = new Unit(Map.units.size() + 1 + "", Screen.X_OFFSET, Screen.Y_OFFSET, "factor");
				Map.cognitive_map.addConcept(unit.concept);
				Map.units.add(unit);
				Screen.unit = null;
				Map.last_selected_unit = null;
			}
		});

		this.getItems().add(LeftMenu.button_1);

		Program.layout.setLeft(this);
	}
}
