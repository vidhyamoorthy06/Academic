package com.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

	String sql = "";
	Statement statement = null;
	HashSet<String> staffname=new  HashSet<String>();
	HashSet<String> childname=new  HashSet<String>();
	Connection con = null;
	PrintWriter out =null;
	Random rand=null;
	@Override
	public void init() 
	{
		con = (Connection) getServletContext().getAttribute("connect");
		try 
		{
			statement = con.createStatement();
			rand=new Random();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result_status = "";
		ResultSet rs = null;
		try {
			
			response.setContentType("text/html");
			out = response.getWriter();
			String action = request.getParameter("action").trim();
			String username = request.getParameter("username");
			String Password = request.getParameter("password");
			System.out.println("============Login Call success==========="+action);
			if (!(username.equalsIgnoreCase(null)|| Password.equalsIgnoreCase(null))) 
			{
				if (action.equalsIgnoreCase("Staff Login")) 
				{
					staffname.add("saran");
					sql = "select password from staffaccdetails where username='"
						+ username + "'";
					rs = statement.executeQuery(sql);
					if (rs.next()) {
						String dbpass = rs.getString("password");
						if (Password.equalsIgnoreCase(dbpass)) {
							result_status = "success";
							System.out.println("user login success");
						} else {
							result_status = "incorrect";
							System.out.println("user login fail 1");
						}
					}
					else {
						result_status = "incorrect";
						System.out.println("user login fail 1");
					}
				} 
				else if (action.equalsIgnoreCase("Child Login")) 
				{
//					System.out.println("the user login call success "+username+" pass "+Password);
					sql = "select password from childaccdetails where username='"
							+ username + "'";
					rs = statement.executeQuery(sql);
					if (rs.next()) {
						String dbpass = rs.getString("password");
						if (Password.equalsIgnoreCase(dbpass)) {
							result_status = "success";
							System.out.println("user login success");
						} else {
							result_status = "incorrect";
							System.out.println("user login fail");
						}
					}
					else {
						result_status = "incorrect";
						System.out.println("user login fail");
					}
				} 
				else if (action.equalsIgnoreCase("Child Registration"))
				{	
					String dob = request.getParameter("dob");
					Boolean childregbool=childname.add(username);
					if (!dob.equalsIgnoreCase(null)&&childregbool) 
					{
						staffname.add("saran");
						Vector<String> tempvec=new Vector<String>();
						tempvec.addAll(staffname);
						System.out.println("the size is "+tempvec.size()+" staffname size is "+staffname.size());
						String staff=tempvec.get(rand.nextInt(tempvec.size()));
						System.out.println("registration success call");
						sql = "insert into childaccdetails values('"+ username + "','" + Password + "','" + dob+ "','"+staff+"')";
						int i = statement.executeUpdate(sql);
						if (i == 1) {
							result_status = "success";
							sql="insert into childmarks values('"+ username + "','" + 0 + "','" + 0+ "','" + 0+ "','" + 0+ "','" + 0+ "','" + 0+ "')";
							i = statement.executeUpdate(sql);
						} else {
							result_status = "invalid";
						}
					} 
					else 
					{
						result_status = "invalid";
					}
				}
				else
				{
					String dob = request.getParameter("dob");
					Boolean staffreg=staffname.add(username);
					if (!dob.equalsIgnoreCase(null)&&staffreg) 
					{
						System.out.println("registration success call");
						sql = "insert into staffaccdetails values('"+ username + "','" + Password + "','" + dob+ "')";
						int i = statement.executeUpdate(sql);
						if (i == 1) {
							result_status = "success";
						} 
						else 
						{
							result_status = "invalid";
						}
					} 
					else 
					{
						result_status = "invalid";
					}
				}
			}
			else 
			{
				result_status = "invalid";
			}
			out.println(result_status);
		} 
		catch (Exception e) 
		{
			result_status = "invalid";
			e.printStackTrace();
		} 
		finally {
			try {
				if(!(rs==null))
				{
					rs.close();
					sql="";
				}
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		try {
			con.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
