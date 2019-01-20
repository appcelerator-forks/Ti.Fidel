/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2017 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.fidel;

import java.io.IOException;
import android.content.Intent ;
import android.content.Context ;
import android.content.Activity ;


import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.io.TiFileFactory;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.io.TiFileFactory;
import org.json.JSONException;
import org.appcelerator.titanium.util.TiUIHelper;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;

import android.graphics.Bitmap;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import  	org.json.JSONObject ;
import org.appcelerator.titanium.util.TiActivityResultHandler;
import org.appcelerator.titanium.util.TiActivitySupport;
import org.appcelerator.titanium.util.TiConvert;

@Kroll.module(name = "Tifidel", id = "ti.fidel")
public class TifidelModule extends KrollModule implements TiActivityResultHandler {
	// Standard Debugging variables
	private static final String LCAT = "TifidelModule";
	private static final boolean DBG = TiConfig.LOGD;

	// You can define constants with @Kroll.constant, for example:
	@Kroll.constant
	public static final String COUNTRY_UNITED_KINGDOM = Fidel.Country.UNITED_KINGDOM.name();
	@Kroll.constant
	public static final String COUNTRY_IRELAND = Fidel.Country.IRELAND.name();
	@Kroll.constant
	public static final String COUNTRY_JAPAN = Fidel.Country.JAPAN.name();
	@Kroll.constant
	public static final String COUNTRY_UNITED_STATES = Fidel.Country.UNITED_STATES.name();
	@Kroll.constant
	public static final String COUNTRY_SWEDEN = Fidel.Country.SWEDEN.name();

	@Kroll.constant
	public static final int FIDEL_LINK_CARD_REQUEST_CODE = Fidel.FIDEL_LINK_CARD_REQUEST_CODE;
	@Kroll.constant
	public static final String FIDEL_LINK_CARD_RESULT_CARD = Fidel.FIDEL_LINK_CARD_RESULT_CARD;

	private boolean autoScan;

	public TifidelModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(LCAT, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}

	// Methods
	@Kroll.method
	public void init(KrollDict opts) {
		Fidel.FIDEL_LINK_CARD_REQUEST_CODE = 1234;
		if (opts.containsKeyAndNotNull("apiKey")) {
			Fidel.apiKey = opts.getString("apiKey");
		}
		if (opts.containsKeyAndNotNull("bannerImage")) {
			Fidel.bannerImage = loadImageFromApplication(opts.getString("apiKey"));
		}
		if (opts.containsKeyAndNotNull("companyName")) {
			Fidel.companyName = opts.getString("companyName");
		}
		if (opts.containsKeyAndNotNull("country")) {
			Fidel.country = Fidel.Country.valueOf(opts.getString("country"));
		}
		if (opts.containsKeyAndNotNull("privacyURL")) {
			Fidel.privacyURL = opts.getString("privacyURL");
		}
		if (opts.containsKeyAndNotNull("programId")) {
			Fidel.programId = opts.getString("programId");
		}
		if (opts.containsKeyAndNotNull("metaData")) {
			Fidel.metaData = new JSONObject(opts.getKrollDict("metaData"));
		}
	}

	private Bitmap loadImageFromApplication(String imageName) {
		Bitmap bitmap = null;
		String url = null;
		try {
			url = resolveUrl(null, imageName);
			TiBaseFile file = TiFileFactory.createTitaniumFile(new String[] { url }, false);
			bitmap = TiUIHelper.createBitmap(file.getInputStream());
		} catch (IOException e) {
			Log.e(LCAT, "Fidel only supports local image files " + url);
		}
		return bitmap;
	}

	@Kroll.method
	public void createForm() {
		present();
	}

	@Kroll.method
	public void startScanner() {
		autoScan = true;
		present();
	}

	@Kroll.method
	public void present() {
		Context context = TiApplication.getInstance().getApplicationContext();
		final TiActivitySupport activitySupport = (TiActivitySupport) TiApplication
				.getInstance().getCurrentActivity();
		/*activitySupport.launchActivityForResult(intent,
				REQUEST_CODE_PAYMENT, new PaymentResultHandler());
				*/
		Activity act = TiApplication.getInstance().getRootOrCurrentActivity();
		Fidel.present(act);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
	        if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
	            LinkResult card = (LinkResult)data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);

	            Log.d("d", "CARD ID = " + card.id);
	        }
	    }
	}
}
