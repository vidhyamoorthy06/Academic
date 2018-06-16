package com.example.dyslexia;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class ChildMarkDetails extends Activity
{
	private String[] mMonth = new String[] {
			"P", "Q" , "B", "D", "M", "W"};	
	ArrayList<String> strongchild=new ArrayList<String>();
	ArrayList<String> weakchild=new ArrayList<String>();
	SharedPreferences pref = null;
	TableLayout tl;
	TableRow tr;
	TextView companyTV,valueTV;
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		try
		{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.table);
	        pref = getSharedPreferences("MyPref",Context.MODE_PRIVATE);
	        String uname=pref.getString("uname", null);
	        HttpClient httpClient = new DefaultHttpClient();
			String url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=childdetails&username="+uname;
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
			Toast.makeText(getApplicationContext(), "the str value is "+str, Toast.LENGTH_LONG).show();
			StringTokenizer outputtok=new StringTokenizer(str, "@");
			while(outputtok.hasMoreTokens())
			{
				StringTokenizer childstatus=new StringTokenizer(outputtok.nextToken(), "$");
				String username=childstatus.nextToken();
				if(childstatus.nextToken().equalsIgnoreCase("weak"))
				{
					weakchild.add(username);
				}
				else
				{
					strongchild.add(username);
				}
				
			}
	        tl = (TableLayout) findViewById(R.id.maintable);
	        addHeaders();
	        addData();
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
    }
 
    /** This function add the headers to the table **/
    public void addHeaders()
    {
    	try
    	{
	         /** Create a TableRow dynamically **/
	        tr = new TableRow(this);
	        tr.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	 
	        /** Creating a TextView to add to the row **/
	        TextView companyTV = new TextView(this);
	        companyTV.setText("Strong Child's");
	        companyTV.setTextColor(Color.GRAY);
	        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	        companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	        companyTV.setPadding(5, 5, 5, 0);
	        tr.addView(companyTV);  // Adding textView to tablerow.
	 
	        /** Creating another textview **/
	        TextView valueTV = new TextView(this);
	        valueTV.setText("Weak Child's");
	        valueTV.setTextColor(Color.GRAY);
	        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	        valueTV.setPadding(5, 5, 5, 0);
	        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	        tr.addView(valueTV); // Adding textView to tablerow.
	 
	        // Add the TableRow to the TableLayout
	        tl.addView(tr, new TableLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	 
	        // we are adding two textviews for the divider because we have two columns
	        tr = new TableRow(this);
	        tr.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	 
	        /** Creating another textview **/
	        TextView divider = new TextView(this);
	        divider.setText("-----------------");
	        divider.setTextColor(Color.GREEN);
	        divider.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	        divider.setPadding(5, 0, 0, 0);
	        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	        tr.addView(divider); // Adding textView to tablerow.
	 
	        TextView divider2 = new TextView(this);
	        divider2.setText("-------------------------");
	        divider2.setTextColor(Color.GREEN);
	        divider2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	        divider2.setPadding(5, 0, 0, 0);
	        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	        tr.addView(divider2); // Adding textView to tablerow.
	 
	        // Add the TableRow to the TableLayout
	        tl.addView(tr, new TableLayout.LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getApplicationContext(), "add headers "+e, Toast.LENGTH_LONG).show();
    	}
    }
 
    /** This function add the data to the table **/
    public void addData()
    {
    	try
    	{
	    	int maxsize=0;
//	    	Toast.makeText(getApplicationContext(), "the company value is "+maxsize,Toast.LENGTH_LONG).show();
	    	if(strongchild.size()>weakchild.size())
	    	{
	    		maxsize=strongchild.size();
	    		Toast.makeText(getApplicationContext(), "the company value is "+maxsize,Toast.LENGTH_LONG).show();
	    	}
	    	else
	    	{
	    		maxsize=weakchild.size();
	    		Toast.makeText(getApplicationContext(), "the os value is "+maxsize,Toast.LENGTH_LONG).show();
	    	}
	        for(int i = 0; i < maxsize; i++)
	        {
	            /** Create a TableRow dynamically **/
	            tr = new TableRow(this);
	            tr.setLayoutParams(new LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
	 
	            /** Creating a TextView to add to the row **/
	            companyTV = new TextView(this);
	            if(i<strongchild.size())
	            {           	
		            companyTV.setText(strongchild.get(i));
		            companyTV.setTextColor(Color.GREEN);
		            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		            companyTV.setPadding(5, 5, 5, 5);
		            tr.addView(companyTV);  // Adding textView to tablerow.
//		            final Toast t=Toast.makeText(getApplicationContext(), " you touched company is "+companyTV.getText().toString(), Toast.LENGTH_LONG);
		            final String child=companyTV.getText().toString().trim();
		            companyTV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							openChart(child);
							
						}
					});
	            }
	            else
	            {
		            companyTV.setText(" ");
		            companyTV.setTextColor(Color.GREEN);
		            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		            companyTV.setPadding(5, 5, 5, 5);
		            tr.addView(companyTV);  
	            }
	 
	            if(i<weakchild.size())
	            {
	            /** Creating another textview **/
		            valueTV = new TextView(this);
		            valueTV.setText(weakchild.get(i));
		            valueTV.setTextColor(Color.RED);
		            valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		            valueTV.setPadding(5, 5, 5, 5);
		            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            tr.addView(valueTV); // Adding textView to tablerow.		            
//		            final Toast t=Toast.makeText(getApplicationContext(), " you touched os is "+valueTV.getText().toString(), Toast.LENGTH_LONG);
		            final String child=valueTV.getText().toString().trim();
		            valueTV.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							openChart(child);
						}
					});
	            }
	            else
	            {
	            	valueTV = new TextView(this);
		            valueTV.setText(" ");
		            valueTV.setTextColor(Color.RED);
		            valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		            valueTV.setPadding(5, 5, 5, 5);
		            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            tr.addView(valueTV); // Adding textView to tablerow.	
	            }
	            // Add the TableRow to the TableLayout
	            tl.addView(tr, new TableLayout.LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
	        }
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getApplicationContext(), "add values "+e, Toast.LENGTH_LONG).show();
    	}
    }
    private void openChart(String username)
    {
    	try
    	{
	    	HttpClient httpClient = new DefaultHttpClient();
			String url="http://"+MainActivity.ipaddr+":9999/TreatmentDyslexia/LevelChecker?action=childmarkdetails&username="+username;
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
			StringTokenizer usertok=new StringTokenizer(str, "$");
			String uname=usertok.nextToken();
			String values=usertok.nextToken();
	    	int[] x = { 0,1,2,3,4,5};
	    	ArrayList valVec=new ArrayList();
	    	StringTokenizer valtok=new StringTokenizer(values,",");
	    	while(valtok.hasMoreTokens())
	    	{
	    		valVec.add(Double.parseDouble(valtok.nextToken()));
	    	}
	    	// Creating an  XYSeries for Income
	    	// CategorySeries incomeSeries = new CategorySeries("Income");
	    	XYSeries incomeSeries = new XYSeries("Marks Scored");
	    	// Creating an  XYSeries for Income
//	    	XYSeries expenseSeries = new XYSeries("Expense");
	    	// Adding data to Income and Expense Series
	    	for(int i=0;i<valVec.size();i++)
	    	{    		
	    		incomeSeries.add(i,(Double)valVec.get(i));
//	    		expenseSeries.add(i,expense[i]);
	    	} 
	    	
	    	
	    	// Creating a dataset to hold each series
	    	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    	// Adding Income Series to the dataset
	    	dataset.addSeries(incomeSeries);
	    	// Adding Expense Series to dataset
//	    	dataset.addSeries(expenseSeries);    	
	    	
	    	
	    	// Creating XYSeriesRenderer to customize incomeSeries
	    	XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
	    	incomeRenderer.setColor(Color.rgb(220, 100, 60));
	    	incomeRenderer.setFillPoints(true);
	    	incomeRenderer.setLineWidth(2);
	    	incomeRenderer.setDisplayChartValues(true);
	    	
	    	// Creating XYSeriesRenderer to customize expenseSeries
//	    	XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
//	    	expenseRenderer.setColor(Color.rgb(220, 80, 80));
//	    	expenseRenderer.setFillPoints(true);
//	    	expenseRenderer.setLineWidth(2);
//	    	expenseRenderer.setDisplayChartValues(true);    	
	    	
	    	// Creating a XYMultipleSeriesRenderer to customize the whole chart
	    	XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	    	multiRenderer.setXLabels(0);
	    	multiRenderer.setChartTitle("Marks of "+uname);
	    	multiRenderer.setXTitle("Letters");
	    	multiRenderer.setYTitle("marks scored");
	    	multiRenderer.setZoomButtonsVisible(true);    	    	
	    	for(int i=0; i<x.length;i++){
	    		multiRenderer.addXTextLabel(i, mMonth[i]);    		
	    	}    	
	    	
	    	
	    	// Adding incomeRenderer and expenseRenderer to multipleRenderer
	    	// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
	    	// should be same
	    	multiRenderer.addSeriesRenderer(incomeRenderer);
//	    	multiRenderer.addSeriesRenderer(expenseRenderer);
	    	
	    	// Creating an intent to plot bar chart using dataset and multipleRenderer    	
	    	Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);
	    	
	    	// Start Activity
	    	startActivity(intent);
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
    	}
    	
    }
}
