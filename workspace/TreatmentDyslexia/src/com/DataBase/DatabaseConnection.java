package com.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseConnection implements ServletContextListener
{
	static Connection conn=null;
	public void contextDestroyed(ServletContextEvent arg0)
	{
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent arg1)
	{	
		ServletContext sc=arg1.getServletContext();
		try
		{
			System.out.println("''''''''' Data Connection to database for DYSLEXIA''''''''");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection("jdbc:mysql://localhost/dyslexia","root","root");
			sc.setAttribute("connect",conn);	
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
 	}	
//	public Connection getConnection()
//	{
//		return conn;
//	}
}