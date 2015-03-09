package com.example.ti.ble.sensortag;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
//import org.achartengine.chartdemo.demo.R;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
public class ChartBuilder {
	//yavari
	XYSeries rawseries;
	XYSeries filteredseries;
	
	int index = 0;
	private GraphicalView mChartView;
	/** The main dataset that includes all the series that go into a chart. */
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	/** The main renderer that includes all the renderers customizing a chart. */
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

	public ChartBuilder(LinearLayout layout) {
		String[] titles = new String[] { "Raw Input", "Filter result" };
		int[] colors = new int[] { Color.RED, Color.BLUE };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT};
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setDisplayChartValues(true);
		}
		setChartSettings(renderer, "Accelerometer", "Index", "Acceleration",Color.LTGRAY, Color.LTGRAY);
		renderer.setXLabels(12);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

		XYMultipleSeriesDataset dataset = buildDataset(titles);
		rawseries = dataset.getSeriesAt(0);
		filteredseries = dataset.getSeriesAt(1);
		mChartView = ChartFactory.getLineChartView(layout.getContext(), dataset, renderer);
		layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	/**
	 * Builds an XY multiple series renderer.
	 * 
	 * @param colors the series rendering colors
	 * @param styles the series point styles
	 * @return the XY multiple series renderers
	 */
	protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	/**
	 * Builds an XY multiple dataset using the provided values.
	 * 
	 * @param titles the series titles
	 * @param xValues the values for the X axis
	 * @param yValues the values for the Y axis
	 * @return the XY multiple dataset
	 */
	protected XYMultipleSeriesDataset buildDataset(String[] titles) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			dataset.addSeries(series);
		}
	}

	/**
	 * Sets a few of the series renderer settings.
	 * 
	 * @param renderer the renderer to set the properties to
	 * @param title the chart title
	 * @param xTitle the title for the X axis
	 * @param yTitle the title for the Y axis
	 * @param xMin the minimum value on the X axis
	 * @param xMax the maximum value on the X axis
	 * @param yMin the minimum value on the Y axis
	 * @param yMax the maximum value on the Y axis
	 * @param axesColor the axes color
	 * @param labelsColor the labels color
	 */
	protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
			String yTitle/*, double xMin, double xMax, double yMin, double yMax*/, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		//	    renderer.setXAxisMin(xMin);
		//	    renderer.setXAxisMax(xMax);
		//	    renderer.setYAxisMin(yMin);
		//	    renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	//	ChartBuilder(Activity activity,LinearLayout layout) {
	//		mChartView = ChartFactory.getLineChartView(layout.getContext(), mDataset, mRenderer);
	//		layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
	//		          LayoutParams.MATCH_PARENT));
	//		rawseries = new XYSeries("Raw");
	//		filteredseries = new XYSeries("Filtered");
	//		// create a new renderer for the new series
	//        XYSeriesRenderer renderer = new XYSeriesRenderer();
	//        mRenderer.addSeriesRenderer(renderer);
	//        mDataset.addSeries(rawseries);
	//        mDataset.addSeries(filteredseries);
	//        mChartView.setVisibility(GraphicalView.VISIBLE);
	//        mRenderer.setPointSize(6);
	//        filteredseries.add(0, 0);
	//        filteredseries.add(10, 10);
	//	}

	public void adddata(double value, double fvalue)
	{
		addraw(value);
		addfiltered(fvalue);
		index++;
	}
	private void addraw(double value)
	{
		rawseries.add(index,value);
		if(rawseries.getItemCount() >98)
			rawseries.remove(0);

	}

	private void addfiltered(Double f)
	{
		filteredseries.add(index,f);
		if(filteredseries.getItemCount() >98)
			filteredseries.remove(0);

	}

	public void repaint()
	{
		mChartView.repaint();
	}

}
