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

import com.devspark.progressfragment.SherlockProgressFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ProblemWebView extends SherlockProgressFragment {
	WebView webView;
	View mContentView;
	static String url = null;
	String title = null;
	
	@Override
	public void onResume() {
		MainActivity.isProblemSearch = true;
		MainActivity.refresh = true;
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.problem_web_view, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateUrl(MainActivity.problemNo);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void updateUrl(int problemNo) {
		url = CommonUtils.PROBLEM_URL + problemNo / 100 + "/" + problemNo
				+ ".html";
		title = "UVa " + problemNo;
		setContentView(mContentView);
		setContentShown(false);
		webView = (WebView) mContentView.findViewById(R.id.web_view);
		webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
		webView.getSettings().setAppCachePath(
				getSherlockActivity().getCacheDir().getAbsolutePath());
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		ConnectionDetector cd = new ConnectionDetector(getSherlockActivity());
		if (cd.isConnectingToInternet()) {
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		} else {
			webView.getSettings().setCacheMode(
					WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress >= 100) {
					setContentShown(true);
				}
			}
		});
		webView.loadUrl(url);
//		getSherlockActivity().getSupportActionBar().setTitle(title);
		MainActivity.problemNo = problemNo;
	}

	@Override
	public void onStop() {
		MainActivity.isProblemSearch = false;
		MainActivity.refresh = false;
		super.onStop();
	}
}