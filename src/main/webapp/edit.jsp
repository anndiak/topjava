<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Add new user</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="POST" action='MealServlet' name="frmAddMeal">

    DateTime : <input
        type="text" name="DateTime:"
        value="<fmt:formatDate pattern="MM/dd/yyyy" value="${meal.dateTime}" />" /> <br />
    Description : <input
        type="text" name="Description:"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="Calories:"
        value="<c:out value="${user.firstName}" />" /> <br />
    <input
        type="submit" value="Save" />
</form>

</body>
</html>