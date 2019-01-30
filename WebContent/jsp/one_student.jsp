<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta charset="UTF-8">
<title>EDIT/INSERT</title>
</head>
<body>
  <c:import url="/html/menu.html"></c:import>
  <div class="w3-row">
    <c:if test="${not empty student}">
      <div class="w3-col m4 l4 w3-display-middle">
        <h5>
          Editing
          <c:out value="${student.getFirstName()} ${student.getLastName()}"></c:out>
        </h5>
        <form class="w3-container" method="post" action="students">
          <input type="text" name="action" value="confirm" hidden="true">
          <label>First name:</label>
          <input type="text" name="firstName" value="${student.getFirstName()}" class="w3-input">
          <label>Last name:</label>
          <input type="text" name="lastName" value="${student.getLastName()}" class="w3-input">
          <label>Group ID:</label>
          <input type="text" name="groupId" value="${student.getGroupId()}" class="w3-input">
          <input type="text" name="id" value="${student.getId()}" hidden="true">
          <button class="w3-btn w3-blue" type="submit">Save</button>
        </form>
      </div>
    </c:if>
    <c:if test="${empty student}">
      <div class="w3-col m4 l4 w3-display-middle">
        <h5>Creating new student</h5>
        <form class="w3-container" method="post" action="students">
          <input type="text" name="action" value="confirm" hidden="true">
          <label>First name:</label>
          <input type="text" name="firstName" value="" class="w3-input">
          <label>Last name:</label>
          <input type="text" name="lastName" value="" class="w3-input">
          <label>Group ID:</label>
          <input type="text" name="groupId" value="" class="w3-input">
          <button class="w3-btn w3-blue" type="submit">Save</button>
        </form>
      </div>
    </c:if>
  </div>
</body>
</html>