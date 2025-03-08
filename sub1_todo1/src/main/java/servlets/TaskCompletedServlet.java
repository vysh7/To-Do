// TaskCompletedServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;


@WebServlet("/TaskCompletedServlet")
public class TaskCompletedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		int taskid=Integer.parseInt(request.getParameter("taskid"));
		int regid=((Integer)session.getAttribute("regid")).intValue();
		ToDoDAO dao=new ToDoDAOImpl();
		dao.markTaskCompleted(taskid, regid);
		context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
	}

}