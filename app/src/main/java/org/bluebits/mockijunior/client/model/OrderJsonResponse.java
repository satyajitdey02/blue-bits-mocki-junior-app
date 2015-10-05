/**
 * 
 */
package org.bluebits.mockijunior.client.model;

/**
 * @author satyajit
 * 
 */
public class OrderJsonResponse {

	private boolean success;
	private String order_id;

	public OrderJsonResponse() {

	}

	public OrderJsonResponse(boolean success, String order_id) {
		this.success = success;
		this.order_id = order_id;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOrderId() {
		return order_id;
	}

	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}
}
