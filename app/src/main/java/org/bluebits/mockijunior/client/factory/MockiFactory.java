package org.bluebits.mockijunior.client.factory;

import java.util.ArrayList;
import java.util.List;

import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.model.MockiMenuItem;
import org.bluebits.mockijunior.client.model.Product;



public class MockiFactory {

	private static final int PRODUCT_LIST_SIZE = 50;

	public static List<Product> getProducts() {
		List<Product> products = new ArrayList<Product>();

		for (int i = 0; i < PRODUCT_LIST_SIZE; i++) {
			Product product = new Product();
			product.setName("Mocki Product " + (i + 1));
			products.add(product);
		}

		return products;
	}

	public static class MockiMenu {
		private static List<MockiMenuItem> items;

		public static List<MockiMenuItem> getMenuItems() {
			items = new ArrayList<MockiMenuItem>();
			items.add(new MockiMenuItem(1, R.drawable.ic_mod_icon, "Order Collection"));
			items.add(new MockiMenuItem(2, R.drawable.ic_mod_icon, "Doctors Call"));
			items.add(new MockiMenuItem(3, R.drawable.ic_mod_icon, "Field Survey"));
			items.add(new MockiMenuItem(4, R.drawable.ic_mod_icon, "Review & Management"));
			items.add(new MockiMenuItem(5, R.drawable.ic_mod_icon, "Synchronization"));
			items.add(new MockiMenuItem(6, R.drawable.ic_mod_icon, "Application Settings"));
			items.add(new MockiMenuItem(7, R.drawable.ic_mod_icon, "About BlueBits"));
			items.add(new MockiMenuItem(8, R.drawable.ic_mod_icon, "Signout"));

			return items;
		}

		public static MockiMenuItem getItemById(int id) {

			for (MockiMenuItem item : items) {
				if (item.id == id) {
					return item;
				}
			}

			return null;
		}
	}
}
