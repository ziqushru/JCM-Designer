package graphics.menu;

import graphics.Screen;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import program.Program;
import program.map.Map;
import program.units.Unit;

public class LeftMenu extends ToolBar
{
	private static final Button button_1 = new Button();

	public LeftMenu()
	{
		super();
		this.setBackground(Background.EMPTY);
		this.setStyle("-fx-background-color: #403C3C");
		this.setOrientation(Orientation.VERTICAL);
		LeftMenu.button_1.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("concept.png"))));
		LeftMenu.button_1.setTooltip(new Tooltip("Creates a concept. Right click the new concept to open Settings"));
		
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Unit unit = new Unit(Map.units.size() + 1 + "", Screen.WIDTH / 2, Screen.HEIGHT - 100, "concept");
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
