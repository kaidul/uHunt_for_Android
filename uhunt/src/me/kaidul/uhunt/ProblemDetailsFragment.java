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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.devspark.progressfragment.SherlockProgressFragment;

public class ProblemDetailsFragment extends SherlockProgressFragment {

	final static String URL = "url";
	final static String Title = "problem_title";
	String mCurrentURL = null;
	View mContentView = null;
	WebView mWebView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setHasOptionsMenu(true);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCurrentURL = savedInstanceState.getString(URL);
		}
		mContentView = inflater.inflate(R.layout.web_layout, container, false);
		mWebView = (WebView) mContentView.findViewById(R.id.webPage);
		mWebView.setWebViewClient(new SwAWebClient());
		mWebView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024);
		mWebView.getSettings().setAppCachePath(
				getSherlockActivity().getCacheDir().getAbsolutePath());
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setContentView(mContentView);
//		setContentShown(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			updateUrl(args.getString(URL));
		} else if (mCurrentURL != "") {
			updateUrl(mCurrentURL);
		}
	}
	
	public void updateUrl(String url) {
		setContentShown(false);
		ConnectionDetector cd = new ConnectionDetector(getSherlockActivity());
		if (cd.isConnectingToInternet()) {
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		} else {
			mWebView.getSettings().setCacheMode(
					WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		mCurrentURL = url;
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress >= 100) {
					setContentShown(true);
				}
			}
		});
		mWebView.loadUrl(mCurrentURL);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(URL, mCurrentURL);
	}

	private class SwAWebClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}

	}

}