/**
 * 
 */
package org.bluebits.mockijunior.client.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author satyajit
 * 
 */
public class Product implements Parcelable {

	private int id;
	private String name;
	private int qty;

	public Product() { }
	
	public Product(Parcel in) {
		readFromParcel(in);
	}

	public Product(int id, String name, int qty) {
		this.id = id;
		this.name = name;
		this.qty = qty;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(name);
		out.writeInt(qty);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		name = in.readString();
		qty = in.readInt();
	}
	
	public static final Parcelable.Creator<Product> CREATOR = 
			new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}
