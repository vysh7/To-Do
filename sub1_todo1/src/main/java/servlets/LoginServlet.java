// LoginServlet.java
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		String email=request.getParameter("email").trim();
		String pass=request.getParameter("pass").trim();
		
		ToDoDAO dao=new ToDoDAOImpl();
		int regid=dao.login(email, pass);
		if(regid==0) {
			// out.println("Login Failed");
			request.setAttribute("loginError", "Invalid Email / Pass ");
			context.getRequestDispatcher("/Login.jsp").forward(request, response);
			
		} else {
			session.setAttribute("email" , email);
			session.setAttribute("regid", regid);
		
			context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
		}	
	}
}