/**
 * 
 */
package org.bluebits.mockijunior.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author satyajit
 *
 */
public class MockiTablesCreator {
	private SQLiteDatabase database;
	
	public MockiTablesCreator(SQLiteDatabase database) {
		this.database = database;
	}
	
	public void createCustomerTable() {
		String query = "CREATE TABLE customer (id INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
		database.execSQL(query);
        Log.d("MOCKI_TABLE_CREATOR","TABLE::customer Created");
        
	}
	
	public void createStoreTable() {
		String query = "CREATE TABLE store ( id INTEGER PRIMARY KEY, name TEXT)";
        database.execSQL(query);
        Log.d("MOCKI_TABLE_CREATOR","TABLE::store Created");
	}
	
	public void createProductTable() {
		String query = "CREATE TABLE product ( id INTEGER PRIMARY KEY, name TEXT, price REAL)";
        database.execSQL(query);
        Log.d("MOCKI_TABLE_CREATOR","TABLE::product Created");
	}
}
