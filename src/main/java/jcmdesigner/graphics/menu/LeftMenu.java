package jcmdesigner.graphics.menu;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import jcmdesigner.program.App;
import jcmdesigner.program.map.Map;
import jcmdesigner.program.map.runnners.Parameters;
import jcmdesigner.program.units.Unit;

public class LeftMenu extends ToolBar
{
	private static final Button button_1 = new Button();

	public LeftMenu()
	{
		super();
		this.setBackground(Background.EMPTY);
		this.setOrientation(Orientation.VERTICAL);
		LeftMenu.button_1.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream(Unit.concepts_path + "blue_concept.png"))));
		LeftMenu.button_1.setTooltip(new Tooltip("Creates a new concept in the middle of the screen"));
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_1.setTranslateY(1);
			}
		});
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				LeftMenu.button_1.setTranslateY(-1);
			}
		});
		LeftMenu.button_1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Unit unit = new Unit("Concept " + (Map.units.size() + 1), App.WIDTH / 2 - 32, App.HEIGHT / 2 - 32, Unit.concepts_path + "blue_concept.png");
				Map.cognitive_map.addConcept(unit.concept);
				Map.units.add(unit);
				if (Map.last_selected_unit != null)
				{
					for (int i = 0; i < Unit.selected_lines.length; i++)
						App.main_border_pane.getChildren().remove(Unit.selected_lines[i]);
					Map.last_selected_unit.setEffect(null);
				}
				Map.last_selected_unit = null;
				Parameters.A_desired = new double[2][Map.units.size()];
				for (int i = 0; i < Map.units.size(); i++)
				{
					Parameters.A_desired[0][i] = Parameters.A_desired_null;
					Parameters.A_desired[1][i] = Parameters.A_desired_null;
				}
			}
		});
		this.getItems().add(LeftMenu.button_1);
		App.main_border_pane.setLeft(this);
	}
}
