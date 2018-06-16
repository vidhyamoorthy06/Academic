package com.example.dyslexia;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TestingSession extends Activity
{
	private TextToSpeech textToSpeech;
	Random rand=new Random();
	ArrayList<TextView> text=new ArrayList<TextView>();
	char c[]=new char[10];
	int randomTexFixer=0,count=0,correctcount=0;
	String letter="";
	ArrayList randomchar=new ArrayList();
	int Firststlimitbool=0,Secondndlimitbool=0;
	String testingstr="",sampleString="";
	TextView middle,midleft1,midleft2,midright1,midright2;
	ImageButton start;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.testing);
	        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() 
	        {
		           
		           public void onInit(int status) {
		              if(status != TextToSpeech.ERROR) {
		            	  textToSpeech.setLanguage(Locale.UK);
		              }
		           }
	        });
	        Intent intent = getIntent();
	        Bundle userinfo = intent.getExtras();
	        letter= userinfo.getString("letter");
	        for(int i=0;i<letter.length();i++)
	        {
	        	c[i]=letter.charAt(i);
//	        	letterList.add();
	        }    
	        start=(ImageButton)findViewById(R.id.start);
	        middle=(TextView)findViewById(R.id.middle);
	        midleft1=(TextView)findViewById(R.id.middleleft1);
	        midleft2=(TextView)findViewById(R.id.middleleft2);
	        midright1=(TextView)findViewById(R.id.middleright1);
	        midright2=(TextView)findViewById(R.id.middleright2);
	        text.add(middle);
	        text.add(midleft1);
	        text.add(midleft2);
	        text.add(midright1);
	        text.add(midright2);
	        randomchar.clear();
			suffleKeypadGenereate();
	        RandomTester();
	        Thread.sleep(500);
	        Toast.makeText(getApplicationContext(), "Random Tester call success "+testingstr, Toast.LENGTH_LONG).show();
	        start.setOnClickListener(new OnClickListener() 
			 {						
					@Override
					public void onClick(View v) 
					{
						TestQuestion();
					}
			});
	        middle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					UserAnswerChecker(middle.getText().toString());
				}
			});
	        midleft1.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								UserAnswerChecker(midleft1.getText().toString());
							}
						});
	        midleft2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					UserAnswerChecker(midleft2.getText().toString());
				}
			});
	        midright1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					UserAnswerChecker(midright1.getText().toString());
				}
			});
			midright2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					UserAnswerChecker(midright2.getText().toString());
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_LONG).show();
		}
	}
	public void TestQuestion()
	{
		try
		{
			speak("the letter is ");
			Thread.sleep(500);
			speak(testingstr);
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}
	public void speak(String str)
	{
		textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
	}
	public void UserAnswerChecker(String useranswer)
	{
		if(count<10)
		{
			count++;
			if(useranswer.equalsIgnoreCase(testingstr))
			{
				try
				{
					speak("correct. congratulations ");
					if(testingstr.equalsIgnoreCase(String.valueOf(c[0])))
					{
						Firststlimitbool++;
					}
					else
					{
						Secondndlimitbool++;
					}
					Thread.sleep(2000);
					RandomTester();
					Thread.sleep(2000);
					TestQuestion();
					correctcount++;
//					Toast.makeText(getApplicationContext(), "the first value is "+Firststlimitbool+" and second vale is "+Secondndlimitbool+" ARRAY VALUE IS "+String.valueOf(c[0]), Toast.LENGTH_LONG).show();
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "the if "+e, Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				try
				{
					speak("sorry, your answer is wrong. try again");
					Thread.sleep(2000);
					RandomTester();
					Thread.sleep(2000);
					TestQuestion();
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "the else "+e, Toast.LENGTH_LONG).show();
				}
			}
		}
		else
		{
			Intent inttt = new Intent(getApplicationContext(),TestResult.class);
			Bundle userinf = new Bundle();
			userinf.putString("mark",""+correctcount);
			userinf.putString("first",""+Firststlimitbool);
			userinf.putString("second",""+Secondndlimitbool);
			userinf.putString("letter", letter);
			inttt.putExtras(userinf);
			startActivity(inttt);
		}
	}
	public void RandomTester()
	{
		try
		{
//			int testingint=rand.nextInt(2);
			if(!randomchar.isEmpty())
			{
		        testingstr=String.valueOf(randomchar.get(0));
		        Toast.makeText(getApplicationContext(), "randomtestor call "+count, Toast.LENGTH_LONG).show();
		        if(testingstr.equalsIgnoreCase(String.valueOf(c[0])))
		        {
		        	sampleString=String.valueOf(c[1]);
		        }
		        else
		        {
		        	sampleString=String.valueOf(c[0]);
		        }
		        randomTexFixer=rand.nextInt(5);
		        for(int i=0;i<text.size();i++)
		        {
		        	if(i==randomTexFixer)
		        	{
		        		text.get(i).setText(testingstr);
		        	}
		        	else
		        	{
		        		text.get(i).setText(sampleString);
		        	}
		        }
		        randomchar.remove(0);
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "randomtestor "+e, Toast.LENGTH_LONG).show();
		}
	}
	public void suffleKeypadGenereate()
	{
		char array[]={c[0],c[0],c[0],c[0],c[0],c[1],c[1],c[1],c[1],c[1]};
		try
		{
			for (int i=0; i<array.length; i++) 
			{
			    int randomPosition = rand.nextInt(array.length);
			    char temp = array[i];
			    array[i] = array[randomPosition];
			    array[randomPosition] = temp;
			}
			for (int i=0; i<array.length; i++) 
			{
				randomchar.add(array[i]);
			}
			Toast.makeText(getApplicationContext(), "the "+randomchar.toString(), Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "the else "+e, Toast.LENGTH_LONG).show();
		}
	}
}
