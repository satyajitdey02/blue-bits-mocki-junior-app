package org.bluebits.mockijunior.client.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;



public class UserGeneratedData implements Parcelable {
	private int storeId;
	private String storeName;
	private int sessionId;
	private String sessionName;
	private String deliveryDate;
	private String remarks;
	private List<OrderProduct> orderProducts;

	public UserGeneratedData() {

	}
	
	public UserGeneratedData(Parcel in) {
		readFromParcel(in);
	}

	public UserGeneratedData(int storeId, int sessionId,
			String deliveryDate, String remarks,
			List<OrderProduct> orderProducts) {
		this.storeId = storeId;
		this.sessionId = sessionId;
		this.deliveryDate = deliveryDate;
		this.remarks = remarks;
		this.orderProducts = orderProducts;
	}
	
	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(storeId);
		out.writeString(storeName);
		out.writeInt(sessionId);
		out.writeString(sessionName);
		out.writeString(deliveryDate);
		out.writeString(remarks);
		out.writeTypedList(orderProducts);
	}

	private void readFromParcel(Parcel in) {
		storeId = in.readInt();
		storeName = in.readString();
		sessionId = in.readInt();
		sessionName = in.readString();
		deliveryDate = in.readString();
		remarks = in.readString();
		orderProducts = new ArrayList<OrderProduct>();
	    in.readTypedList(orderProducts, OrderProduct.CREATOR);
	}

	public static final Parcelable.Creator<UserGeneratedData> CREATOR = 
			new Parcelable.Creator<UserGeneratedData>() {
		public UserGeneratedData createFromParcel(Parcel in) {
			return new UserGeneratedData(in);
		}

		public UserGeneratedData[] newArray(int size) {
			return new UserGeneratedData[size];
		}
	};
}
