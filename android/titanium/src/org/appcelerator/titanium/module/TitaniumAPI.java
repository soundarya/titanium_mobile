/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package org.appcelerator.titanium.module;

import java.io.UnsupportedEncodingException;

import org.appcelerator.titanium.TitaniumMethod;
import org.appcelerator.titanium.TitaniumModuleManager;
import org.appcelerator.titanium.api.ITitaniumAPI;
import org.appcelerator.titanium.api.ITitaniumJSRef;
import org.appcelerator.titanium.api.ITitaniumMethod;
import org.appcelerator.titanium.config.TitaniumConfig;
import org.appcelerator.titanium.module.api.TitaniumMemoryBlob;
import org.appcelerator.titanium.util.Log;

import android.webkit.WebView;

public class TitaniumAPI extends TitaniumBaseModule implements ITitaniumAPI
{
	private static final String LCAT = "TiAPI";
	private static final boolean DBG = TitaniumConfig.LOGD;

	public TitaniumAPI(TitaniumModuleManager manager, String name) {
		super(manager, name);
	}

	@Override
	public void register(WebView webView) {
		String name = getModuleName();
		if (DBG) {
			Log.d(LCAT, "Registering TitaniumAPI as " + name);
		}
		webView.addJavascriptInterface((ITitaniumAPI) this, name);
	}

	public void log(int severity, String msg)
	{
		/*
		TRACE: 1,
		DEBUG: 2,
		INFO: 3,
		NOTICE: 4,
		WARN: 5,
		ERROR: 6,
		CRITICAL: 7,
		FATAL: 8*/
		if (severity == 1)
		{
			Log.v(LCAT,msg);
		}
		else if (severity < 3)
		{
			Log.d(LCAT,msg);
		}
		else if (severity < 5)
		{
			Log.i(LCAT,msg);
		}
		else if (severity == 5)
		{
			Log.w(LCAT,msg);
		}
		else
		{
			Log.e(LCAT,msg);
		}
	}

	public void updateNativeControls(String json) {
		getModuleManager().getWebView().updateNativeControls(json);
	}

	public void signal(String syncId) {
		getModuleManager().getWebView().signal(syncId);
	}

	public void invalidateLayout() {
		getModuleManager().getWebView().invalidateLayout();
	}

	public ITitaniumJSRef getObjectReference(int key) {
		return getModuleManager().getObjectReference(key);
	}

	public int getTitaniumMemoryBlobLength(int key) {
		int len = -1;
		TitaniumMemoryBlob blob = (TitaniumMemoryBlob) tmm.getObject(key);
		if (blob != null) {
			len = blob.getLength();
		}
		return len;
	}

	public String getTitaniumMemoryBlobString(int key) {
		String value = null;

		TitaniumMemoryBlob blob = (TitaniumMemoryBlob) tmm.getObject(key);
		if (blob != null) {
			try {
				byte[] data = blob.getData();
				if (data != null) {
					value = new String(data, "UTF-8");
				} else {
					Log.w(LCAT, "Blob data reclaimed due to low memory or reference count.");
				}
			} catch (UnsupportedEncodingException e) {
				Log.w(LCAT, "Unable to encode data as utf-8", e);
			}
		}

		return value;
	}

	public ITitaniumMethod acquireMethod() {
		return new TitaniumMethod(tmm);
	}
}