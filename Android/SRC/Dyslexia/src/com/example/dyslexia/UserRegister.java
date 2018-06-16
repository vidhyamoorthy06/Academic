package com.example.dyslexia;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserRegister extends ActionBarActivity
{
	private EditText uname,pass,repass,dob;
	private CheckBox policy;
	private Button register;
	String action="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.registration);
	        Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        action= userinfo.getString("action");
	        uname=(EditText)findViewById(R.id.reguname);
	        pass=(EditText) findViewById(R.id.regpass);
	        repass=(EditText) findViewById(R.id.regrepass);
	        dob=(EditText) findViewById( R.id.regdob);
	        register=(Button) findViewById(R.id.register);
	        policy=(CheckBox) findViewById(R.id.acceptpolicy);
	        register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					try
					{
						if(policy.isChecked())
						{
							if((pass.getText().toString().trim()).equalsIgnoreCase(repass.getText().toString().trim()))
							{
								HttpClient httpClient = new DefaultHttpClient();
								String url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/Login?action="+action.trim()+"&username="+uname.getText().toString().trim()+"&password="+pass.getText().toString().trim()+"&dob="+dob.getText().toString().trim();
								MainActivity.SetToast(getApplicationContext(), url);
								url=url.replace(" ", "%20");
								HttpGet getRequest=new HttpGet(url);
								HttpResponse res= httpClient.execute(getRequest);
								InputStream is= res.getEntity().getContent();
						        byte[] b=null;
						        ByteArrayOutputStream bos = new ByteArrayOutputStream();
						        int ch;
								while((ch = is.read()) != -1)
								{
									bos.write(ch);
								}
								b=bos.toByteArray();
								String str = new String(b).replace("\n","").trim();
								if(str.equalsIgnoreCase("success"))
								{
									MainActivity.SetToast(getApplicationContext(), "Registration successfull");
									Intent intent = new Intent(getApplicationContext(),LoginValidation.class);
									Bundle userinfo = new Bundle();
									if(action.equalsIgnoreCase("Staff Registration"))
									{
										userinfo.putString("action","Staff Login");
									}
									else
									{
										userinfo.putString("action","Child Login");
									}
									intent.putExtras(userinfo);
									startActivity(intent);
								}
								else
								{
									MainActivity.SetToast(getApplicationContext(), "Registration Failed... Try Again !!!");
								}
							}
							else
							{
								MainActivity.SetToast(getApplicationContext(), "Password Is Wrong");
							}
						}
						else
						{
							MainActivity.SetToast(getApplicationContext(), "Check The Term & Conditions");
						}
					}
					catch(Exception e)
					{
						MainActivity.SetToast(getApplicationContext(), "registration onclick exception"+e);
					}		
				}
			});
		}
		catch(Exception e)
		{
			MainActivity.SetToast(getApplicationContext(), "registration exception"+e);
		}
	}
}
