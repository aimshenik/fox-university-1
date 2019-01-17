<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task 13 - User Interface-1</title>
</head>
<body>
	<h1>University</h1>
	<form action="group" method="get">
		Enter Group id : <input type="text" name="groupId"> <input type="submit" value="search">
	</form>
	<br>Group:
	<br>
	<c:out value="${group}" />
	<br>
	<br> Students:
	<br>
	<ul>
	<c:forEach var="s" items="${students}">
		<li><c:out value="${s}" /></li>
		
	</c:forEach>
	</ul>
	<br>
</body>
</html>
