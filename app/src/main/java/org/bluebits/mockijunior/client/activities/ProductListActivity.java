/**
 * 
 */
package org.bluebits.mockijunior.client.activities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.bluebits.mockijunior.ApplicationContext;
import org.bluebits.mockijunior.MockiJunior;
import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.adapters.ProductListAdapter;
import org.bluebits.mockijunior.client.model.OrderProduct;
import org.bluebits.mockijunior.client.model.Product;
import org.bluebits.mockijunior.client.utils.ActivityLoaderUtil;
import org.bluebits.mockijunior.client.utils.DataSynchronizer;
import org.bluebits.mockijunior.client.utils.OrderCollectionUtil;

/**
 * @author satyajit
 *
 */
public class ProductListActivity extends Activity {
	private ApplicationContext context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oc_product_list);
		context = (ApplicationContext) getApplicationContext();
		
		getOverflowMenu();
		populateProductList();
	}
	
	private void populateProductList() {
		List<Product> products = context.getAppData().getProducts();
		if(products != null && products.size() == 0) {
			Toast.makeText(this, "Product list empty.",Toast.LENGTH_LONG).show();
			return;
		}
		ArrayAdapter<Product> adapter = new ProductListAdapter(this, products);
		
		ListView productList = (ListView) findViewById(R.id.productList);
		productList.setAdapter(adapter);
		productList.setChoiceMode(ListView.CHOICE_MODE_NONE);
		
		/*List<OrderProduct> orderProducts = getOrderProducts(products);
		if(orderProducts == null || orderProducts.size() == 0) {
			return;
		}
		context.getUserGeneratedData().setOrderProducts(orderProducts);*/
	}
	
	private List<OrderProduct> getOrderProducts(List<Product> products) {
		List<OrderProduct> orderProducts = null;

		if (products != null && products.size() > 0) {
			orderProducts = new ArrayList<OrderProduct>();
			for (Product product : products) {
				if (product.getQty() > 0) {
					OrderProduct orderProduct = new OrderProduct(
							product.getId(), product.getQty());
					orderProducts.add(orderProduct);
				}
			}
		}

		return orderProducts;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_oc_product_list, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_edit_customer:
			ActivityLoaderUtil.load(this, CustomerFormActivity.class);
			return true;
		case R.id.action_save_order:
			return true;
		case R.id.action_send_order:
			sendOrder();
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_sync:
			new DataSynchronizer(this).execute();
			return true;
		case R.id.action_signout:
			context.clearContext();
			ActivityLoaderUtil.load(this, MockiJunior.class);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void sendOrder() {
		try {
			OrderCollectionUtil.sendOrder(this);
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
