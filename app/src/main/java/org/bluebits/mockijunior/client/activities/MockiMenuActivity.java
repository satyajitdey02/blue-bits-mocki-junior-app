/**
 * 
 */
package org.bluebits.mockijunior.client.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.bluebits.mockijunior.ApplicationContext;
import org.bluebits.mockijunior.MockiJunior;
import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.adapters.MockiMenuAdapter;
import org.bluebits.mockijunior.client.factory.MockiFactory;
import org.bluebits.mockijunior.client.utils.ActivityLoaderUtil;
import org.bluebits.mockijunior.client.utils.DataSynchronizer;


/**
 * @author satyajit
 * 
 */
public class MockiMenuActivity extends Activity {
	
	/*enum Menu {
	    ORDER_COLLECTION(0),
	    DOCTORS_CALL(1),
	    FIELD_SURVEY(2),
	    REVIEW_MANAGEMENT(3),
	    SYNCHRONIZATION(4),
	    APP_SETTINGS(5),
	    ABOUT_BLUEBITS(6),
	    SIGNOUT(7);
	    
	    private int  menuIndex;
	    
		private Menu(int menuIndex) {
			this.menuIndex = menuIndex;
		}
	 
		public int getMenuIndex() {
			return menuIndex;
		}
	}*/
	
	private ApplicationContext context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mocki_menu);
		
		context = (ApplicationContext) getApplicationContext();
		
		ListView listView = (ListView) findViewById(R.id.listView);
		MockiMenuAdapter adapter = new MockiMenuAdapter(this,
				R.layout.layout_mocki_menu_item,
				MockiFactory.MockiMenu.getMenuItems());

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListItemClickListener(this));
	}

	private class ListItemClickListener implements OnItemClickListener {

		private Context ctx;

		public ListItemClickListener(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case 0:
				ActivityLoaderUtil.load(this.ctx, CustomerFormActivity.class);
				break;
			
			case 4:
				new DataSynchronizer(MockiMenuActivity.this).execute();
				break;
				
			case 7:
				context.clearContext();
				ActivityLoaderUtil.load(this.ctx, MockiJunior.class);
				finish();
				break;	

			default:
				System.out.println("No Action!");
				break;
			}
		}
	}
}
