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
public class OrderProduct implements Parcelable{

	private int productId;
	private int productQty;

	public OrderProduct() {

	}
	
	public OrderProduct(Parcel in) {
		readFromParcel(in);
	}

	public OrderProduct(int productId, int qty) {
		this.productId = productId;
		this.productQty = qty;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(productId);
		out.writeInt(productQty);
	}

	private void readFromParcel(Parcel in) {
		productId = in.readInt();
		productQty = in.readInt();
	}

	public static final Parcelable.Creator<OrderProduct> CREATOR = 
			new Parcelable.Creator<OrderProduct>() {
		public OrderProduct createFromParcel(Parcel in) {
			return new OrderProduct(in);
		}

		public OrderProduct[] newArray(int size) {
			return new OrderProduct[size];
		}
	};
}
