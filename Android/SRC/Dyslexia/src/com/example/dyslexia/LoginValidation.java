package com.example.dyslexia;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginValidation extends ActionBarActivity 
{
	private EditText uname,pass;
	private Button login;
	private TextView userlogin;
	String action="";
	SharedPreferences pref = null;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        if(android.os.Build.VERSION.SDK_INT > 9)
		    {
		    	 StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    	 StrictMode.setThreadPolicy(policy);
		    }
	        Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        action= userinfo.getString("action");
	        pref = getSharedPreferences("MyPref",
					Context.MODE_PRIVATE);
	        userlogin=(TextView) findViewById(R.id.userlogin);
	        userlogin.setText(action);
	        uname=(EditText)findViewById(R.id.uname);
	        pass=(EditText)findViewById(R.id.pass);
	        login=(Button)findViewById(R.id.login);
	        login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					try
					{
						Toast.makeText(getApplicationContext(),"the action is "+action,Toast.LENGTH_LONG);
						HttpClient httpClient = new DefaultHttpClient();
						String url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/Login?action="+action+"&username="+uname.getText().toString().trim()+"&password="+pass.getText().toString().trim();
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
							SharedPreferences.Editor editor = pref.edit();		         
					         editor.putString("uname",uname.getText().toString().trim());
					         editor.commit();
							if(action.equalsIgnoreCase("Staff Login"))
							{
								Intent intent = new Intent(getApplicationContext(),ChildMarkDetails.class);
//								Bundle userinfo = new Bundle();
//								userinfo.putString("action","Admin Login");
//								intent.putExtras(userinfo);
								startActivity(intent);
							}
							else if(action.equalsIgnoreCase("Child Login"))
							{
								Intent intent = new Intent(getApplicationContext(),LevelChecker.class);
								Bundle userinfo = new Bundle();
								userinfo.putString("level","nothing");
								intent.putExtras(userinfo);
								startActivity(intent);
								
							}
						}
					}
					catch(Exception e)
					{
						MainActivity.SetToast(getApplicationContext(), "login onclick exception"+e);
					}
				}
			});
	        
		}
		catch(Exception e)
		{
			MainActivity.SetToast(getApplicationContext(), "login exception"+e);
		}
	}
}
