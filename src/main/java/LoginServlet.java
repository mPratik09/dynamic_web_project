

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection con;
	private Statement stmnt;
	private ResultSet results;
    
    public LoginServlet() {
        super();
    }

    public void init(ServletConfig config){
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
//    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db_02", "root", "Mysql");
    		stmnt = con.createStatement();
    	} catch (Exception e) {
			System.out.println(e);
		}
    } 
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String emailId = request.getParameter("emailid");
		String password = request.getParameter("password");
		HttpSession session = request.getSession(true);
		
		try{
			results = stmnt.executeQuery("select * from login where emailid = '"+emailId+"' and password = '"+password+"'");
			if(results.next()){
				System.out.println("Logged in successfully..");
				session.setAttribute("emailid", emailId);
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createCustomer.html");
				rd.forward(request, response);

//				session.setMaxInactiveInterval(10);
			}
			else {
				System.out.println("Invalid Credentials");
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
	} 

}
