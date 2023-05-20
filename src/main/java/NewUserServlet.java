

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/newUser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection con;
	private Statement stmnt;
	private int results;
	
    public NewUserServlet() {
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
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createNewUser.html");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String emailId = request.getParameter("emailid");
		String password = request.getParameter("enterpassword");
		String reEnterPassword = request.getParameter("reenterpassword");

		if(password.equals(reEnterPassword)) {
			try {
	//			boolean equals = password.equals(reEnterPassword);
	//			System.out.println("Pasword matched or not:\t" + equals);
				stmnt.executeUpdate("insert into login values('"+emailId+"', '"+password+"')");
				System.out.println("User Created Succesfully..");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("Password did not matched..");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/createNewUser.html");
		rd.include(request, response);
		
	}

}
