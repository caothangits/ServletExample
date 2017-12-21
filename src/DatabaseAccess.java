
import java.io.IOException;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/DatabaseAccess")
public class DatabaseAccess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Ten cua driver va dia chi URL cua co so du lieu
	String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	String DB_URL = "jdbc:mysql://localhost:3306/test";
	Connection conn;
	Statement stmt;

	// Ten nguoi dung va mat khau cua co so du lieu
	String USER = "root";
	String PASS = "12345";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String title = "Database Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n");
		
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("-------- MySQL JDBC Connection Demo ------------");
	        try
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	        }
	        catch (ClassNotFoundException e) {
	        	System.out.println(e);
	            System.out.println("MySQL JDBC Driver not found !!");
	            return;
	        }
			
	        System.out.println("MySQL JDBC Driver Registered!");
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute SQL query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, age, first, last FROM test.employees";
			ResultSet rs = stmt.executeQuery(sql);

			// Extract data from result set
			while (rs.next()) {
				// Lay du lieu boi su dung ten cot
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");

				// Hien thi cac gia tri
				out.println("ID: " + id + "<br>");
				out.println(", Age: " + age + "<br>");
				out.println(", First: " + first + "<br>");
				out.println(", Last: " + last + "<br>");
			}
			out.println("</body></html>");

			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Xu ly cac loi cho JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Xu ly cac loi cho Class.forName
			e.printStackTrace();
		} finally {
			// Khoi finally duoc su dung de dong cac resource
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // Ket thuc khoi finally
		} // Ket thuc khoi try
	}
}
