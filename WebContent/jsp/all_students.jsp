<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Students</title>
</head>
<body>
  <c:import url="/html/menu.html"></c:import>
  <div class="w3-container">
    <div class="w3-row">
      <div class="w3-col m6 l6">
        <h3>Students list:</h3>
        <table class="w3-table w3-centered w3-hoverable w3-bordered w3-card-4">
          <thead>
            <tr>
              <th>ID</th>
              <th>First name</th>
              <th>Last name</th>
              <th>Group</th>
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
                  <c:out value="${groupsMap.get(student.getGroupId())}" />
                </td>
                <td>
                  <form action="student" method="get">
                    <input name="id" value="${student.getId()}" class="w3-hide" />
                    <button class="w3-btn w3-block w3-blue" type="submit">Edit</button>
                  </form>
                </td>
                <td>
                  <form action="student/delete" method="post">
                    <input name="id" value="${student.getId()}" class="w3-hide" />
                    <button class="w3-btn w3-block w3-red" type="submit" onclick="return window.confirm('Delete student ${student.getFirstName()} ${student.getLastName()}?')">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr class="w3-cyan">
              <td colspan="6">
                <div class="w3-container w3-cyan">
                  <h2>Create new student</h2>
                </div>
                <form action="students" method="post" class="w3-container">
                  <div class="w3-quarter">
                    <input name="firstName" placeholder="first name" class="w3-input w3-border" />
                  </div>
                  <div class="w3-quarter">
                    <input name="lastName" placeholder="last name" class="w3-input w3-border" />
                  </div>
                  <div class="w3-quarter">
                    <select class="w3-select" name="groupId">
                      <option value="" disabled selected>Choose group</option>
                      <c:forEach var="group" items="${groups}">
                        <option value="${group.getId()}">${group.getName()}</option>
                      </c:forEach>
                    </select>
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
    </div>
  </div>
</body>
</html>
