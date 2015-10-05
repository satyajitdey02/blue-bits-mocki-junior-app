/**
 * 
 */
package org.bluebits.mockijunior.client.utils;

import org.bluebits.mockijunior.client.model.ApplicationData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author satyajit
 *
 */
public class ActivityLoaderUtil {
	
	private static final String EXTRA_KEY_BUNDLE = "bundle";
	private static final String EXTRA_KEY_DATA = "data";
	
	
	public static <T> void load(Context context, Class<T> activityClazz) {
		Intent intent = new Intent(context, activityClazz);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	} 
	
	public static <T> void load(Context context, Class<T> activityClazz, Bundle bundle) {
		Intent intent = new Intent(context, activityClazz);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra(EXTRA_KEY_BUNDLE, bundle);
		context.startActivity(intent);
	} 
	
	public static <T> void load(Context context, Class<T> activityClazz, ApplicationData data) {
		Intent intent = new Intent(context, activityClazz);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra(EXTRA_KEY_DATA, data);
		context.startActivity(intent);
	} 
}
