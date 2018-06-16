package com.example.dyslexia;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class LevelChecker extends Activity
{
	ImageButton level1,level2,level3;
	String uname="";
	SharedPreferences pref = null;
	String level="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.levels);
	        level1=(ImageButton) findViewById(R.id.level1);
	        level2=(ImageButton)findViewById(R.id.level2);
	        level3=(ImageButton) findViewById(R.id.level3);
	        pref = getSharedPreferences("MyPref",Context.MODE_PRIVATE);
	        Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        level= userinfo.getString("level");
	        uname=pref.getString("uname", null);
	        if(level.equalsIgnoreCase("nothing"))
	        {
	        	HttpClient httpClient = new DefaultHttpClient();
				String url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=checkstatus&username="+uname;
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
				Toast.makeText(getApplicationContext(), "the str is "+str, Toast.LENGTH_LONG).show();
				if(str.equalsIgnoreCase("level1"))
				{
					level2.setVisibility(View.VISIBLE);
				}
				else if(str.equalsIgnoreCase("level2"))
				{
					level2.setVisibility(View.VISIBLE);
		        	level3.setVisibility(View.VISIBLE);
				}
				else if(str.equalsIgnoreCase("level3"))
				{
					level2.setVisibility(View.VISIBLE);
		        	level3.setVisibility(View.VISIBLE);
				}
	        }
	        else if(level.equalsIgnoreCase("level1"))
	        {
	        	level2.setVisibility(View.VISIBLE);
	        }
	        else
	        {
	        	level2.setVisibility(View.VISIBLE);
	        	level3.setVisibility(View.VISIBLE);
	        }
	        level1.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),PractiseSession.class);
					Bundle userinfo = new Bundle();
					userinfo.putString("letter","P");
					intent.putExtras(userinfo);
					startActivity(intent);
					
				}
			});
	        level2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),PractiseSession.class);
					Bundle userinfo = new Bundle();
					userinfo.putString("letter","B");
					intent.putExtras(userinfo);
					startActivity(intent);
				}
			});
			level3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),PractiseSession.class);
					Bundle userinfo = new Bundle();
					userinfo.putString("letter","M");
					intent.putExtras(userinfo);
					startActivity(intent);
					
				}
			});
		}
		catch(Exception e)
		{
				Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}
}
