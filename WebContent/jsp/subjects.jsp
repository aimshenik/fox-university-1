<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Groups</title>
</head>
<body>
  <div class="w3-center">
    <h1>University</h1>
  </div>
  <c:import url="/html/menu.html"></c:import>
  <div class="w3-container">
    <div class="w3-row">
      <div class="w3-col m4 l3">
        <h3>Subjects list:</h3>
        <ul>
          <c:forEach var="s" items="${subjects}">
            <li>
              <c:out value="${s.getName()}" />
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>
