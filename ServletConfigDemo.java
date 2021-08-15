package com.javaByDeveloper.ServletConfigDemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletConfigDemo
 */
public class ServletConfigDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String driverClassName,driverURL,UserName,Password;
    public ServletConfigDemo() {
        super();
        
    }
	public void init(ServletConfig config) throws ServletException {
		driverClassName = config.getInitParameter("driver");
		driverURL       = config.getInitParameter("url");
		UserName        = config.getInitParameter("username");
		Password        =  config.getInitParameter("password");
		 
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String continentName = request.getParameter("select");
		//To handle the error page if empty option is selected
		if(continentName.equals("error")){
			out.println("<center>");
			out.println("<h2>Please select any one of the continents</h2>");
			out.println("<br>");
			out.println("<a href=Continents.html> Click Here To Go Back To Home Page </a>");
			out.println("</center>");
	}else{
		  try{
			Class.forName(driverClassName);
			Connection con = DriverManager.getConnection(driverURL, UserName, Password);
			System.out.println("Connection is sucessfully opened");
			Statement stmt = con.createStatement();
			System.out.println("Statment is created");
			ResultSet rs = stmt.executeQuery("select "+continentName+" from continents");
			ResultSetMetaData rsmd = rs.getMetaData();
			//get columns count in ResultSet
			int count = rsmd.getColumnCount();
			out.println("<center>");
			
			if(continentName.equals("Asia")||continentName.equals("Africa")||continentName.equals("SouthAmerica")||continentName.equals("Europe")){
				out.println("<table border=2 bgcolor=orange>");
			out.println("<h4 style=background-color:orange>Displaying Some Countries Of "+continentName+"</h4>");
			}else{
				out.println("<table border=2 bgcolor=cyan>");
				out.println("<h4 style=background-color:cyan>Displaying Some States Of "+continentName+"</h4>");
			}
			out.println("<tr>");
			for(int i=1;i<=count;i++){
				out.println("<tb>"+rsmd.getColumnName(i)+"<tb>");
			}
			out.println("</tr>");
			//Print data of column from ResultSet 
			while(rs.next()){
				out.println("<tr>");
				for(int k=1;k<=count;k++){
					out.println("<td>"+rs.getString(k)+"</td>");
				}
				out.println("</tr>");
			}
			
			out.println("</table> </center>");
			rs.close();
			stmt.close();
			System.out.println("Statement is sucessfully closed");
			con.close();
			System.out.println("Connection is sucessfully closed");
			out.println("<center>");
			out.println("<br>");	
			out.println("<a href=Continents.html> Click Here To Select Another Continent </a>");
			out.println("</center>");
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	}
}


