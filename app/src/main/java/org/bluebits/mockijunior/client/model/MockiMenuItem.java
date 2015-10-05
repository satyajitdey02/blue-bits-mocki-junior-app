/**
 * 
 */
package org.bluebits.mockijunior.client.model;

/**
 * @author satyajit
 * 
 */
public class MockiMenuItem {
	public int id;
	public int iconResId;
	public String name;

	public MockiMenuItem(int id, int iconResId, String name) {
		this.id = id;
		this.iconResId = iconResId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
