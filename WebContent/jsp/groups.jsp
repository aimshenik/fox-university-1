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
        <h3>Group list:</h3>
        <ul>
          <c:forEach var="g" items="${groups}">
            <li>
              <a href="groups?id=${g.getId()}">
                <c:out value="${g.getName()}" />
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>
      <div class="w3-col m8 l9">
        <h3>Group ${group.getName()} students:</h3>
        <ul>
          <c:forEach var="s" items="${students}">
            <li>
              <p>
                <c:out value="${s.getFirstName()} ${s.getLastName()}" />
              </p>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>
