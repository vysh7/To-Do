// AddTaskServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Task;
import dao.ToDoDAOImpl;


@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context=getServletContext();
		HttpSession session=request.getSession();
		String taskName=request.getParameter("taskName").trim();
		String taskDate=request.getParameter("taskDate").trim();
		int taskStatus=Integer.parseInt(request.getParameter("taskStatus").trim());
		int regid=((Integer)session.getAttribute("regid")).intValue();
		Task task=new Task(0, taskName, taskDate, taskStatus, regid);
		boolean flag=new ToDoDAOImpl().addTask(task, regid);
		if(flag) {
			context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
		}
	}
}
