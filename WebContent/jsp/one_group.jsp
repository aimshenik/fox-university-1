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
    <c:if test="${not empty group}">
      <div class="w3-col m4 l4 w3-display-middle">
        <h5>
          Editing
          <c:out value="${group.getName()}"></c:out>
        </h5>
        <form class="w3-container" method="post" action="groups">
          <input type="text" name="action" value="confirm" hidden="true">
          <label>group name:</label>
          <input type="text" name="name" value="${group.getName()}" class="w3-input">
          <input type="text" name="id" value="${group.getId()}" hidden="true">
          <button class="w3-btn w3-blue" type="submit">Save</button>
        </form>
      </div>
    </c:if>
    <c:if test="${empty group}">
      <div class="w3-col m4 l4 w3-display-middle">
        <h5>Creating new group</h5>
        <form class="w3-container" method="post" action="groups">
          <label>group name:</label>
           <input type="text" name="action" value="confirm" hidden="true">
          <input type="text" name="name" value="" class="w3-input">
          <button class="w3-btn w3-blue" type="submit">Save</button>
        </form>
      </div>
    </c:if>
  </div>
</body>
</html>