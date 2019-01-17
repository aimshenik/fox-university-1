<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.imshenik.university.domain.Student"%>
<%@ page import="net.imshenik.university.domain.Group"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task 13 - User Interface-1</title>
</head>
<body>
	<h1>Create status pages (read data from dao - show it in JSP)</h1>
	<form action="getGroup" method="post">
		<input type="text" name="gr_id"> <input type="submit">
	</form>
	<%
	    out.println("<br>Group:<br>");
	    Group p = (Group) request.getAttribute("group_obj");
	    if (p != null) {
	        out.println(p.toString() + "<br>");
	    }
	%>
	<%
	    out.println("<br>Students:");
	    List<Student> students = (List<Student>) request.getAttribute("students");
	    if (students != null) {
	        for (Student s : students) {
	            out.println("<br>" + s.toString());
	        }
	    }
	%>
</body>
</html>