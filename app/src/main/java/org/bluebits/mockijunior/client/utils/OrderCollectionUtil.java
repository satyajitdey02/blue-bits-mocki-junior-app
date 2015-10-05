package org.bluebits.mockijunior.client.utils;

import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.bluebits.mockijunior.ApplicationContext;
import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.model.Order;
import org.bluebits.mockijunior.client.model.OrderJsonResponse;
import org.bluebits.mockijunior.client.model.UserGeneratedData;
import org.bluebits.mockijunior.client.net.MockiHttpClient;
import org.bluebits.mockijunior.client.net.NetworkConnectivityManager;

public class OrderCollectionUtil {
	
	private static ApplicationContext context;
	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/?route=feed/web_api/order";
	private static final String CONTENT_TYPE = "application/json";
	
	private static Order extractOrder() {
		UserGeneratedData ugd = context.getUserGeneratedData();
		if(ugd == null) {
			return null;
		}
		
		Order order = new Order();
		order.setCustomerId(context.getLoggedInUser());
		order.setStoreId(ugd.getStoreId());
		order.setSessionId(ugd.getSessionId());
		order.setDeliveryDate(ugd.getDeliveryDate());
		order.setRemarks(ugd.getRemarks());
		order.setOrderProducts(ugd.getOrderProducts());
		
		return order;
	}
	
	private static String extractOrderAsJson(Order order) {
		return order != null ? order.getJsonOrder() : null;
	}
	
	public static void sendOrder(final Activity parent) throws Exception {
		context = (ApplicationContext) parent.getApplicationContext();
		Order order = extractOrder();
		if(order == null) {
			throw new Exception("Error extracting Order. Please try again.");
		}
		
		if(order.getStoreId() <= 0) {
			throw new Exception("No Customer is selected! Please select Store.");
		}
		
		if(TextUtils.isEmpty(order.getDeliveryDate())) {
			throw new Exception("No Delivery Date is selected! Please choose a Date.");
		}
		
		if(order.getOrderProducts() == null || order.getOrderProducts().size() == 0) {
			throw new Exception("No product(s) selected! Please select product(s).");
		}
		
		String jsonOrder = OrderCollectionUtil.extractOrderAsJson(order);
		if(jsonOrder == null) {
			throw new Exception("Bad formatted Order. Please try again.");
		}
		
		if(!NetworkConnectivityManager.isConnected(parent)) {
			throw new Exception("Network not available!. Please try again.");
		}
			
		HttpEntity entity = null;
		
		try {
			entity = new StringEntity(jsonOrder);
		} catch (Exception e) {
			Log.e("ERROR", "Error parsing json order to HttpEntity Order.");
		}
		
		if(entity == null) {
			throw new Exception("Bad formatted Order. Please try again.");
		}
		
		MockiHttpClient.post(parent, BASE_URL, entity, CONTENT_TYPE, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				showOrderResponse(parent, response);
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
			    if (error.getCause() instanceof ConnectTimeoutException) {
			    	Toast.makeText(parent, "Connection timeout, Please try again.", Toast.LENGTH_LONG).show();
			    } else if(error.getCause() instanceof SocketTimeoutException) {
			    	Toast.makeText(parent, "Socket timeout, Please try again.", Toast.LENGTH_LONG).show();
			    } else if(error.getCause() instanceof ConnectionPoolTimeoutException) {
			    	Toast.makeText(parent, "Collection Pool timeout, Please try again.", Toast.LENGTH_LONG).show();
			    }
			}
			
			@Override
			public void onFinish() {
				//OrderCollectionUtil.resetOrder();
			}
		});
		
	}
	
	private static void showOrderResponse(Activity parent, String response) {
		AlertDialog.Builder builder = new AlertDialog.Builder(parent);

		if (TextUtils.isEmpty(response)) {
			builder.setTitle("Error")
				   .setIcon(R.drawable.ic_error)
				   .setMessage("Empty response found. Please contact with sysadmin.");
		} else {
			OrderJsonResponse jsonResponse = new Gson().fromJson(response,OrderJsonResponse.class);

			if (jsonResponse.isSuccess()
					&& !TextUtils.isEmpty(jsonResponse.getOrderId())) {
				builder.setTitle("Success")
					   .setIcon(R.drawable.ic_success)
					   .setMessage("Order sent successfully. Order ID:" + jsonResponse.getOrderId());
			} else {
				builder.setTitle("Error")
					   .setIcon(R.drawable.ic_error)
					   .setMessage("Error sending Order. Please try again.");
			}
		}

		builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		builder.show();
	}
}
