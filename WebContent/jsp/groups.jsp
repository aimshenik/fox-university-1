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
  <c:import url="/html/menu.html"></c:import>
  <div class="w3-container">
    <div class="w3-row">
      <div class="w3-col m4 l3">
        <h3>Group list:</h3>
        <table class="w3-table w3-centered w3-hoverable w3-bordered">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th colspan="2">Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="group" items="${groups}">
              <tr>
                <td>
                  <c:out value="${group.getId()}" />
                </td>
                <td>
                  <a href="groups?id=${group.getId()}">
                    <c:out value="${group.getName()}" />
                  </a>
                </td>
                <td>
                  <a href="groups?action=edit&id=${group.getId()}">Update</a>
                </td>
                <td>
                  <a href="groups?action=delete&id=<c:out value="${group.getId()}"/>">Delete</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        <p>
          <a href="groups?action=add">Add Group</a>
        </p>
      </div>
      <div class="w3-col m8 l9">
        <c:if test="${not empty group}">
          <h3>Group ${group.getName()} students:</h3>
        </c:if>
        <c:if test="${not empty errorMessage}">
          <h3 class="w3-red">${errorMessage}</h3>
          <c:import url="/jsp/error.jsp"></c:import>
        </c:if>
        <ul>
          <c:forEach var="student" items="${students}">
            <li>
              <p>
                <c:out value="${student.getFirstName()} ${student.getLastName()}" />
              </p>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>
