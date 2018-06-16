package com.example.dyslexia;


import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity 
{
	private Button setipaddr;
	private EditText ip;
	private RadioGroup loginStatus;
	private RadioButton clickedButt;
	static String ipaddr;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ip=(EditText)findViewById(R.id.ipaddr);
	        loginStatus=(RadioGroup) findViewById(R.id.loginregbutton);
	        setipaddr=(Button) findViewById(R.id.connectbutton);
	        if(android.os.Build.VERSION.SDK_INT > 9)
		    {
		    	 StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    	 StrictMode.setThreadPolicy(policy);
		    }
	        setipaddr.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					try {

						ipaddr=ip.getText().toString().toString();
						int id=loginStatus.getCheckedRadioButtonId();
						clickedButt=(RadioButton)findViewById(id);
						SetToast(getApplicationContext(), ""+clickedButt.getText());
						String login=(String) clickedButt.getText();
//						Toast.makeText(getApplicationContext(), "the login str is "+login, Toast.LENGTH_LONG);
						SetToast(getApplicationContext(), "the string is "+login);
						if(!(login.equalsIgnoreCase(null)||ipaddr.equalsIgnoreCase(null)))
						{
							if(login.equalsIgnoreCase("Staff Login"))
					        {
					        	Intent intent = new Intent(getApplicationContext(),LoginValidation.class);
								Bundle userinfo = new Bundle();
								userinfo.putString("action","Staff Login");
								intent.putExtras(userinfo);
								startActivity(intent);
					        }
					        else if(login.equalsIgnoreCase("Child Login"))
					        {
					        	Intent intent = new Intent(getApplicationContext(),LoginValidation.class);
								Bundle userinfo = new Bundle();
								userinfo.putString("action","Child Login");
								intent.putExtras(userinfo);
								startActivity(intent);
					        }
					        else if(login.equalsIgnoreCase("Child Registration"))
					        {
					        	Intent intent = new Intent(getApplicationContext(),UserRegister.class);
					        	Bundle userinfo = new Bundle();
								userinfo.putString("action","Child Registration");
								intent.putExtras(userinfo);
								startActivity(intent);
					        }
					        else //if(login.equalsIgnoreCase("Staff Registration"))
					        {
					        	Intent intent = new Intent(getApplicationContext(),UserRegister.class);
					        	Bundle userinfo = new Bundle();
								userinfo.putString("action","Staff Registration");
								intent.putExtras(userinfo);
								startActivity(intent);
					        }
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Enter the all fields correctly", Toast.LENGTH_LONG).show();
						}
					}
					catch(Exception e)
					{
						SetToast(getApplicationContext(), "on click "+e);
					}
					
				}
			});
		}
		catch(Exception e)
		{
//			SetToast(getApplicationContext(), "main Activity "+e);
		}
	}
	public static void SetToast(Context context,String msg)
	{
		Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
