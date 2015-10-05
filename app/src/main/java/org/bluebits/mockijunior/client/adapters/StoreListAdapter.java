/**
 * 
 */
package org.bluebits.mockijunior.client.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.model.Store;

public class StoreListAdapter extends ArrayAdapter<Store> {

	private Context context;
	private int resourceId;
	private List<Store> stores;

	public StoreListAdapter(Context context, int resourceId, List<Store> stores) {
		super(context, resourceId, stores);

		this.resourceId = resourceId;
		this.context = context;
		this.stores = stores;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(resourceId, parent, false);
		}

		Store store = stores.get(position);

		TextView textViewItem = (TextView) convertView.findViewById(R.id.store);
		textViewItem.setText(store.getName());
		textViewItem.setTag(Integer.valueOf(store.getId()));

		return convertView;
	}
}
