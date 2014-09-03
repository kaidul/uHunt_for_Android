/*
 * uHunt for Android - The most comprehensive Android app for uHunt and Competitive programming
 * Copyright (C) 2013 Kaidul Islam
 * 
 * This file is part of uHunt for Android.

 * uHunt for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * uHunt for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with uHunt for Android.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.kaidul.uhunt;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineGraph {

	public GraphicalView getView(Context context) {

		// Our first data
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y = { 30, 34, 45, 57, 77, 89, 100, 111, 123, 145 }; // y values!
		TimeSeries series = new TimeSeries("Progress Line");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		renderer.setColor(Color.BLACK);
		renderer.setPointStyle(PointStyle.DIAMOND);
		renderer.setFillPoints(false);

		mRenderer.setChartTitle("Progress over the Years");
		mRenderer.setLabelsTextSize(14);
		mRenderer.setChartTitleTextSize(18);
		mRenderer.setXTitle("Years");
		mRenderer.setYTitle("No. of Solved Problems");
		mRenderer.setAxesColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setZoomEnabled(true);
		mRenderer.setShowLegend(false);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setYLabelsColor(0, Color.BLACK);

		return ChartFactory.getLineChartView(context, dataset, mRenderer);

	}

}