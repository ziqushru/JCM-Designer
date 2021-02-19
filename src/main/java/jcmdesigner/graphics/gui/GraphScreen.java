package jcmdesigner.graphics.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import jcmdesigner.program.map.Map;
import jcmdesigner.program.map.runnners.Parameters;

public class GraphScreen
{
	private static final int		WIDTH = 1024;
	private final int				concepts_length;
	private final int				iterations;
	public static List<JFrame>		frames = new ArrayList<JFrame>();
	
	@SuppressWarnings("serial")
	private class CustomLabel extends JLabel
	{
		public CustomLabel(String text, int size)
		{
			super(text);
			this.setFont(new Font("Alcubierre", Font.PLAIN, size));
		}
		
		public CustomLabel(double text, int size)
		{
			this(text + "", size);
		}
	}
	
	public GraphScreen(String application_title, String chart_title, List<double[]> A_overall)
	{
		this.concepts_length = Map.units.size();
		this.iterations = A_overall.size();
		
		JPanel main_panel = new JPanel(new BorderLayout());
		JFreeChart chart = ChartFactory.createXYLineChart(chart_title, "Iterations", "Concept Values", createDataset(A_overall), PlotOrientation.VERTICAL, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		ChartPanel chart_panel = new ChartPanel(chart);
		chart_panel.setMinimumSize(new Dimension(GraphScreen.WIDTH, 500));
		final XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for (int i = 0; i < this.concepts_length; i++)
		{
			Color color = Color.BLACK;
			if (i % 6 == 0)			color = Color.BLUE;
			else if (i % 6 == 1)	color = Color.GREEN;
			else if (i % 6 == 2)	color = Color.RED;
			else if (i % 6 == 3)	color = Color.ORANGE;
			else if (i % 6 == 4)	color = Color.YELLOW;
			else if (i % 6 == 5)	color = Color.MAGENTA;
			renderer.setSeriesPaint(i, color);
			renderer.setSeriesStroke(i, new BasicStroke(1.0f));
		}
		plot.setRenderer(renderer);
		main_panel.add(chart_panel, BorderLayout.CENTER);

		JPanel data_panel = new JPanel(new GridLayout(1 + this.iterations, 1 + this.concepts_length));
		data_panel.setBackground(Color.WHITE);
		data_panel.add(new CustomLabel("  Iterations", 16));
		for (int x = 0; x < this.concepts_length; x++)
			data_panel.add(new CustomLabel(Map.units.get(x).getName(), 16));
		
		for (int y = 0; y < iterations; y++)
		{
			data_panel.add(new CustomLabel("       " + y, 14));
			for (int x = 0; x < concepts_length; x++)
				data_panel.add(new CustomLabel(A_overall.get(y)[x], 14));
		}
		JScrollPane scroll_pane = new JScrollPane(data_panel);
		scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll_pane.setPreferredSize(new Dimension(GraphScreen.WIDTH, 150));
		
		JPanel statistics_panel = new JPanel(new GridLayout(1, 2));
		statistics_panel.setBackground(Color.WHITE);
		CustomLabel ge = new CustomLabel("GE = " + Parameters.calculateGE(A_overall), 16);
		ge.setHorizontalAlignment(JLabel.CENTER);
		statistics_panel.add(ge);
		CustomLabel mse = new CustomLabel("MSE = " + Parameters.calculateMSE(A_overall), 16);
		mse.setHorizontalAlignment(JLabel.CENTER);
		statistics_panel.add(mse);
		
		JPanel bottom_panel = new JPanel(new BorderLayout());
		bottom_panel.setBackground(Color.WHITE);
		bottom_panel.add(statistics_panel, BorderLayout.NORTH);
		bottom_panel.add(scroll_pane, BorderLayout.CENTER);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);

		JFrame frame = new JFrame(application_title);
		frame.setSize(new Dimension(GraphScreen.WIDTH, 500 + 15 + 150));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(main_panel);
		frame.pack();
		frame.setVisible(true);
		frames.add(frame);
	}

	private XYDataset createDataset(List<double[]> A_overall)
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
