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
        <header class="w3-container w3-blue">
          <h4>
            Editing
            <c:out value="${student.getFirstName()} ${student.getLastName()}"></c:out>
          </h4>
        </header>
        <form class="w3-container" method="post" action="student">
          <input type="text" name="id" value="${student.getId()}" class="w3-hide">
          <label>First name:</label>
          <input type="text" name="firstName" value="${student.getFirstName()}" class="w3-input">
          <label>Last name:</label>
          <input type="text" name="lastName" value="${student.getLastName()}" class="w3-input">
          <label>Group:</label>
          <select class="w3-select" name="groupId">
            <option value="${currentGroup.getId()}" selected>${currentGroup.getName()}</option>
            <c:forEach var="group" items="${groups}">
              <option value="${group.getId()}">${group.getName()}</option>
            </c:forEach>
          </select>
          <button class="w3-btn w3-blue" type="submit">Save</button>
        </form>
      </div>
    </c:if>
  </div>
</body>
</html>
