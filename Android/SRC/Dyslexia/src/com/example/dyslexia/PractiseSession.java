package com.example.dyslexia;

import java.util.ArrayList;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PractiseSession extends Activity implements OnGesturePerformedListener
{
	private GestureLibrary gestureLib;
	private TextView sessionText;
	private ImageButton next,start,voice,gesture;
	private TextToSpeech textToSpeech;
	protected static final int RESULT_SPEECH = 1;
	private int count=0;
	Boolean gesutrebool=false;
	String letter="";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
		    View inflate = getLayoutInflater().inflate(R.layout.practicesession, null);
		    gestureOverlayView.addView(inflate);
		    gestureOverlayView.setGestureColor(Color.RED);
		    gestureOverlayView.addOnGesturePerformedListener( this);
		    gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
		    if (!gestureLib.load()) 
		    {
		    	Log.e("GestureSample", "Gesture library was not loaded…");	
		    	finish();
		    }
		    setContentView(gestureOverlayView);
		    Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        letter= userinfo.getString("letter");
		    voice=(ImageButton) findViewById(R.id.voice);
		    gesture=(ImageButton) findViewById(R.id.gesture);
		    next=(ImageButton) findViewById(R.id.next);
		    start=(ImageButton)findViewById(R.id.start);
		    sessionText=(TextView) findViewById(R.id.practicetext);
		    sessionText.setText(letter);
		    textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
		           
		           public void onInit(int status) {
		              if(status != TextToSpeech.ERROR) {
		            	  textToSpeech.setLanguage(Locale.UK);
		              }
		           }
		        });
		    voice.setOnClickListener(new OnClickListener() 
		    {	
				@Override
				public void onClick(View v) 
				{
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
					startActivityForResult(intent, RESULT_SPEECH);
//					gesture.setVisibility(View.VISIBLE);
				}
			});
		    gesture.setOnClickListener(new OnClickListener() 
		    {	
				@Override
				public void onClick(View v) 
				{
					gesutrebool=true;
					count=0;
				}
			});
		    next.setOnClickListener(new OnClickListener() 
		    {	
				@Override
				public void onClick(View v) 
				{
					Intent inttt = new Intent(getApplicationContext(),PractiseSession.class);
					Bundle userinf = new Bundle();
					if(letter.equalsIgnoreCase("P"))
					{
						userinf.putString("letter","Q");
					}
					else if(letter.equalsIgnoreCase("Q"))
					{
						inttt = new Intent(getApplicationContext(),TestingSession.class);
						userinf.putString("letter","PQ");
					}
					else if(letter.equalsIgnoreCase("M"))
					{
						userinf.putString("letter","W");
					}
					else if(letter.equalsIgnoreCase("W"))
					{
						inttt = new Intent(getApplicationContext(),TestingSession.class);
						userinf.putString("letter","MW");
					}
					else if(letter.equalsIgnoreCase("B"))
					{
						userinf.putString("letter","D");
					}
					else
					{
						inttt = new Intent(getApplicationContext(),TestingSession.class);
						userinf.putString("letter","BD");
					}
						
					inttt.putExtras(userinf);
					startActivity(inttt);
				}
			});
		    start.setOnClickListener(new OnClickListener() 
		    {	
				@Override
				public void onClick(View v) 
				{
					try
					{
//						count=0;
//						while(count<4)
//						{		
//							textToSpeech.speak(sessionText.getText().toString().trim(), TextToSpeech.QUEUE_FLUSH, null);
								speak(sessionText.getText().toString().trim());
//								count++;
								Thread.sleep(3000);		
//						}
//						count=0;
						voice.setVisibility(View.VISIBLE);
						speak("click recognize voice button");
					}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		catch(Exception e)
		{
			MainActivity.SetToast(getApplicationContext(), "practice session"+e);
		}
	}
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		try
		{
			Boolean tempbool=true;
			ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
		    for (Prediction prediction : predictions) 
		    {
		    	 if (gesutrebool&&tempbool)
		    	 {
		    		 tempbool=false;
				    if(prediction.name.toString().equalsIgnoreCase(sessionText.getText().toString().trim()))
				    {
//				    	Toast.makeText(getApplicationContext(), ""+count, Toast.LENGTH_LONG).show();
//				    	count++;
//				    	if(count>3)
//				    	{
				    		next.setVisibility(View.VISIBLE);
				    		gesutrebool=false;
				    		MediaPlayer mp=new MediaPlayer();
				    		Toast.makeText(getApplicationContext(), "media player call", Toast.LENGTH_LONG).show();
				    		mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Dyslexia/congratz.mp3");  				            
				            mp.prepare();  
				            mp.start();  
				            Thread.sleep(5000);
				            mp.stop();
				            speak("click next button");
//				    	}
//				    	else
//				    	{
//				    		speak("draw again");
//				    	}
				    }
				    else
				    {
//				    	count=0;
				    	speak("gesture does not match. listen tutorial again");
				    	voice.setVisibility(View.INVISIBLE);
				    	this.gesture.setVisibility(View.INVISIBLE);
				    }
		    	 }
//			     else
//			     {}
		    }
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "in on gesturemethod "+e, Toast.LENGTH_LONG).show();
		}
		
	}
	public void speak(String str)
	{
		textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_LONG).show();
		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {
				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if(!text.get(0).equals(""))
				{
					try
					{
						String invoice=text.get(0).toString();
						if(invoice.equalsIgnoreCase(sessionText.getText().toString().trim()))
						{
//							count++;
//							Toast.makeText(getApplicationContext(), "count "+count, Toast.LENGTH_LONG).show();
//							if(count>2)
//							{
								speak("click recognize gesture button");
								gesture.setVisibility(View.VISIBLE);
//							}
//							else
//							{
//								speak("repeat letter again");
//								Thread.sleep(3000);
//								Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//								intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//								startActivityForResult(intent, RESULT_SPEECH);				
//							}
						}
						else
						{
//							count=0;
							voice.setVisibility(View.INVISIBLE);
							gesture.setVisibility(View.INVISIBLE);
							speak("Repeat the tutorial again");
						}
					}catch (Exception e) {
//						Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
//					Toast.makeText(getApplicationContext(), "else block",Toast.LENGTH_SHORT).show();
				}
				
			}
			break;
		}

		}
	}
}
