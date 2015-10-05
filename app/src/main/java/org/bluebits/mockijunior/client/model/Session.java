/**
 * 
 */
package org.bluebits.mockijunior.client.model;

/**
 * @author satyajit
 *
 */
public class Session {
	String id;
	String name;
	
	public String getSpinnerText() {
		return id;
	}
	
	public Session(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
