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

public class LevelGraphDrawer extends BarGraphDrawer {

	String[] code = { "#71d7ff", "#45c0f0", "#2e9fcc", "#2089b3", "#167ba2",
			"#0e688b", "#085573", "#05455e", "#03374c", "#01202c" };
	String[] label = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5",
			"Level 6", "Level 7", "Level 8", "Level 9", "Level 10" };

	public LevelGraphDrawer(String graphTitle, int screenWidth, int[] values) {
		super(graphTitle, screenWidth, values);
		values = new int[10];
		barCount = 10;
		for (int i = 0; i < code.length; i++) {
			ColorCode[i] = code[i];
			verdictLabel[i] = label[i];
		}
	}

}
