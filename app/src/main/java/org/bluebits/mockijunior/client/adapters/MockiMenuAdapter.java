/**
 * 
 */
package org.bluebits.mockijunior.client.adapters;

/**
 * @author satyajit
 *
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.factory.MockiFactory;
import org.bluebits.mockijunior.client.model.MockiMenuItem;

public class MockiMenuAdapter extends ArrayAdapter<MockiMenuItem> {

	private final Context context;
	private final List<MockiMenuItem> menuItems;
	private final int rowResourceId;

	public MockiMenuAdapter(Context context, int textViewResourceId,
			List<MockiMenuItem> menuItems) {

		super(context, textViewResourceId, menuItems);

		this.context = context;
		this.menuItems = menuItems;
		this.rowResourceId = textViewResourceId;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(rowResourceId, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.mockiMenuIcon);
		TextView textView = (TextView) rowView.findViewById(R.id.mockiMenuName);

		int id = menuItems.get(position).getId();
		textView.setText(MockiFactory.MockiMenu.getItemById(id).name);
		
		int iconResId = MockiFactory.MockiMenu.getItemById(id).getIconResId();
		imageView.setImageResource(iconResId);
		
		return rowView;

	}
}
