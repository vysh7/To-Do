// ToDoDAOImpl.java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Register;
import beans.Task;
import factory.DBConn;

public class ToDoDAOImpl implements ToDoDAO {

	Connection con;
	Statement stmt;
	PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmt5;
	ResultSet rs;
	
	public ToDoDAOImpl() {
		try {
			con=DBConn.getConn();
			stmt=con.createStatement();
			pstmt1=con.prepareStatement("INSERT INTO register VALUES (?,?,?,?,?,?,?)");
			pstmt2=con.prepareStatement("INSERT INTO tasks VALUES (?,?,?,?,?)");
			pstmt3=con.prepareStatement("INSERT INTO taskid_pks VALUES (?,?)");
			pstmt4=con.prepareStatement("UPDATE taskid_pks SET taskid=? WHERE regid=?");
			pstmt5=con.prepareStatement("UPDATE tasks SET taskstatus=? WHERE regid=? AND taskid=?");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int register(Register register) {
		int regid=0;
		try {
			// pk generation
			rs=stmt.executeQuery("SELECT max(regid) FROM register");
			if(rs.next()) {
				regid=rs.getInt(1);
			}
			regid++;
			
			pstmt1.setInt(1, regid);
			pstmt1.setString(2, register.getFname());
			pstmt1.setString(3, register.getLname());
			pstmt1.setString(4, register.getEmail());
			pstmt1.setString(5, register.getPass());
			pstmt1.setLong(6, register.getMobile());
			pstmt1.setString(7, register.getAddress());
			int i=pstmt1.executeUpdate();
			if(i==1) 
				System.out.println("record inserted into register table");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return regid;
	}

	@Override
	public int login(String email, String pass) {
		int regid=0;
		try {
			rs=stmt.executeQuery("select regid from register where email='"+email+"' and pass='"+pass+"'");
			if(rs.next()) {
				regid=rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return regid;
	}

	@Override
	public boolean addTask(Task task, int regid) {
		boolean flag=false; boolean isNew=true; int i,j=0; int taskid=0;
		try {
			rs=stmt.executeQuery("select taskid from taskid_pks where regid="+regid);
			if(rs.next()) {
				taskid=rs.getInt(1);
				isNew=false;
			}
			taskid++;
			con.setAutoCommit(false);
			pstmt2.setInt(1, taskid);
			pstmt2.setString(2, task.getTaskName());
			pstmt2.setString(3,  task.getTaskDate());
			pstmt2.setInt(4,task.getTaskStatus());
			pstmt2.setInt(5, task.getRegid());
			i=pstmt2.executeUpdate();
			if(isNew) {
				pstmt3.setInt(1, regid);
				pstmt3.setInt(2, taskid);
				j=pstmt3.executeUpdate();
			} else {
				pstmt4.setInt(1, taskid);
				pstmt4.setInt(2, regid);
				j=pstmt4.executeUpdate();
			}
			if(i==1 && j==1) {
				con.commit(); flag=true;
				System.out.println("Task inserted");
			} else {
				con.rollback(); flag=false;
				System.out.println("TX insertion failed");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Task> findAllTasks(int regid) {
		List<Task> taskList=new ArrayList<Task>();
		try {
			rs=stmt.executeQuery("select * from tasks where regid="+regid);
			while(rs.next()) {
				Task task=new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
				taskList.add(task);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}

	@Override
	public boolean markTaskCompleted(int taskid, int regid) {
		boolean flag=false;
		try {
			pstmt5.setInt(1, 3); // taskstatus 3 is completed
			pstmt5.setInt(2, regid);
			pstmt5.setInt(3, taskid);
			int i=pstmt5.executeUpdate();
			if(i==1)
				System.out.println(taskid+" task marked completed");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}