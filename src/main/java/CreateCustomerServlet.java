

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateCustomer
 */
@WebServlet("/createCustomer")
public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection con;
	private Statement stmnt;
	private ResultSet results;
	
    public CreateCustomerServlet() {
        super();
    }
    
    public void init(ServletConfig config) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db_02", "root", "Mysql");
    		stmnt = con.createStatement();
    	} catch (Exception e) {
			System.out.println(e);
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Captain");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String emailId = request.getParameter("emailid");
		String phoneNum = request.getParameter("phone");
		String city = request.getParameter("city");
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			try {
				int numOfRecords = stmnt.executeUpdate("insert into customers values('"+name+"', '"+emailId+"', '"+phoneNum+"', '"+city+"')");
				System.out.println("Customer saved successfully..");
			} catch (Exception e) {
				System.out.println(e);
			}
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createCustomer.html");
			rd.include(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		
	}

}







