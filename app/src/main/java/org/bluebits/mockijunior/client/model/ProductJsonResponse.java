/**
 * 
 */
package org.bluebits.mockijunior.client.model;

import java.util.List;

/**
 * @author satyajit
 * 
 */
public class ProductJsonResponse {

	private boolean success;

	private List<MockiProduct> products;

	public ProductJsonResponse() {

	}

	public ProductJsonResponse(boolean success, List<MockiProduct> products) {
		this.success = success;
		this.products = products;
	}

	public List<MockiProduct> getProducts() {
		return products;
	}

	public void setProducts(List<MockiProduct> products) {
		this.products = products;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
