package org.bluebits.mockijunior.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.bluebits.mockijunior.client.model.Customer;
import org.bluebits.mockijunior.client.model.Product;
import org.bluebits.mockijunior.client.model.Store;

public class DBController extends SQLiteOpenHelper {

	private static final String DB_NAME = "mockijunior.db";
	private static final String TABLE_CUSTOMER = "customer";
	private static final String TABLE_STORE = "store";
	private static final String TABLE_PRODUCT = "product";

	public DBController(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		MockiTablesCreator tableCreator = new MockiTablesCreator(database);
		dropTables(database);
		tableCreator.createCustomerTable();
		tableCreator.createStoreTable();
		tableCreator.createProductTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		dropTables(database);
		onCreate(database);
	}

	private void dropTables(SQLiteDatabase database) {
		String query = "DROP TABLE IF EXISTS customer";//DROP customer
		database.execSQL(query);

		query = "DROP TABLE IF EXISTS store";//DROP store
		database.execSQL(query);

		query = "DROP TABLE IF EXISTS product";//DROP product
		database.execSQL(query);
	}

	public void addCustomer(Customer customer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("firstName", customer.getFirstName());
		values.put("lastName", customer.getLastName());
		values.put("email", customer.getEmail());
		values.put("password", customer.getPassword());

		db.insert(TABLE_CUSTOMER, null, values);
		db.close();
	}
	
	/*public Customer addCustomer(Customer customer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("firstName", customer.getFirstName());
		values.put("lastName", customer.getLastName());
		values.put("email", customer.getEmail());
		values.put("password", customer.getPassword());
		
		db.insert(TABLE_CUSTOMER, null, values);
		db.close(); 
		
		return findCustomer(customer.getEmail(), customer.getEmail());
	}*/

	public Customer findCustomer(String email, String password) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CUSTOMER, 
				new String[] {"id", "email"},"email=? AND password=?",
				new String[] { email.trim(), password }, null, null, null, null);
		
		Customer customer = null;
		
		if(cursor != null) {
			cursor.moveToFirst();
			customer = new Customer();
			customer.setId(cursor.getInt(0));
			customer.setEmail(cursor.getString(1));
		}
		
		return customer;
	}
	
	public List<Customer> getAllCustomer() {
		List<Customer> customerList = new ArrayList<Customer>();
		String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(cursor.getString(0)));
				customer.setEmail(cursor.getString(1));
				customerList.add(customer);
			} while (cursor.moveToNext());
		}

		return customerList;
	}
	
	public void addStore(Store store) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", store.getId());
			values.put("name", store.getName());
			db.insert(TABLE_STORE, null, values);
		} catch (Exception e) {
			Log.e("ERROR", "Error adding store(id: " + store.getId()
					+ ", name: " + store.getName() + ") to DB");
		} finally {
			db.close();
		}
	}
	
	public void addStores(List<Store> stores) {
		for (Store store : stores) {
			addStore(store);
		}
	}
	
	public List<Store> getAllStores() {
		List<Store> storeList = new ArrayList<Store>();
		String selectQuery = "SELECT  * FROM " + TABLE_STORE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Store store = new Store();
				store.setId(Integer.parseInt(cursor.getString(0)));
				store.setName(cursor.getString(1));
				storeList.add(store);
			} while (cursor.moveToNext());
		}

		return storeList;
	}
	
	public void addProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put("id", product.getId());
			values.put("name", product.getName());
			db.insert(TABLE_PRODUCT, null, values);
		} catch (Exception e) {
			Log.e("ERROR", "Error adding product(id: " + product.getId()
					+ ", name: " + product.getName() + ") to DB.");
		} finally {
			db.close();
		}
	}
	
	public void addProducts(List<Product> products) {
		for (Product product : products) {
			addProduct(product);
		}
	}

	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<Product>();
		String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Product product = new Product();
				product.setId(Integer.parseInt(cursor.getString(0)));
				product.setName(cursor.getString(1));
				productList.add(product);
			} while (cursor.moveToNext());
		}

		return productList;
	}

	private int getDataCount(String table) {
		String countQuery = "SELECT  * FROM " + table;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}
	
	public int getCustomerCount() {
		return getDataCount(TABLE_CUSTOMER);
	}
	
	public int getStoreCount() {
		return getDataCount(TABLE_STORE);
	}
	
	public int getProductCount() {
		return getDataCount(TABLE_CUSTOMER);
	}
}
