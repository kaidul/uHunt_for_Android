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
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class BarGraphDrawer {
	String graphTitle;
	int ScreenWidth;
	int barCount = 8;
	int[] values = new int[15];
	String[] ColorCode = new String[15];
	String[] code = { "#00AA00", "#666600", "#FF0000", "#0000FF",
			"#6767D0", "#AAAA00", "#00AAAA", "#000000" };
	String[] verdictLabel = new String[15];
	String[] label = {"AC", "PE", "WA", "TL", "ML", "CE", "RE", "OT"};
	float val = 0;
	
	public BarGraphDrawer(String graphTitle, int screenWidth, int[] values) {
		this.graphTitle = graphTitle;
		this.ScreenWidth = screenWidth;
		this.values = (int[]) values.clone();
		for (int i = 0; i < code.length; i++) {
			ColorCode[i] = code[i];
			verdictLabel[i] = label[i];
		}
	}

	public GraphicalView getView(Context context) {
		int leftMargin = 30;
		int rightMargin = 20;
		
		int range = (ScreenWidth - leftMargin - rightMargin) / barCount;
		int barWidth = (ScreenWidth - leftMargin - rightMargin) / (barCount + 1);
		int yMax = -1;
		for (int i = 0; i < values.length; i++) {
			if(yMax < values[i]) yMax = values[i];
		}
		yMax += (yMax / 10);
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < barCount; i++) {
			XYSeries bar = new XYSeries(verdictLabel[i]);
			bar.add(i + 1, values[i]);
			dataset.addSeries( bar );
		}

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle(graphTitle);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setAxesColor(Color.BLACK);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setYLabelsColor(0, Color.BLACK);

		mRenderer.setBarWidth(barWidth);
		mRenderer.setXAxisMin(-range);
		mRenderer.setXAxisMax(range + 10);
		mRenderer.setYAxisMax(yMax);
		mRenderer.setYAxisMin(0);
		mRenderer.setXLabels(0);
		mRenderer.setPanEnabled(false, false);
		mRenderer.setZoomEnabled(false);
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, metrics);
        mRenderer.setAxisTitleTextSize(val);
        mRenderer.setChartTitleTextSize(val);
        mRenderer.setLabelsTextSize(val);
        mRenderer.setLegendTextSize(val);
        val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, metrics);
        mRenderer.setChartTitleTextSize(val);
		
		for (int i = 0; i < barCount; i++) {
			mRenderer.addSeriesRenderer(customization(ColorCode[i]));
		}

		return ChartFactory.getBarChartView(context, dataset, mRenderer,
				Type.DEFAULT);
	}

	XYSeriesRenderer customization(String color) {
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.parseColor(color));
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(val);
		return renderer;
	}

}
