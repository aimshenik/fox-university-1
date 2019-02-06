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
    <div class="w3-col m4 l4 w3-display-middle">
      <h5>
        Editing
        <c:out value="${group.getName()}"></c:out>
      </h5>
      <form class="w3-container" method="post" action="group">
        <input type="text" name="id" value="${group.getId()}" class="w3-hide">
        <input type="text" name="name" value="${group.getName()}" class="w3-input">
        <button class="w3-btn w3-blue" type="submit">Save</button>
      </form>
    </div>
  </div>
</body>
</html>
