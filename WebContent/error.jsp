<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
</head>
<body>
	<%@ page isErrorPage="true" %>
	<h5>Ops! A error occurred on application! </h5>
	<%= exception %>
</body>
</html>