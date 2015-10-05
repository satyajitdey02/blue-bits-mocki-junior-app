/**
 * 
 */
package org.bluebits.mockijunior.client.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author satyajit
 *
 */
public class NetworkConnectivityManager {
	
	public static boolean isConnected(Context ctx) {
		ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		return (networkInfo != null && networkInfo.isConnected()); 
	}

}
