/**
 * 
 */
package org.bluebits.mockijunior.client.model;

import java.util.List;

/**
 * @author satyajit
 *
 */
public class StoreJsonResponse {
	private boolean success;

	private List<MockiStore> stores;
	
	public StoreJsonResponse() {
		
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<MockiStore> getStores() {
		return stores;
	}

	public void setStores(List<MockiStore> stores) {
		this.stores = stores;
	}
}
