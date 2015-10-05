/**
 * 
 */
package org.bluebits.mockijunior.client.net;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author satyajit
 *
 */

public class MockiHttpClient {
	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/";

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	static {
		client.setTimeout(10 * 1000);// A 10 seconds timeout set
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		//client.get(getAbsoluteUrl(url), params, responseHandler);
		//This is stupid for now because the url pattern is not following proper REST convention now
		client.get(url, params, responseHandler);
	}

	public static void post(Context ctx, String url, HttpEntity entity, String contentType,
			AsyncHttpResponseHandler responseHandler) {
		//client.post(getAbsoluteUrl(url), params, responseHandler);
		//This is stupid for now because the url pattern is not following proper REST convention now
		client.post(ctx, url, entity, contentType, responseHandler);
	}

	@SuppressWarnings("unused")
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
