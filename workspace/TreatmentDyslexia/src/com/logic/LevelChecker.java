package com.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LevelChecker extends HttpServlet 
{
	String sql = "",result_String="";
	Statement statement = null,statement1=null;
	Connection con = null;
	PrintWriter out =null;
//	Boolean bool=true;
	@Override
	public void init() 
	{
		con = (Connection) getServletContext().getAttribute("connect");
		try 
		{
			statement = con.createStatement();
			statement1=con.createStatement();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		ResultSet rs = null;
		try
		{
			response.setContentType("text/html");
			out = response.getWriter();		
			result_String="";
//			bool=false;
			String action=request.getParameter("action");
			String username=request.getParameter("username");
			System.out.println("the action is "+action+" username is "+username);
			if(action.equalsIgnoreCase("changestatus"))
			{
				String p="",q="",b="",d="",m="",w="";
				String level=request.getParameter("level");
//				sql="select username from childmarks";
//				rs=statement.executeQuery(sql);
//				while(rs.next())
//				{
//					String uname=rs.getString("username");
//					if(uname.equalsIgnoreCase(username))
//					{
//						bool=true;
//						break;
//					}
//				}
				if(level.equalsIgnoreCase("level1"))
				{
					p=request.getParameter("p");
					q=request.getParameter("q");
//					if(bool)
//					{
						sql="UPDATE childmarks SET p='"+p+"',q='"+q+"' WHERE username='"+username+"'";
//					}
//					else
//					{
//						sql="insert into childmarks values('"+ username + "','" + p + "','" + q+ "','" + 0+ "','" + 0+ "','" + 0+ "','" + 0+ "')";
//					}
					System.out.println("inside pq "+p+" q value is "+q);
				}
				else if(level.equalsIgnoreCase("level2"))
				{
					b=request.getParameter("b");
					d=request.getParameter("d");
					sql="UPDATE childmarks SET b='"+b+"',d='"+d+"' WHERE username='"+username+"'";
				}
				else
				{
					m=request.getParameter("m");
					w=request.getParameter("w");
					sql="UPDATE childmarks SET m='"+m+"',w='"+w+"' WHERE username='"+username+"'";
				}
				int i=statement.executeUpdate(sql);
				if(i==0)
				{
					System.out.println("updated failed");	
				}
				else
				{
					System.out.println("updated successfully");
				}
			}
			else if(action.equalsIgnoreCase("checkstatus"))
			{
				sql="select * from childmarks where username='"+username+"'";
				rs=statement.executeQuery(sql);
				if(rs.next())
				{
					int p=Integer.parseInt(rs.getString("p"));
					int q=Integer.parseInt(rs.getString("q"));
					int b=Integer.parseInt(rs.getString("b"));
					int d=Integer.parseInt(rs.getString("d"));
					int m=Integer.parseInt(rs.getString("m"));
					int w=Integer.parseInt(rs.getString("w"));
					if(p>3&&q>3)
					{
						result_String="level1";
						if(b>3&&d>3)
						{
							result_String="level2";
							if(m>3&&w>3)
							{
								result_String="level3";
							}
						}
					}
					else
					{
						result_String="nothing";
					}
					
				}
			}
			else if(action.equalsIgnoreCase("childdetails"))
			{
				try
				{
					ResultSet rs1=null;
					sql="select username from childaccdetails where staff='"+username+"'";
					rs=statement.executeQuery(sql);
					String childname="";
					while(rs.next())
					{
						childname=rs.getString("username");
//						System.out.println("the child name is "+childname);
						sql="select * from childmarks where username='"+childname+"'";
						rs1=statement1.executeQuery(sql);
						String temp="";
						if(rs1.next())
						{
							String uname=rs1.getString("username");
							int p=Integer.parseInt(rs1.getString("p"));
							int q=Integer.parseInt(rs1.getString("q"));
							int b=Integer.parseInt(rs1.getString("b"));
							int d=Integer.parseInt(rs1.getString("d"));
							int m=Integer.parseInt(rs1.getString("m"));
							int w=Integer.parseInt(rs1.getString("w"));
//							System.out.println("the username is "+uname);
							if(p>3&&q>3) 
							{
								temp=uname+"$"+"level1";
								if(b>3&&d>3)
								{
									temp=uname+"$"+"level2";
									if(m>3&&w>3)
									{
										temp=uname+"$"+"level3";
									}
								}
							}
							else
							{
								temp=uname+"$weak";
								System.out.println("======the weak block call====");
							}
							result_String=result_String+temp+"@";
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(action.equalsIgnoreCase("childmarkdetails"))
			{
				sql="select * from childmarks where username='"+username+"'";
				rs=statement.executeQuery(sql);
				if(rs.next())
				{
					result_String=username+"$"+rs.getString("p");
					result_String=result_String+","+rs.getString("q");
					result_String=result_String+","+rs.getString("b");
					result_String=result_String+","+rs.getString("d");
					result_String=result_String+","+rs.getString("m");
					result_String=result_String+","+rs.getString("w");
				}
				System.out.println("the result_String value is "+result_String);
			}
			else
			{
				System.out.println("oops! unknown action received");
			}
			out.println(result_String);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
					rs.close();
					sql="";
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

}
