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
public class Store implements Parcelable {
	private int id;
	private String name;

	public Store() { 
		
	}
	
	public Store(Parcel in) {
		readFromParcel(in);
	}

	public Store(int id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(name);
	}

	private void readFromParcel(Parcel in) {
		id = in.readInt();
		name = in.readString();
	}

	public static final Parcelable.Creator<Store> CREATOR = 
			new Parcelable.Creator<Store>() {
		public Store createFromParcel(Parcel in) {
			return new Store(in);
		}

		public Store[] newArray(int size) {
			return new Store[size];
		}
	};
}
