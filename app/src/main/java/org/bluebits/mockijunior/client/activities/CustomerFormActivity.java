/**
 * 
 */
package org.bluebits.mockijunior.client.activities;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.bluebits.mockijunior.ApplicationContext;
import org.bluebits.mockijunior.MockiJunior;
import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.adapters.StoreListAdapter;
import org.bluebits.mockijunior.client.model.Store;
import org.bluebits.mockijunior.client.model.UserGeneratedData;
import org.bluebits.mockijunior.client.utils.ActivityLoaderUtil;
import org.bluebits.mockijunior.client.utils.DataSynchronizer;
import org.bluebits.mockijunior.client.utils.OrderCollectionUtil;

/**
 * @author satyajit
 *
 */

public class CustomerFormActivity extends Activity {
	private ApplicationContext context;
	private static final int TIME_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oc_customer_form);
		context = (ApplicationContext) getApplicationContext();
		
		addUiEventkListener();
		getOverflowMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_oc_customer_form, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		try {
			UserGeneratedData ugd = context.getUserGeneratedData();
			
			final TextView lblStore = (TextView) findViewById(R.id.lblStore);
			final Spinner spnSession = (Spinner) findViewById(R.id.spnrSession);
			final TextView lblDeliveryDate = (TextView) findViewById(R.id.lblDatePicker);
			final EditText txtRemarks = (EditText) findViewById(R.id.txtRemarks);

			if (ugd != null) {
				lblStore.setTag(ugd.getStoreId());
				lblStore.setText(ugd.getStoreName());
				spnSession.setSelection(ugd.getSessionId());
				lblDeliveryDate.setText(ugd.getDeliveryDate());
				txtRemarks.setText(ugd.getRemarks());
			} else {
				ugd = new UserGeneratedData();
				context.setUserGeneratedData(ugd);
			}
		} catch (Exception e) {
			Log.e("ERROR", "Error resuming customer info to CustomerFormActivity");
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		final TextView lblStore = (TextView) findViewById(R.id.lblStore);
		final Spinner  spnSession = (Spinner) findViewById(R.id.spnrSession);
		final TextView lblDeliveryDate = (TextView) findViewById(R.id.lblDatePicker);
		final EditText txtRemarks = (EditText) findViewById(R.id.txtRemarks);
		
		try {
			UserGeneratedData ugd = context.getUserGeneratedData();
			if(ugd == null) {
				ugd = new UserGeneratedData();
			}
			
			String storeId = String.valueOf(lblStore.getTag());
			ugd.setStoreId(Integer.valueOf("null".equals(storeId) || "".equals(storeId) ? "0" : storeId));
			ugd.setStoreName(lblStore.getText().toString());
			ugd.setSessionId(spnSession.getSelectedItemPosition());
			ugd.setDeliveryDate(lblDeliveryDate.getText().toString());
			ugd.setRemarks(txtRemarks.getText().toString());
			
			context.setUserGeneratedData(ugd);
		} catch (Exception e) {
			Log.e("ERROR", "Error saving customer info to ApplicationContext");
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_product_list:
			ActivityLoaderUtil.load(this, ProductListActivity.class);
			return true;
		case R.id.action_save_order:
			return true;
		case R.id.action_send_order:
			sendOrder();
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_sync:
			new DataSynchronizer(this).execute();
			return true;
		case R.id.action_signout:
			context.clearContext();
			ActivityLoaderUtil.load(this, MockiJunior.class);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void sendOrder() {
		try {
			OrderCollectionUtil.sendOrder(this);
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void addUiEventkListener() {
		final TextView lblStore = (TextView) findViewById(R.id.lblStore);
		lblStore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showStoreList();
			}
		});
		
		final TextView lblDatePicker = (TextView) findViewById(R.id.lblDatePicker);
		lblDatePicker.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		final Spinner spnSession = (Spinner) findViewById(R.id.spnrSession);
		spnSession.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int s = spnSession.getSelectedItemPosition();
				context.getUserGeneratedData().setSessionId(s);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				context.getUserGeneratedData().setSessionId(0);// 0-Morning
			}
		});
		
		final EditText txtRemarks = (EditText) findViewById(R.id.txtRemarks);
		txtRemarks.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				context.getUserGeneratedData().setRemarks(txtRemarks.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			Calendar c = Calendar.getInstance();
			int mYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH);
			int mDay = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dialog = new DatePickerDialog(this,
					datePickerListener, mYear, mMonth, mDay);

			return dialog;
		}

		return null;
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener = 
			new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker picker, int year, int month, int day) {
			TextView target = (TextView) findViewById(R.id.lblDatePicker);
			String date = String.valueOf(day) + "-" 
			            + String.valueOf(month) + "-" 
					    + String.valueOf(year);

			target.setText(date);
			target.setTag(date);
			context.getUserGeneratedData().setDeliveryDate(date);
		}
	};
	
	private class ListItemClickListener implements OnItemClickListener {
		private AlertDialog dialog;

		public ListItemClickListener(AlertDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			TextView textViewItem = ((TextView) view.findViewById(R.id.store));
			String listItemText = textViewItem.getText().toString();
			String listItemId = TextUtils.isEmpty(String.valueOf(textViewItem.getTag())) ? "" : textViewItem.getTag().toString();

			TextView target = (TextView) findViewById(R.id.lblStore);
			target.setText(listItemText);
			target.setTag(listItemId);
			
			context.getUserGeneratedData().setStoreId(Integer.valueOf(listItemId));
			context.getUserGeneratedData().setStoreName(listItemText);
			
			this.dialog.dismiss();
		}
	}
	
	private void showStoreList() {
		List<Store> stores = context.getAppData().getStores();
		if(stores == null || stores.size() == 0) {
			Toast.makeText(this, "Store list empty.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		StoreListAdapter adapter = new StoreListAdapter(this,
				R.layout.layout_oc_store_list, stores);

		ListView listViewItems = new ListView(this);
		listViewItems.setAdapter(adapter);

		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setView(listViewItems).setTitle("Store List")
				.setIcon(R.drawable.ic_store);

		builder.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					TextView lblStore = (TextView) findViewById(R.id.lblStore);
					lblStore.setText(getResources().getString(R.string.oc_select_store));
					lblStore.setTag(0);
					context.getUserGeneratedData().setStoreId(0);
					dialog.dismiss();
				}
			});

		AlertDialog dialog = builder.show();
		listViewItems.setOnItemClickListener(new ListItemClickListener(dialog));
	}
	
	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
