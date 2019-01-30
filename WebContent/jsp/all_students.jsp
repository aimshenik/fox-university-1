<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>students</title>
</head>
<body>
  <c:import url="/html/menu.html"></c:import>
  <div class="w3-container">
    <div class="w3-row">
      <div class="w3-col m4 l3">
        <h3>Students list:</h3>
        <table class="w3-table w3-centered w3-hoverable w3-bordered w3-card-4">
          <thead>
            <tr>
              <th>ID</th>
              <th>First name</th>
              <th>Last name</th>
              <th>Group ID</th>
              <th colspan="2">Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="student" items="${students}">
              <tr>
                <td>
                  <c:out value="${student.getId()}" />
                </td>
                <td>
                  <c:out value="${student.getFirstName()}" />
                </td>
                <td>
                  <c:out value="${student.getLastName()}" />
                </td>
                <td>
                  <c:out value="${student.getGroupId()}" />
                </td>
                <td>
                  <form action="students" method="post">
                    <input name="action" value="update" hidden="true" />
                    <input name="id" value="${student.getId()}" hidden="true" />
                    <button class="w3-btn w3-blue" type="submit">Edit</button>
                  </form>
                </td>
                <td>
                  <form action="students" method="post">
                    <input name="action" value="delete" hidden="true" />
                    <input name="id" value="${student.getId()}" hidden="true" />
                    <button class="w3-btn w3-red" type="submit" onclick="return window.confirm('Delete student ${student.getFirstName()} ${student.getLastName()}?')">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="6">
                <form action="students" method="post">
                  <input name="action" value="create" hidden="true" />
                  <button class="w3-btn w3-light-green" type="submit">Add student</button>
                </form>
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
