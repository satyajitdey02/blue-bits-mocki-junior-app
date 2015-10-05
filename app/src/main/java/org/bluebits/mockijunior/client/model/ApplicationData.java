package org.bluebits.mockijunior.client.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationData implements Parcelable {
	
	private Boolean hasRegisteredUser;
	private List<Store> stores;
	private List<Product> products;
	
	public ApplicationData() {
	
	}
	
	public ApplicationData(Parcel in) {
		readFromParcel(in);
	}

	public ApplicationData(Boolean hasRegisteredUser, List<Store> stores, List<Product> products) {
		this.hasRegisteredUser = hasRegisteredUser;
		this.stores = stores;
		this.products = products;
	}

	public Boolean hasRegisteredUser() {
		return hasRegisteredUser;
	}

	public void setHasRegisteredUser(Boolean hasRegisteredUser) {
		this.hasRegisteredUser = hasRegisteredUser;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(hasRegisteredUser ? 1 : 0);  
		out.writeTypedList(stores);
		out.writeTypedList(products);
	}
	
	private void readFromParcel(Parcel in) {
		hasRegisteredUser = in.readInt() == 1;
		
		stores = new ArrayList<Store>();
	    in.readTypedList(stores, Store.CREATOR);
	    
	    products = new ArrayList<Product>();
	    in.readTypedList(products, Product.CREATOR);
	}
	
	public static final Parcelable.Creator<ApplicationData> CREATOR =
            new Parcelable.Creator<ApplicationData>() {
        public ApplicationData createFromParcel(Parcel in) {
            return new ApplicationData(in);
        }
 
        public ApplicationData[] newArray(int size) {
            return new ApplicationData[size];
        }
    };
}
