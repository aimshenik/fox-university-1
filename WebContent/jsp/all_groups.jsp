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
      <div class="w3-col m4 l4 w3-display-left">
        <h3>Groups list:</h3>
        <table class="w3-table-all w3-centered w3-card-4">
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
                  <form action="group" method="get">
                    <input name="id" value="${group.getId()}" class="w3-hide" />
                    <button class="w3-btn w3-block w3-blue" type="submit">Edit</button>
                  </form>
                </td>
                <td>
                  <form action="group/delete" method="post">
                    <input name="id" value="${group.getId()}" class="w3-hide" />
                    <button class="w3-btn w3-block w3-red" type="submit" onclick="return window.confirm('Delete group ${group.getName()}?')">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr class='w3-cyan'>
              <td colspan="4">
                <div class="w3-container w3-cyan">
                  <h2>Create new group</h2>
                </div>
                <form action="groups" method="post">
                  <div class="w3-threequarter">
                    <input name="name" value="" class="w3-input w3-border" />
                  </div>
                  <div class="w3-quarter  ">
                    <button class="w3-btn w3-block w3-cyan" type="submit">Add</button>
                  </div>
                </form>
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
      <div class="w3-col m6 l6 w3-display-right">
        <c:if test="${not empty group}">
          <h3>Group ${group.getName()} students:</h3>
        </c:if>
        <c:if test="${not empty errorMessage}">
          <h3 class="w3-red">${errorMessage}</h3>
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
