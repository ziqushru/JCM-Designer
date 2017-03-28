package graphics.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import program.map.Map;

public class GraphScreen
{
	private final int				concepts_length;
	private final int				iterations;
	private final List<double[]>	A_overall;
	public static List<JFrame>		frames = new ArrayList<JFrame>();
	
	public GraphScreen(String application_title, String chart_title, List<double[]> A_overall)
	{
		this.A_overall = A_overall;
		this.concepts_length = Map.units.size();
		this.iterations = A_overall.size();
		
		JPanel main_panel = new JPanel(new BorderLayout());
		JFreeChart chart = ChartFactory.createXYLineChart(chart_title, "Iterations", "Concept Values", createDataset(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chart_panel = new ChartPanel(chart);
		final XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for (int i = 0; i < this.concepts_length; i++)
		{
			Color color = Color.BLACK;
			if (i % 6 == 0)			color = Color.RED;
			else if (i % 6 == 1)	color = Color.ORANGE;
			else if (i % 6 == 2)	color = Color.YELLOW;
			else if (i % 6 == 3)	color = Color.GREEN;
			else if (i % 6 == 4)	color = Color.BLUE;
			else if (i % 6 == 5)	color = Color.MAGENTA;
			renderer.setSeriesPaint(i, color);
			renderer.setSeriesStroke(i, new BasicStroke(1.0f));
		}
		plot.setRenderer(renderer);
		main_panel.add(chart_panel, BorderLayout.CENTER);

		JPanel data_panel = new JPanel(new GridLayout(1 + this.iterations, 1 + this.concepts_length));
		data_panel.setBackground(Color.WHITE);
		data_panel.add(new JLabel("  Iterations"));
		for (int x = 0; x < this.concepts_length; x++)
			data_panel.add(new JLabel(Map.units.get(x).getName()));
		
		for (int y = 0; y < iterations; y++)
		{
			data_panel.add(new JLabel("       " + y));
			for (int x = 0; x < concepts_length; x++)
				data_panel.add(new JLabel(A_overall.get(y)[x] + ""));
		}
		main_panel.add(data_panel, BorderLayout.SOUTH);

		JFrame frame = new JFrame(application_title);
		frame.setSize(new Dimension(1024, 500 + 20 * iterations));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(main_panel);
		frame.setVisible(true);
		frames.add(frame);
	}

	private XYDataset createDataset()
	{
		final XYSeriesCollection dataset = new XYSeriesCollection();
		final XYSeries[] units = new XYSeries[concepts_length];

		for (int i = 0; i < concepts_length; i++)
			units[i] = new XYSeries(Map.units.get(i).getName());
		
		for (int y = 0; y < A_overall.size(); y++)
			for (int x = 0; x < A_overall.get(y).length; x++)
				units[x].add(y, A_overall.get(y)[x]);
		
		for (int i = 0; i < concepts_length; i++)
			dataset.addSeries(units[i]);
		
		return dataset;
	}
}
