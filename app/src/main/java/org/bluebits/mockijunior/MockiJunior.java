package org.bluebits.mockijunior;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bluebits.mockijunior.client.activities.MockiMenuActivity;
import org.bluebits.mockijunior.client.model.Customer;
import org.bluebits.mockijunior.client.utils.ActivityLoaderUtil;
import org.bluebits.mockijunior.client.utils.LoginUtils;
import org.bluebits.mockijunior.db.DBController;

public class MockiJunior extends Activity {
	
	ApplicationContext context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = (ApplicationContext) getApplicationContext();

		setContentView(R.layout.layout_login);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new LoginClickListener());
		
		Button btnQuit = (Button) findViewById(R.id.btnQuit);
		btnQuit.setOnClickListener(new QuitClickListener());
		
		/*ApplicationData appData = context.getAppData();
		if(appData.hasRegisteredUser()) {
			setContentView(R.layout.layout_login);
			
			Button btnLogin = (Button) findViewById(R.id.btnLogin);
			btnLogin.setOnClickListener(new LoginClickListener());
		} else {
			setContentView(R.layout.layout_customer_signup);
			
			Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
			btnSignUp.setOnClickListener(new SignupClickListener());
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mocki_junior, menu);
		return true;
	}
	
	private class SignupClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			String firstName = ((EditText) findViewById(R.id.txtFirstName)).getText().toString();
			String lastName = ((EditText) findViewById(R.id.txtLastName)).getText().toString();
			String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
			String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
			String confirmPassword = ((EditText) findViewById(R.id.txtConfirmPassword)).getText().toString();
			
			if(!password.equals(confirmPassword)) {
				Toast.makeText(MockiJunior.this, "Both Password must match.", Toast.LENGTH_LONG).show();
				return;
			}
			
			Customer customer = new Customer(firstName, lastName, email, password);
			
			DBController dbController = new DBController(MockiJunior.this);
			dbController.addCustomer(customer);
			
			if(customer.getId() > 0) {
				context.getAppData().setHasRegisteredUser(true);
				context.setLoggedInUser(email);
				ActivityLoaderUtil.load(MockiJunior.this, MockiMenuActivity.class);
			} else {
				Toast.makeText(MockiJunior.this, "Registration failed, Please try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private class QuitClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			MockiJunior.this.finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}
	
	private class LoginClickListener implements OnClickListener {
		
		@Override
		public void onClick(View view) {
			final EditText txtUserName = (EditText) findViewById(R.id.txtUserName);
			final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
			
			String userName = txtUserName.getText().toString();
			String password = txtPassword.getText().toString();
			if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
				Toast.makeText(MockiJunior.this, "Please enter both user name and password to login.", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (LoginUtils.authenticate(userName, password)) {
				context.setLoggedInUser(userName);
				ActivityLoaderUtil.load(MockiJunior.this, MockiMenuActivity.class);
			}
		}
	}
}
