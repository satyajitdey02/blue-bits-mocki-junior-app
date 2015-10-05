package org.bluebits.mockijunior;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.bluebits.mockijunior.client.model.MockiProduct;
import org.bluebits.mockijunior.client.model.MockiStore;
import org.bluebits.mockijunior.client.model.Product;
import org.bluebits.mockijunior.client.model.ProductJsonResponse;
import org.bluebits.mockijunior.client.model.Store;
import org.bluebits.mockijunior.client.model.StoreJsonResponse;
import org.bluebits.mockijunior.client.net.MockiHttpClient;
import org.bluebits.mockijunior.client.net.NetworkConnectivityManager;
import org.bluebits.mockijunior.client.utils.ActivityLoaderUtil;
import org.bluebits.mockijunior.db.DBController;

public class SplashScreen extends Activity {
	private ApplicationContext context;
	private DBController dbController = new DBController(this); 
	private static final String BASE_URL = "http://desertshipbd.com/mocki_server/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context = (ApplicationContext) getApplicationContext();
		
		new PrefetchData().execute();
		
		ActivityLoaderUtil.load(SplashScreen.this, MockiJunior.class);
		finish();
	}
	
	private void fetchRemoteServerData() {
		try {
			// For now Mocki will Load all data without checking
			if (!NetworkConnectivityManager.isConnected(this)) {
				Toast.makeText(SplashScreen.this, "Network not available!",
						Toast.LENGTH_LONG).show();
				return;
			}
	
			loadProductList();
			loadStoreList();
		} catch (Exception e) {
			Log.e("ERROR", "Error loading AppData to ApplicationContext");
		}
	}
	
	private void loadStoreList() {
		RequestParams params = new RequestParams();
		params.put("route", "feed/web_api/stores");
		params.put("email", context.getLoggedInUser());
		
		MockiHttpClient.get(BASE_URL, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				try {
					Gson gson = new Gson();
					StoreJsonResponse jsonResponse = gson.fromJson(response,
							StoreJsonResponse.class);
					List<Store> stores = null;
					if (jsonResponse.isSuccess()
							&& jsonResponse.getStores().size() > 0) {
						stores = new ArrayList<Store>();
						for (MockiStore ms : jsonResponse.getStores()) {
							Store store = new Store();
							store.setId(Integer.parseInt(ms.getAddress_id()));
							store.setName(ms.getCompany());
							stores.add(store);
						}
					}

					if (stores != null && stores.size() > 0) {
						dbController.addStores(stores);
					}

				} catch (Exception e) {
					Toast.makeText(SplashScreen.this,
							"Error Loading store list. Please try again.",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				if (error.getCause() instanceof ConnectTimeoutException) {
					Toast.makeText(SplashScreen.this,
							"Connection timeout, Please try again.",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Toast.makeText(SplashScreen.this,
						"Error Loading store list. Please try again.",
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void loadProductList() {
		RequestParams params = new RequestParams();
		params.put("route", "feed/web_api/products");
		params.put("category", "");
		params.put("key", "desertship");

		MockiHttpClient.get(BASE_URL, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				Gson gson = new Gson();
				ProductJsonResponse jsonResponse = gson.fromJson(response,
						ProductJsonResponse.class);

				List<Product> products = null;
				if (jsonResponse.isSuccess()
						&& jsonResponse.getProducts().size() > 0) {
					products = new ArrayList<Product>();

					for (MockiProduct mp : jsonResponse.getProducts()) {
						Product product = new Product();
						product.setId(Integer.parseInt(mp.getId()));
						product.setName(mp.getName());
						products.add(product);
					}
				}

				if (products != null && products.size() > 0) {
					dbController.addProducts(products);
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				if (error.getCause() instanceof ConnectTimeoutException) {
					Toast.makeText(SplashScreen.this,
							"Connection timeout, Please try again.",
							Toast.LENGTH_SHORT).show();
					return;
				}

				Toast.makeText(SplashScreen.this,
						"Error Loading Product list. Please try again.",
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private class PrefetchData extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			fetchRemoteServerData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			try {
				// Give Mocki 2 seconds to take a breath and save fetched data from server
				Thread.sleep(2 * 1000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ActivityLoaderUtil.load(SplashScreen.this, MockiJunior.class);
			finish();
		}
	}
}
