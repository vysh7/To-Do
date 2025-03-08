<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%
	Object o = request.getAttribute("loginError");
	String error = (o == null) ? "" : o.toString();
	%>
	<p align="center" style="color: red;">
		<b><%=error%></b>
	</p>
	<form method="post" action="./LoginServlet">
		<table border="1" width="25%" align="center">
			<tr>
				<th>Email</th>
				<td><input type="text" name="email" value=""></td>
			</tr>
			<tr>
				<th>Pass</th>
				<td><input type="password" name="pass" value=""></td>
			</tr>
			<tr>
				<th><input type="submit" name="submit" value="Login"></th>
				<td><input type="reset" name="reset" value="Clear"></td>
			</tr>
		</table>
	</form>
	<P align="center">
		<A href="./Register.html">Register</A>
	</P>
</body>
</html>