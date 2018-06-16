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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TestResult extends Activity
{
	int mark=0,firstmark=0,secondmark=0;
	private String letter="";
	RelativeLayout rl;
	String uname="";
	SharedPreferences pref = null;
	ImageButton nextpage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.result);
	        nextpage=(ImageButton) findViewById(R.id.nextpage);
	        Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        mark= Integer.parseInt(userinfo.getString("mark"));
	        firstmark=Integer.parseInt(userinfo.getString("first"));
	        secondmark=Integer.parseInt(userinfo.getString("second"));
	        letter=userinfo.getString("letter");
	        Toast.makeText(getApplicationContext(), "the mark is "+mark, Toast.LENGTH_LONG).show();
	        rl = (RelativeLayout)findViewById(R.id.resultRL);
	        pref = getSharedPreferences("MyPref",Context.MODE_PRIVATE);
	        uname=pref.getString("uname", null);
//	        MediaPlayer mp=new MediaPlayer();
//    		mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Dyslexia/congratz.mp3");  		
//            mp.prepare();  
//            mp.start();  
//            Thread.sleep(5000);
//            mp.stop();
	        if(firstmark<3||secondmark<3)
	        {
	        	rl.setBackgroundResource(R.drawable.fail);
	        }
//	        else
//	        {
		        HttpClient httpClient = new DefaultHttpClient();
		        String url="";
				if(letter.equalsIgnoreCase("PQ"))
				{
					url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=changestatus&level=level1&username="+uname+"&p="+firstmark+"&q="+secondmark;
				}
				else if(letter.equalsIgnoreCase("BD"))
				{
					url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=changestatus&level=level2&username="+uname+"&b="+firstmark+"&d="+secondmark;
				}
				else
				{
					url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=changestatus&level=level3&username="+uname+"&m="+firstmark+"&w="+secondmark;
				}
				url=url.replace(" ", "%20");
				HttpGet getRequest=new HttpGet(url);
				HttpResponse res= httpClient.execute(getRequest);
//	        }
	        nextpage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					if(firstmark<3||secondmark<3)
			        {
						if(letter.equalsIgnoreCase("PQ"))
						{
							Intent inttt = new Intent(getApplicationContext(),PractiseSession.class);
							Bundle userinf = new Bundle();
							userinf.putString("letter","P");
							inttt.putExtras(userinf);
							startActivity(inttt);
							
						}
						else if(letter.equalsIgnoreCase("BD"))
						{
							Intent inttt = new Intent(getApplicationContext(),PractiseSession.class);
							Bundle userinf = new Bundle();
							userinf.putString("letter","B");
							inttt.putExtras(userinf);
							startActivity(inttt);
						}
						else
						{
							Intent inttt = new Intent(getApplicationContext(),PractiseSession.class);
							Bundle userinf = new Bundle();
							userinf.putString("letter","M");
							inttt.putExtras(userinf);
							startActivity(inttt);
						}
			        }
					else
					{
						if(letter.equalsIgnoreCase("PQ"))
						{
							Intent inttt = new Intent(getApplicationContext(),LevelChecker.class);
							Bundle userinf = new Bundle();
							userinf.putString("level","level1");
							inttt.putExtras(userinf);
							startActivity(inttt);
							
						}
						else if(letter.equalsIgnoreCase("BD"))
						{
							Intent inttt = new Intent(getApplicationContext(),LevelChecker.class);
							Bundle userinf = new Bundle();
							userinf.putString("level","level2");
							inttt.putExtras(userinf);
							startActivity(inttt);
						}
						else
						{
							Intent inttt = new Intent(getApplicationContext(),LevelChecker.class);
							Bundle userinf = new Bundle();
							userinf.putString("level","level3");
							inttt.putExtras(userinf);
							startActivity(inttt);
						}
					}
					
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}
}
