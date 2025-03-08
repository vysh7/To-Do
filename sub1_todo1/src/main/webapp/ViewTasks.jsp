<%@page import="beans.Task"%>
<%@page import="java.util.List"%>
<%@page import="dao.ToDoDAO"%>
<%@page import="dao.ToDoDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ToDo List</title>
</head>
<body>
	<p align="right">
		Welcome <b style='color: red'><%=session.getAttribute("email")%></b>
	</p>
	<form method="post" action="./AddTaskServlet">
		<table align="center" width="30%" border="1">
			<tr>
				<th>TaskName</th>
				<td><input type="text" name="taskName"></td>
			</tr>
			<tr>
				<th>TaskDate</th>
				<td><input type="text" name="taskDate" placeholder="dd-mm-yyyy"></td>
			</tr>
			<tr>
				<th>TaskStatus</th>
				<td><select name="taskStatus">
						<option value="1">Not Yet Started</option>
						<option value="2">Pending</option>
						<option value="3">Completed</option>
				</select></td>
			</tr>
			<tr>
				<th><input type="submit" name="submit" value="Add Task"></th>
				<td><input type="reset" name="reset" value="Clear"></td>
			</tr>
		</table>
	</form>
	<br />
	<hr width="50%" border="1" />
	<%
	String email = session.getAttribute("email").toString();
	int regid = ((Integer) session.getAttribute("regid")).intValue();
	ToDoDAO dao = new ToDoDAOImpl();
	List<Task> taskList = dao.findAllTasks(regid);
	%>
	<table align="center" width="50%" border="1">
		<tr>
			<th>Task ID</th>
			<th>Task Name</th>
			<th>Task Date</th>
			<th>Task Status</th>
		</tr>
		<%
		for (Task task : taskList) {
			int status = task.getTaskStatus();
			if (status == 3) {
		%>
		<strike>
			<tr>

				<td><strike><%=task.getTaskid()%></strike></td>
				<td><strike><%=task.getTaskName()%></strike></td>
				<td><strike><%=task.getTaskDate()%></strike></td>
				<td><strike>Completed</strike></td>

			</tr> <%
 } else {
 %>
			<tr>
				<td><%=task.getTaskid()%></td>
				<td><%=task.getTaskName()%></td>
				<td><%=task.getTaskDate()%></td>
				<td><a
					href='./TaskCompletedServlet?taskid=<%=task.getTaskid()%>'><font
						color='red'>Complete</font></a></td>
			</tr> <%
 }
 }
 %>
		
	</table>
	<p align="center">
		<a href="./LogoutServlet">Logout</a>
	</p>

</body>
</html>
