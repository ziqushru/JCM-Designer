package graphics.gui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CustomGridPane extends GridPane
{
	public CustomGridPane(int columns, int rows)
	{
		super();
		ColumnConstraints column = new ColumnConstraints();
		column.setHalignment(HPos.CENTER);
		column.setPercentWidth(100 / columns);
		RowConstraints row = new RowConstraints();
		row.setValignment(VPos.CENTER);
		row.setPercentHeight(100 / rows);

		for (int i = 0; i < columns; i++)
			this.getColumnConstraints().add(column);

		for (int i = 0; i < rows; i++)
			this.getRowConstraints().add(row);
	}
}
