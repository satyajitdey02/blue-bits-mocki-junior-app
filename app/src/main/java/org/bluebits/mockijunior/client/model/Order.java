/**
 * 
 */
package org.bluebits.mockijunior.client.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * @author satyajit
 * 
 */
public class Order implements Parcelable {
	private String customerId;
	private int storeId;
	private int sessionId;
	private String deliveryDate;
	private String remarks;
	private List<OrderProduct> orderProducts;

	public Order() {

	}
	
	public Order(Parcel in) {
		readFromParcel(in);
	}

	public Order(String customerId, int storeId, int sessionId,
			String deliveryDate, String remarks,
			List<OrderProduct> orderProducts) {
		this.customerId = customerId;
		this.storeId = storeId;
		this.sessionId = sessionId;
		this.deliveryDate = deliveryDate;
		this.remarks = remarks;
		this.orderProducts = orderProducts;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getJsonOrder() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(customerId);
		out.writeInt(storeId);
		out.writeInt(sessionId);
		out.writeString(deliveryDate);
		out.writeString(remarks);
		out.writeTypedList(orderProducts);
	}

	private void readFromParcel(Parcel in) {
		customerId = in.readString();
		storeId = in.readInt();
		sessionId = in.readInt();
		deliveryDate = in.readString();
		remarks = in.readString();
		orderProducts = new ArrayList<OrderProduct>();
	    in.readTypedList(orderProducts, OrderProduct.CREATOR);
	}

	public static final Parcelable.Creator<Order> CREATOR = 
			new Parcelable.Creator<Order>() {
		public Order createFromParcel(Parcel in) {
			return new Order(in);
		}

		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
}
