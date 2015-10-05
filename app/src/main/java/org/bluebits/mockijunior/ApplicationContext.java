package org.bluebits.mockijunior;

import java.util.List;

import android.app.Application;
import android.util.Log;

import org.bluebits.mockijunior.client.model.ApplicationData;
import org.bluebits.mockijunior.client.model.Order;
import org.bluebits.mockijunior.client.model.Product;
import org.bluebits.mockijunior.client.model.Store;
import org.bluebits.mockijunior.client.model.UserGeneratedData;
import org.bluebits.mockijunior.db.DBController;

public class ApplicationContext extends Application {
	private String loggedInUser;
	private ApplicationData appData ;
	private UserGeneratedData userGeneratedData;
	private Order orderData;
	private DBController dbController = new DBController(this);
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public ApplicationData getAppData() {
		try {
			if(appData == null) {
				appData = new ApplicationData();
				List<Store> stores = dbController.getAllStores();
				List<Product> products = dbController.getAllProducts();
				
				appData.setStores(stores);
				appData.setProducts(products);
			}
			
			if(appData.getStores() == null || appData.getStores().size() == 0) {
				List<Store> stores = dbController.getAllStores();
				appData.setStores(stores);
			}
			
			if(appData.getProducts() == null || appData.getProducts().size() == 0) {
				List<Product> products = dbController.getAllProducts();
				appData.setProducts(products);
			}
		} catch (Exception e) {
			Log.e("ERROR", "Error loading AppData to ApplicationContext");
		}
		
		return appData;
	}

	public void setAppData(ApplicationData appData) {
		this.appData = appData;
	}

	public UserGeneratedData getUserGeneratedData() {
		return userGeneratedData;
	}

	public void setUserGeneratedData(UserGeneratedData userGeneratedData) {
		this.userGeneratedData = userGeneratedData;
	}

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public Order getOrderData() {
		return orderData;
	}

	public void setOrderData(Order orderData) {
		this.orderData = orderData;
	}
	
	public void clearContext() {
		this.loggedInUser = null;
		this.userGeneratedData = null;
		this.orderData = null;
	}
}
